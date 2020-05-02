package com.fei0x.euchre.run;

import com.fei0x.euchre.distributed_play.client.EuchreClientImpl;
import com.fei0x.euchre.distributed_play.server.EuchreServerImpl;
import com.fei0x.euchre.distributed_play.server.ServerPrintStream;
import com.fei0x.euchre.exceptions.MissingPlayer;
import com.fei0x.euchre.game.EuchreGame;
import com.fei0x.euchre.game.Player;
import com.fei0x.euchre.game.PlayerAI;

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
public class RunEuchre
{
	

	public static final String GAME_MODE__CLIENT = "Client"; //this execution is from a client perspective
	public static final String GAME_MODE__SERVER = "Server"; //this execution is from a server perspective
	public static final String GAME_MODE__LOCAL = "Local";   //this execution is done completely locally

	
	
	/**
	 * Below are variables representing the program's arguments.
	 * Various arguments are needed for different modes.
	 */

	/**
	 * What mode the game is working in, default to local only
	 */
	private static String gameMode = null;
	
	/**
	 * General Euchre game data, meaningful to server and local play only
	 */
	private static boolean verbose = false; //true will make the games more verbose
	private static boolean roundRobin = false; //indicates if a multiple games of euchre should be played
	private static int roundRobinPlays = 1; //indicates how many games for each combination of players should be played, when doing a round robin
	private static List<Player> localPlayers = new ArrayList<Player>(); //a list of all of the players declared locally, or by the server
    
	/**
	 *  Server information, needed for both servers and clients
	 */
	private static int serverport = 0; //port of the server to host with or connect to
	
	/**
	 *  Client information, needed for clients
	 */
	private static String serverhost = ""; //host address of the server to connect to
	private static int clientport = 0; //port to which serve the client's RMI with
	private static String clientPlayerName = null; //the name of the client player
	private static PlayerAI clientPlayerAI = null; //the ai class for the client player
    
	
    /**
     * Play some Euchre!!! follow the input parameter schema below.. (sorry for it being kinda big.)
     * <boolean verbose false = minimal true = all>
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
     * true com.fei0x.euchre.player_ai_impl.ExamplePlayerRandomAI Jeremy com.fei0x.euchre.player_ai_impl.ExamplePlayerRandomAI Jonathan com.fei0x.euchre.player_ai_impl.ExamplePlayerRandomAI Adrian com.fei0x.euchre.player_ai_impl.ExamplePlayerRandomAI Glen
     * 
     * Example joining as client
     * true client 5.5.5.5 555 444 com.fei0x.euchre.player_ai_impl.HumanPlayerAI Jeremy
     * 
     * Example host as server and one player (3 clients should join)
     * true server 555 com.fei0x.euchre.player_ai_impl.HumanPlayer Jeremy
     * 
     * Example host as server hosting with round robin and two players (2 clients should join)
     * true server 555 roundrobin 3 com.fei0x.euchre.player_ai_impl.ExamplePlayerRandomAI Jeremy com.fei0x.euchre.player_ai_impl.ExamplePlayerRandomAI Jonathan
     * 
     *
     * @param args see above
     * @throws IllegalStateException
     * @throws MissingPlayer
     */
    public static void main( String[] args ) throws IllegalStateException, MissingPlayer, UnknownHostException, RemoteException, IOException, InterruptedException
    {
        /**
         * populate variables form command line arguments
         */
        int argIndex = 0;

        verbose = Boolean.parseBoolean(args[0]);
        gameMode = args[1];

        if(gameMode.equalsIgnoreCase(GAME_MODE__CLIENT)){  //in the case where this is a client connecting to a server
             System.out.println("Running as Client");
             serverhost = args[2];
             serverport = Integer.parseInt(args[3]);
             clientport = Integer.parseInt(args[4]);

         	 System.out.println("Create Client Player:");
             clientPlayerName = args[6];
             clientPlayerAI = playerAIFactory(args[5]);
         	 System.out.println("Player: " + args[6] + " with AI: " + args[5]);
             
        }else{  //in the case where this is a server hosting a game or just a local game
            if(gameMode.equalsIgnoreCase(GAME_MODE__SERVER)){
                System.out.println("Running as Server");
                serverport = Integer.parseInt(args[2]);
                argIndex = 3;
            }else{
            	gameMode = GAME_MODE__LOCAL;
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
                	System.out.println("Create Local Players:");
                }
                
                while(args.length > argIndex +1){  // all the args left should be players, could be more than 4 if doing a round robin, or as low as 0 if running server with no players
                	localPlayers.add(makePlayer(args[argIndex], args[argIndex+1]));
                	 System.out.println("Player: " + args[argIndex+1] + " with AI: " + args[argIndex]);
                    argIndex += 2;
                }
            }
        }
        

