/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchredistributedplay;

import com.n8id.n8euchreexceptions.MissingPlayer;
import com.n8id.n8euchregame.Trick;
import com.n8id.n8euchreplayers.Player;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jsweetman
 */
public class EuchreServerImpl extends UnicastRemoteObject implements EuchreServer  {

    /**
     * Random used for generating unique sessionIDs
     */
    private Random rnd = new Random();

    /**
     * List of players connected to this session
     */
    private ArrayList<PlayerSession> players = new ArrayList<PlayerSession>();


    /**
     * Registry that this server uses to look up clients
     */
    private Registry clientRegistry;


    /**
     * Output for local messages
     */
    private PrintStream localout;

    /**
     * Flag for accepting new player connections
     */
    private boolean acceptingNewPlayers = true;

    /*************************
     * RMI (as a server) DATA
     *************************/

    /**
     * The port for players to connect to this server.
     */
    private int serverPort;
    /**
     * the host for players to connect to this server.
     */
    private String serverAddr;
    /**
     * the registry for clients to connect to the server via RMI
     */
     private Registry registry;

    /**
     * Start up a server, add a bunch of local players to start.
     * @param players
     */
    public EuchreServerImpl(ArrayList<Player> players, int serverPort, PrintStream localout) throws RemoteException, UnknownHostException, IllegalArgumentException{
        this.localout = localout;
        this.serverPort = serverPort;
        InetAddress addr = InetAddress.getLocalHost();
        serverAddr = addr.getCanonicalHostName();
        localout.println("Provide this address '" + serverAddr + "' and port '" + serverPort + "' to clients to connect with.");
        try{
            registry = LocateRegistry.createRegistry(serverPort);
            registry.rebind("EuchreServer", this);
        }catch(RemoteException re){
            throw re;
        }
    }

    /**
     * Register a remote player to the game. while the game is running
     * @param playerName the name of the player
     * @param hostAddress the address of this player's
     * @return a session id for the player, 0 if the player was not accepted into the game. -1 if they're not allowed in because of a duplicate name
     */
    @Override
    public long joinServer(String playerName, String clientAddress, int clientPort) throws RemoteException{
        localout.println("Recieving Incoming player Request: Player '" + playerName + "' at host '" + clientAddress +"' and port '" + clientPort);
        long sessionID = 0;//return a zero if the new player is not allowed to connect.
        if(acceptingNewPlayers){//check to see if new player's are allowed to connect.

            //create a session ID
            while(sessionID == 0 || sessionID == -1){
                sessionID = rnd.nextLong()*100000;
            }

            //validate the unique, name, host+port, and sessionID
            for(PlayerSession activeplayer : players){
                if(playerName == null || playerName.equalsIgnoreCase(activeplayer.playername)) {
                    localout.println("Playername already exists");
                    return -1;
                }
                if(clientAddress.equals(activeplayer.hostAddr) && clientPort == activeplayer.port){
                    localout.println("Host and port are already active host:" + clientAddress +"' and port: '" + clientPort + "'" );
                    return 0;
                }
                if(sessionID == activeplayer.sessionID){
                    localout.println("Active Player has that SessionID: " + sessionID);
                    return 0;
                }
            }
            localout.println("Adding Player '" + playerName + "' at host '" + clientAddress +"' and port '" + clientPort + "'. SessionID: " + sessionID);

            EuchreClient client = null;
            //after validating, get a reference to it's object.
            try{
                clientRegistry=LocateRegistry.getRegistry(clientAddress,clientPort);
                client = (EuchreClient)(clientRegistry.lookup("EuchreClient"));
            }catch(RemoteException re){
                System.out.println("Remote Exception error.");
                re.printStackTrace(System.out);
            }catch(NotBoundException nbe){
                System.out.println("Remote Exception error.");
                nbe.printStackTrace(System.out);
            }

            //add new player
            PlayerSession newplayer = new PlayerSession(client, playerName, clientAddress, clientPort, sessionID);
            players.add(newplayer);
            sendMessage(newplayer, "Welcome to the Game " + playerName + ". Please wait until we enlist more players.");
            
        }
        return sessionID;
    }


    /**
     * Send a message to a specific remote player
     * @param player the player to send the message to.
     * @param message the
     */
    private void sendMessage(PlayerSession player, String message){
        try {
            player.client.tellClient(message);
        } catch (RemoteException ex) {
            ex.printStackTrace(localout);
        }
    }

    /**
     * 
     * @param message
     */
    public void sendMessageToAll(String message){
        //send out the message to each remote player
        for(PlayerSession player : players){
                sendMessage(player,message);
        }
        //then send out one local message
    }

