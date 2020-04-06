/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.distributed_play;

import com.fei0x.euchre.exceptions.MissingPlayer;
import com.fei0x.euchre.game.Trick;
import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.Player;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
  * This class is instantiated by a Euchre Server to connect and communicate to the Euchre Client
 * @author jsweetman
 */
public class EuchreServerImpl extends UnicastRemoteObject implements EuchreServer  {

    /**
	 * ID for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Random used for generating unique sessionIDs
     */
    private Random rnd = new Random();

    /**
     * List of players connected to this session
     * Each of these contains a Player object with a STUB AI implementation 'RemotePlayerAI'
     * when the game needs to ask one of these players a question it will invoke the remote AI and make an RMI call to the client to ask the real AI the question.
     */
    private List<PlayerSession> playerSessions = new ArrayList<PlayerSession>();


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
    public EuchreServerImpl(List<Player> players, int serverPort, PrintStream localout) throws RemoteException, UnknownHostException, IllegalArgumentException{
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
     * The client calls this method remotely
     * @param newPlayerName the name of the player
     * @param hostAddress the address of this player's
     * @return a session id for the player, 0 if the player was not accepted into the game. -1 if they're not allowed in because of a duplicate name
     */
    @Override
    public long joinServer(String newPlayerName, String clientAddress, int clientPort) throws RemoteException{
        localout.println("Recieving Incoming player Request: Player '" + newPlayerName + "' at host '" + clientAddress +"' and port '" + clientPort);
        long sessionID = 0;//return a zero if the new player is not allowed to connect.
        if(acceptingNewPlayers){//check to see if new player's are allowed to connect.

            //create a session ID
            while(sessionID == 0 || sessionID == -1){
                sessionID = rnd.nextLong()*100000;
            }

            //validate the unique, name, host+port, and sessionID
            for(PlayerSession existingPlayerSession : playerSessions){
                if(newPlayerName == null || newPlayerName.equalsIgnoreCase(existingPlayerSession.remotePlayer.getName())) {
                    localout.println("Playername already exists");
                    return -1;
                }
                if(clientAddress.equals(existingPlayerSession.hostAddr) && clientPort == existingPlayerSession.port){
                    localout.println("Host and port are already active host:" + clientAddress +"' and port: '" + clientPort + "'" );
                    return 0;
                }
                if(sessionID == existingPlayerSession.sessionID){
                    localout.println("Active Player has that SessionID: " + sessionID);
                    return 0;
                }
            }
            localout.println("Adding Player '" + newPlayerName + "' at host '" + clientAddress +"' and port '" + clientPort + "'. SessionID: " + sessionID);

            EuchreClient client = null;
            
            //after validating, get a reference to it's client object.
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
            PlayerSession newplayer = new PlayerSession(client, newPlayerName, clientAddress, clientPort, sessionID);
            playerSessions.add(newplayer);
            sendMessage(newplayer, "Welcome to the Game " + newPlayerName + ". Please wait until we enlist more players.");
            
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
     * Broadcasts a message to all players
     * @param message
     */
    public void sendMessageToAll(String message){
        //send out the message to each remote player
    	playerSessions.stream().forEach(ps -> sendMessage(ps, message));
    }

    /**
     * used by the client to make sure the connection is still connected.
     * @throws RemoteException
     */
    public void keepAlive() throws RemoteException {
        //Keepin' it (client) alive
    }

    /**
     * Disconnect a specific remote player
     */
    public void disconnectPlayer(Player player){
    	PlayerSession session = playerSessions.stream().filter(ps -> ps.remotePlayer.equals(player)).findFirst().orElse(null);
    	if(session != null){
             try{
                    session.client.disconnect();
             }catch(Exception e){
                e.printStackTrace(localout);
             }
         }
    }

    /**
     * Disconnect all the remote player sessions
     */
    public void disconnectAll(){
         for(PlayerSession session : playerSessions){
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
     * returns the remotePlayers who were gathered by the server
     * @return the players who have connected to the server.
     */
    public List<Player> getRemotePlayers(){
        List<Player> remotePlayerList = new ArrayList<Player>();
        for(PlayerSession ps: playerSessions){
            remotePlayerList.add(ps.remotePlayer);
        }
        return remotePlayerList;
        
    }

    /**
     * Private inner class holding all the remote player sessions
     * which in turn holds the 'pointers' to the remote players
     */
    private class PlayerSession{

        /**
         * the address for connecting to this host
         */
        public String hostAddr;

        /**
         * the port for this player's interface
         */
        public int port;

        /**
         * the id denoting the players session, used like a password, distributed at login to remote players
         */
        public long sessionID;

        /**
         * The interface Client object for communicating to the client.
         * When we want to talk to the client we use this object.
         * If we want to tell the client to disconnect, we do that through here too
         */
        public EuchreClient client;

        /**
         * player acting remotely
         * this class hosts a shim AI for the real implementation which is on the EuchreClient class (which is instantiated by the client)
         * we get the 'AskRemoteGame' class hosted in this object to ask the player ai what they want to do. (which is slightly different than normal)
         */
        public Player remotePlayer;

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
            this.hostAddr = hostAddr;
            this.port = port;
            this.sessionID = sessionID;
            this.remotePlayer = new Player( playerName, new RemotePlayerAI(client));
        }
    }


    /**
     * For incoming requests (from remote players) (game related), we use the player and session id to look up the player,
     * we use the playerNames to establish who is talking, because that is effectively the 'id' of the player
     * if the sessionID doesn't match, that means somebody else is trying to cheat... probably.
     * @param player the player to lookup
     * @param sessionID the session id for this player (like a password that keeps them in the game)
     * @return the PlayerSession
     * @throws MissingPlayer if the player cannot be found, or the sessionID dosen't match.
     */
    private PlayerSession getRemotePlayerSession(String playerName, long sessionID) throws MissingPlayer{
    	PlayerSession ps = playerSessions.stream().filter(s -> s.remotePlayer.getName().equalsIgnoreCase(playerName) ).findFirst().orElse(null);
        if (ps == null) throw new MissingPlayer(playerName, "No player found with that session.");
        return ps;
    }


    /********************************
     * AskGame Server Interface functions
     * These are methods called by the client, when processing it's player AI logic.
     * they invoke calls on the server and correlate 1-to-1 with the interface provided by AskGame
     * they will invoke a 'REAL' AskGameImpl, rather than the stubbed AskRemoteGame, that initiated calls here
     ******************************/
    

    @Override
    public List<Card> myHand(String playerName, long sessionID) throws RemoteException {
        try{
            PlayerSession ps = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayerAI)(ps.remotePlayer.getAi())).getAskGame().myHand();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }
    
    @Override
    public String partnersName(String playerName, long sessionID) throws RemoteException {
        try{
            PlayerSession ps = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayerAI)(ps.remotePlayer.getAi())).getAskGame().partnersName();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }
    
    @Override
    public String myTeamName(String playerName, long sessionID) throws RemoteException {
        try{
            PlayerSession ps = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayerAI)(ps.remotePlayer.getAi())).getAskGame().myTeamName();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }
    
    @Override
    public String opponentsTeamName(String playerName, long sessionID) throws RemoteException {
        try{
            PlayerSession ps = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayerAI)(ps.remotePlayer.getAi())).getAskGame().opponentsTeamName();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }
    