        /**
         * Begin the Euchre Game (or Round Robin), depending on the role:
         * client - establish a connection to the server, and let the server drive the game
         * server - wait for client connections and drive the game when ready
         * local-only - run the game right away
         */
        switch (gameMode){
	        case GAME_MODE__CLIENT:
	        	
	        	EuchreClientImpl euchreClient = new EuchreClientImpl(clientPlayerName, clientPlayerAI, clientport, System.out);
	            euchreClient.connectToServer(serverhost, serverport); //this call is blocking and will end when the connection is cut (manually or accidentally)
	            
	            //The game will play out in the line above
	            
	            System.out.println("Euchre Session Ended.");
	            
	        	break;
	        
	        case GAME_MODE__SERVER:
	        
	        	System.out.println("Startup Euchre Server");
	            EuchreServerImpl euchreServer = new EuchreServerImpl(serverport,System.out);
	            ServerPrintStream sps = new ServerPrintStream(euchreServer, System.out);

	            //input mechanism for beginning the game.
	            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	            //wait for players and type go when ready
	            while(true){
	                System.out.println("Type 'go' once all players have entered to begin play.");
	                String response = in.readLine();

	                if(response.equalsIgnoreCase("go")){
	                    euchreServer.stopAcceptingPlayers();
	                    break;
	                }
	            }

	            //accumulate players
	            List<Player> allplayers = new ArrayList<Player>();
	            allplayers.addAll(localPlayers);
	            allplayers.addAll(euchreServer.getRemotePlayers());

	            //make sure there are at least 4 players. (if more than 4 the extras will only be included in the round robin
	            if(allplayers.size() < 4){
	                sps.println("Too Few Players. Enlist 4 players to play euchre. Game closing.");
	                sps.close();
	            	euchreServer.disconnectAll();
	                System.exit(0);            	
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
	        
	        	break;	        
	        case GAME_MODE__LOCAL:
	        	
	            //play Euchre
	            if(roundRobin){
	                new RoundRobin(localPlayers, roundRobinPlays, verbose, System.out).playRoundRobin();
	            }else{
	                new EuchreGame(localPlayers.get(0),localPlayers.get(1),localPlayers.get(2),localPlayers.get(3), verbose, System.out).playGame();
	            }
	            
	        	break;
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
    		player = new Player(playerName, playerAIFactory(className));
    	}catch(Exception e){
    		throw new MissingPlayer(playerName, "Could not make Player: " + playerName + "  ERROR:" + e.getMessage(), e);
        }    	
    	return player;
    }

    /**
     * Given a class name, use reflection to find the class which extends a PlayerAI abstract class
     * @param className the class to resolve
     * @return the instantiated class
     */
    private static PlayerAI playerAIFactory(String className){
    	PlayerAI playerAI = null;
    	try{
	    	Class<?> parttypes[] = {};
	        Constructor<?> ct = Class.forName(className).getConstructor(parttypes);
	        playerAI = (PlayerAI)ct.newInstance();
    	}catch(Exception e){
            try{
                //in case of the human player, it's constructor requires input and output streams
                Class<?> parttypes[] = {PrintStream.class, InputStream.class};
                Constructor<?> ct = Class.forName(className).getConstructor(parttypes);
                Object arglist[] = { System.out, System.in };
                playerAI = (PlayerAI)ct.newInstance(arglist);
            }catch(Exception e2){
                e.printStackTrace();
                e2.printStackTrace();
                throw new IllegalArgumentException("Could not make Player using PlayerAI of class '" + className + "' with a no arg constructor.", e);
            }
        }
    	return playerAI;
    }
    
}
