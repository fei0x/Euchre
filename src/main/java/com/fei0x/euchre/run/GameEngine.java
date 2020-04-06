package com.fei0x.euchre.run;

import com.fei0x.euchre.distributed_play.EuchreClientImpl;
import com.fei0x.euchre.distributed_play.EuchreServerImpl;
import com.fei0x.euchre.distributed_play.ServerPrintStream;
import com.fei0x.euchre.exceptions.MissingPlayer;
import com.fei0x.euchre.game.EuchreGame;
import com.fei0x.euchre.game.Player;
import com.fei0x.euchre.player_ai.PlayerAI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is the Euchre Game Engine, call the main class with your choice of parameters to start a game.
 */
public class GameEngine
{
    /**
     * Play some Euchre!!! follow the input parameter schema below.. (sorry for it being kinda big.)
     * <int verbose 0 = none 1 = minimal 2 = all>
     * [ client <host server> <int serverport> <int clientport> <ClassName com.yourpath.to.your.PlayerClass> <Player playername>] 
     * | 
     * [
     * 	[server <int port> ]
     *  [roundrobin <int numPlays>]
     *  [<ClassName playerType>  <Player playername>]*
     * ]
     *
     * //Note  players must have a single string constructor.
     * //Note  in local-only play the local players will be teamed in order, so that the first two players are team one, and the second two team two.
     *
     * Example running game locally (4 players required)
     * 1 com.fei0x.euchre.player_ai.ExamplePlayerRandomAI Jeremy com.fei0x.euchre.player_ai.ExamplePlayerRandomAI Jonathan com.fei0x.euchre.player_ai.ExamplePlayerRandomAI Adrian com.fei0x.euchre.player_ai.ExamplePlayerRandomAI Glen
     * 
     * Example joining as client
     * 1 client 5.5.5.5 555 444 com.fei0x.euchre.player_ai.HumanPlayerAI Jeremy
     * 
     * Example host as server and one player (3 clients should join)
     * 1 server 555 com.fei0x.euchre.player_ai.HumanPlayer Jeremy
     * 
     * Example host as server hosting with round robin and two players (2 clients should join)
     * 1 server 555 roundrobin 3 com.fei0x.euchre.player_ai.ExamplePlayerRandomAI Jeremy com.fei0x.euchre.player_ai.ExamplePlayerRandomAI Jonathan
     * 
     * 
     *
     * @param args see above
     * @throws IllegalStateException
     * @throws MissingPlayer
     */
    public static void main( String[] args ) throws IllegalStateException, MissingPlayer, UnknownHostException, RemoteException, IOException, InterruptedException
    {
        int verbose = 0;
        boolean roundRobin = false;
        int roundRobinPlays = 1;
        boolean client = false;
        boolean server = false;
        int clientport = 0;
        int serverport = 0;
        String serverhost = "";
        List<Player> localPlayers = new ArrayList<Player>();
        Player clientPlayer = null;

        int argIndex = 0;

        verbose = Integer.parseInt(args[0]);

        //populate variables form command line arguments
        if(args[1].equalsIgnoreCase("client")){  //in the case where this is a client connecting to a servier
             client = true;
             System.out.println("Running as Client");
             serverhost = args[2];
             serverport = Integer.parseInt(args[3]);
             clientport = Integer.parseInt(args[4]);

         	 System.out.println("Add Client Player:");
             clientPlayer = makePlayer(args[5], args[6]);
             
        }else{  //in the case where this is a server hosting a game or just a local game
            if(args[1].equalsIgnoreCase("server")){
                server = true;
                System.out.println("Running as Server");
                serverport = Integer.parseInt(args[2]);
                argIndex = 3;
            }else{
                argIndex = 1;
            }
            
            if(args.length > argIndex){ //check for more args, it's possible just to be a server with no players 
            	
                if(args[argIndex].equalsIgnoreCase("roundrobin")){ //check if there is a round robin
                    roundRobin = true;
                    argIndex++;
                    roundRobinPlays = Integer.parseInt(args[argIndex]);
                    argIndex++;
                }
            
                if(args.length > argIndex){
                	System.out.println("Add Local Players:");
                }
                
                while(args.length > argIndex +1){  // all the args left should be players, could be more than 4 if doing a round robin, or as low as 0 if running server with no players

                	localPlayers.add(makePlayer(args[argIndex], args[argIndex+1]));
                    argIndex += 2;
                }
            }
        }
        
        
        if(client){  //connect to the server
            EuchreClientImpl euchreClient = new EuchreClientImpl(clientPlayer, clientport, System.out);
            euchreClient.connectToServer(serverhost, serverport);
            System.out.println("Session Ended.");


        }else if(server){ // wait and connect to the clients
            System.out.println("Startup Euchre Server");
            EuchreServerImpl euchreServer = new EuchreServerImpl(new ArrayList<Player>(),serverport,System.out);
            ServerPrintStream sps = new ServerPrintStream(euchreServer, System.out);

            boolean waitingForPlayers = true;

            //input mechanism for beginning game.
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while(waitingForPlayers){
                System.out.println("Type 'go' once all players have entered to begin play.");
                String response = in.readLine();

                if(response.equalsIgnoreCase("go")){
                    waitingForPlayers = false;
                    euchreServer.stopAcceptingPlayers();
                }
            }


            //accumulate players
            List<Player> allplayers = new ArrayList<Player>();
            allplayers.addAll(localPlayers);
            allplayers.addAll(euchreServer.getRemotePlayers());

            //make sure there are 4 players.
            if(allplayers.size() < 4){
                sps.println("Too Few Players. Enlist 4 players to play euchre. Game closing.");
                sps.close();
            	euchreServer.disconnectAll();
            	throw new MissingPlayer("","Too Few Players. Enlist 4 players to play euchre. Game closing.");
            }

            sps.println("The game begins now.");
            Collections.shuffle(allplayers);

            //play Euchre
            if(roundRobin){
                new RoundRobin(allplayers, roundRobinPlays, verbose, sps).playRoundRobin();
            }else{
                new EuchreGame(allplayers.get(0),allplayers.get(1),allplayers.get(2),allplayers.get(3), verbose, sps).playGame();
            }
            sps.close();
            euchreServer.disconnectAll();
            Thread.sleep(3000);//wait 3 seconds so that they don't all crash from closing too soon.
        
        
        }else{  //you're just playing a regular local game of Euchre (or Round Robin)
            //play Euchre
            if(roundRobin){
                new RoundRobin(localPlayers, roundRobinPlays, verbose, System.out).playRoundRobin();
            }else{
                new EuchreGame(localPlayers.get(0),localPlayers.get(1),localPlayers.get(2),localPlayers.get(3), verbose, System.out).playGame();
            }
        }
        
        System.out.println("Thanks for playing!");
        System.exit(0);
    }

    /**
     * Attempt to construct a player with inputs from main. Attempts to make a player with and without inputs
     * @param className the class to use for the AI
     * @param playerName the name of the player
     * @return a player object ready to play Euchre with an AI attached
     */
    private static Player makePlayer(String className, String playerName){
    	Player player = null;
    	try{
	    	Class<?> parttypes[] = {};
	        Constructor<?> ct = Class.forName(className).getConstructor(parttypes);
	        player = new Player(playerName, (PlayerAI)ct.newInstance());
    	}catch(Exception e){
            try{
                //in case of the human constructor
                Class<?> parttypes[] = {PrintStream.class, InputStream.class};
                Constructor<?> ct = Class.forName(className).getConstructor(parttypes);
                Object arglist[] = { System.out, System.in};
                player = new Player(playerName, (PlayerAI)ct.newInstance(arglist) );
            }catch(Exception e2){
                e.printStackTrace();
                e2.printStackTrace();
                throw new MissingPlayer(playerName, "Could not make PlayerAI of class '" + className + "' with a no arg constructor.", e);
            }
        }

    	System.out.println("Added Players: " + playerName + " with AI: " + className);
    	return player;
    }

}