    /**
     * used by the client to make sure the connection is still connected.
     * @throws RemoteException
     */
    public void keepAlive() throws RemoteException {
        //Keepin' it (client) alive
    }

    /**
     * Turn off the switch which lets new players in.
     */
    public void disconnectAll(String playerName){
         for(PlayerSession session : players){
             try{
                if(session.playername.equalsIgnoreCase(playerName)){
                    session.client.disconnect();
                }
             }catch(Exception e){
                e.printStackTrace(localout);
             }
         }
    }

    /**
     * Turn off the switch which lets new players in.
     */
    public void disconnectAll(){
         for(PlayerSession session : players){
             try{
                session.client.disconnect();
             }catch(Exception e){
                e.printStackTrace(localout);
             }
         }
    }

    /**
     * Turn off the switch which lets new players in.
     */
    public void stopAcceptingPlayers(){
        acceptingNewPlayers = false;
    }

    /**
     * returns the remotePlayers who were gathered by the
     * @return the players who have connected to the server.
     */
    public ArrayList<Player> getRemotePlayers(){
        ArrayList<Player> remotePlayerList = new ArrayList<Player>();
        for(PlayerSession player: players){
            remotePlayerList.add(player.player);
        }
        return remotePlayerList;
        
    }

    /**
     * Private inner class holding all the remote player sessions
     */
    private class PlayerSession{



        /**
         * the players name, must be unique
         */
        public String playername;

        /**
         * the address for connecting to this host
         */
        public String hostAddr;

        /**
         * the port for this player's interface
         */
        public int port;

        /**
         * the id demarking the players session, used like a password, distributed at login to remote players
         */
        public long sessionID;

        /**
         * The interface Client object for communicating to the client.
         */
        public EuchreClient client;

        /**
         * the player object local or remote for passing into a round robin or euchre game.
         */
        public RemotePlayer player;

        /**
         * Create a remote player session
         * @param client
         * @param playerName
         * @param hostAddr
         * @param port
         * @param sessionID
         */
        public PlayerSession(EuchreClient client, String playerName, String hostAddr, int port, long sessionID){
            this.client = client;
            this.playername = playerName;
            this.hostAddr = hostAddr;
            this.port = port;
            this.sessionID = sessionID;
            player = new RemotePlayer(playerName, client);
        }
    }


    /**
     * For incoming requests (from remote players) (game related), use the player name and session id to look up the player,
     * if the sessionID doesn't match, that means somebody else is trying to cheat... probably.
     * @param playerName the name of the player to lookup
     * @param sessionID the session id for tihs player (like a password that keeps them in the game)
     * @return the PlayerSession
     * @throws MissingPlayer if the player cannot be found, or the sessionID dosen't match.
     */
    private PlayerSession getRemotePlayerSession(String playerName, long sessionID) throws MissingPlayer{
        for(PlayerSession player : players){
            if(player.playername.equalsIgnoreCase(playerName) && sessionID == player.sessionID){
                return player;
            }
        }
        throw new MissingPlayer(playerName, "No player found with that session.");
    }


    /********************************
     * AskGame Server Interface functions
     ******************************/


    @Override
    public void speak(String playerName, long sessionID, String somethingToSay) throws RemoteException {
        try{
            PlayerSession player = getRemotePlayerSession(playerName, sessionID);
            ((RemotePlayer)(player.player)).getAskGame().speak(somethingToSay);
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public ArrayList<Trick> getPastTricks(String playerName, long sessionID) throws RemoteException, IllegalStateException {
        try{
            PlayerSession player = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayer)(player.player)).getAskGame().getPastTricks();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public int getMyTeamsScore(String playerName, long sessionID) throws RemoteException, MissingPlayer {
        try{
            PlayerSession player = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayer)(player.player)).getAskGame().getMyTeamsScore();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public int getOpponentsScore(String playerName, long sessionID) throws RemoteException {
        try{
            PlayerSession player = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayer)(player.player)).getAskGame().getOpponentsScore();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public String getPartnersName(String playerName, long sessionID) throws RemoteException {
        try{
            PlayerSession player = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayer)(player.player)).getAskGame().getPartnersName();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public ArrayList<String> getOpponents(String playerName, long sessionID) throws RemoteException {
        try{
            PlayerSession player = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayer)(player.player)).getAskGame().getOpponents();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public String whoIsDealer(String playerName, long sessionID) throws RemoteException {
        try{
            PlayerSession player = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayer)(player.player)).getAskGame().whoIsDealer();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public String getLeadPlayer(String playerName, long sessionID) throws RemoteException {
        try{
            PlayerSession player = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayer)(player.player)).getAskGame().getLeadPlayer();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }
}
