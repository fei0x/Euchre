package com.n8id.n8euchretable;

import com.n8id.n8euchredistributedplay.EuchreClientImpl;
import com.n8id.n8euchredistributedplay.EuchreServerImpl;
import com.n8id.n8euchredistributedplay.ServerPrintStream;
import com.n8id.n8euchreexceptions.MissingPlayer;
import com.n8id.n8euchregame.EuchreGame;
import com.n8id.n8euchreplayers.Player;
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

/**
 * This is the Euchre Game Engine, call the main class with your choice of parameters to start a game.
 */
public class GameEngine
{
    /**
     * Play some Euchre!!! follow the input parameter schema below.. (sorry for it being kinda big.)
     * <int verbose 0=minimum 1=some 2=full>
     * [ client <host server> <int serverport> <int clientport> <ClassName com.yourpath.to.your.PlayerClass> <Player playername> | server <int port> ]
     * [roundrobin <int numPlays>]
     * [<ClassName playerType>  <Player playername>]
     *
     * //Note  players must have a single string constructor.
     * //Note  in local-only play the local players will be teamed in order, so that the first two players are team one, and the second two team two.
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
        ArrayList<Player> localPlayers = new ArrayList<Player>();
        Player clientPlayer = null;

        int argIndex = 0;

        verbose = Integer.parseInt(args[0]);

        //populate command line arguments
        if(args[1].equalsIgnoreCase("client")){
             client = true;
             System.out.println("Running as Client");
             serverhost = args[2];
             serverport = Integer.parseInt(args[3]);
             clientport = Integer.parseInt(args[4]);
             
             try{
                 //make client player
                 Class parttypes[] = {String.class};
                 Constructor ct = Class.forName(args[5]).getConstructor(parttypes);
                 Object arglist[] = {args[6]};
                 clientPlayer = (Player)ct.newInstance(arglist);
                 System.out.println("Preparing Client " + args[5] + " " + args[6]);
             }catch(Exception e){
                try{
                     //in case of the human constructor
                     Class parttypes[] = {String.class, PrintStream.class, InputStream.class};
                     Constructor ct = Class.forName(args[5]).getConstructor(parttypes);
                     Object arglist[] = {args[6], System.out, System.in};
                     clientPlayer = (Player)ct.newInstance(arglist);
                 }catch(Exception e2){
                     e.printStackTrace();
                     e2.printStackTrace();
                     throw new MissingPlayer(args[6], "Could not make Player of class '" + args[5] + "' with a single string constructor.", e);
                 }
             }
        }else{
            if(args[1].equalsIgnoreCase("server")){
                server = true;
                System.out.println("Running as Server");
                serverport = Integer.parseInt(args[2]);
                argIndex = 3;
            }else{
                argIndex = 1;
            }
            if(args.length > argIndex){
                if(args[argIndex].equalsIgnoreCase("roundrobin")){
                    roundRobin = true;
                    argIndex++;
                    roundRobinPlays = Integer.parseInt(args[argIndex]);
                    argIndex++;
                }
                while(args.length > argIndex +1){

                     try{
                         //make local player
                         Class parttypes[] = {String.class};
                         Constructor ct = Class.forName(args[argIndex]).getConstructor(parttypes);
                         System.out.println(ct.toGenericString());
                         System.out.println(ct.toString());
                         Object arglist[] = {args[argIndex+1]};
                         localPlayers.add((Player)ct.newInstance(arglist));
                         System.out.println("Preparing Local " + args[argIndex] + " " + args[argIndex+1]);
                     }catch(Exception e){
                        try{
                             //in case of the human constructor
                             Class parttypes[] = {String.class, PrintStream.class, InputStream.class};
                             Constructor ct = Class.forName(args[argIndex]).getConstructor(parttypes);
                             Object arglist[] = {args[argIndex+1], System.out, System.in};
                             localPlayers.add((Player)ct.newInstance(arglist));
                             System.out.println("Preparing Local " + args[argIndex] + " " + args[argIndex+1]);
                         }catch(Exception e2){
                             e.printStackTrace();
                             e2.printStackTrace();
                             throw new MissingPlayer(args[argIndex+1], "Could not make Player of class '" + args[argIndex] + "' with a single string constructor.", e);
                         }
                     }
                    argIndex += 2;
                }
            }
        }
        if(client){
            //connect to the server
            EuchreClientImpl euchreClient = new EuchreClientImpl(clientPlayer, clientport, System.out);
            euchreClient.connectToServer(serverhost, serverport);
            System.out.println("Session Ended.");


        }else if(server){
            //connect to the client
            System.out.println("Startup Euchre Server");
            EuchreServerImpl euchreServer = new EuchreServerImpl(new ArrayList<Player>(),serverport,System.out);
            ServerPrintStream sps = new ServerPrintStream(euchreServer, System.out);

            boolean waitingForPlayers = true;

            //input mechanism for begining game.
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
            ArrayList<Player> allplayers = new ArrayList<Player>();
            for(Player localPlayer : localPlayers){
                allplayers.add(localPlayer);
            }
            for(Player remotePlayer : euchreServer.getRemotePlayers()){
                allplayers.add(remotePlayer);
            }
            //make sure there are 4 players.
            if(allplayers.size() < 4){
                throw new MissingPlayer("","Too Few Players. Enlist 4 players to play euchre.");
            }

            sps.println("The game begins now.");
            Collections.shuffle(allplayers);

            //play euchre
            if(roundRobin){
                RoundRobin rr = new RoundRobin(allplayers, roundRobinPlays, verbose, sps);
                rr.playRoundRobin();
            }else{
                EuchreGame game = new EuchreGame(allplayers.get(0),allplayers.get(1),allplayers.get(2),allplayers.get(3), verbose, sps);
                game.playGame();
            }
            euchreServer.disconnectAll();
            Thread.sleep(3000);//wait 3 seconds so that they don't all crash from closing too soon.
        }else{
            //play euchre
            if(roundRobin){
                RoundRobin rr = new RoundRobin(localPlayers, roundRobinPlays, verbose, System.out);
                rr.playRoundRobin();
            }else{
                EuchreGame game = new EuchreGame(localPlayers.get(0),localPlayers.get(1),localPlayers.get(2),localPlayers.get(3), verbose, System.out);
                game.playGame();
            }
        }
        System.out.println();
        System.exit(0);
    }
}