    @Override
    public List<String> opponentsNames(String playerName, long sessionID) throws RemoteException {
        try{
            PlayerSession ps = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayerAI)(ps.remotePlayer.getAi())).getAskGame().opponentsNames();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public void speak(String playerName, long sessionID, String somethingToSay) throws RemoteException {
        try{
            PlayerSession ps = getRemotePlayerSession(playerName, sessionID);
            ((RemotePlayerAI)(ps.remotePlayer.getAi())).getAskGame().speak(somethingToSay);
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public List<Trick> pastTricks(String playerName, long sessionID) throws RemoteException, IllegalStateException {
        try{
        	PlayerSession ps = getRemotePlayerSession(playerName, sessionID);
            return ((RemotePlayerAI)(ps.remotePlayer.getAi())).getAskGame().pastTricks();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public int myTeamsScore(String playerName, long sessionID) throws RemoteException, MissingPlayer {
        try{
        	PlayerSession ps = getRemotePlayerSession(playerName, sessionID);
        	return ((RemotePlayerAI)(ps.remotePlayer.getAi())).getAskGame().myTeamsScore();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public int opponentsScore(String playerName, long sessionID) throws RemoteException {
        try{
        	PlayerSession ps = getRemotePlayerSession(playerName, sessionID);
        	return ((RemotePlayerAI)(ps.remotePlayer.getAi())).getAskGame().opponentsScore();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public String whoIsDealer(String playerName, long sessionID) throws RemoteException {
        try{
        	PlayerSession ps = getRemotePlayerSession(playerName, sessionID);
        	return ((RemotePlayerAI)(ps.remotePlayer.getAi())).getAskGame().whoIsDealer();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }

    @Override
    public String whoIsLeadPlayer(String playerName, long sessionID) throws RemoteException {
        try{
        	PlayerSession ps = getRemotePlayerSession(playerName, sessionID);
        	return ((RemotePlayerAI)(ps.remotePlayer.getAi())).getAskGame().whoIsLeadPlayer();
        }catch(MissingPlayer mp){
            throw new RemoteException("Server could not locate player:" + playerName + " with Session:" + sessionID);
        }
    }
}
