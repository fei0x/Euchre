/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.distributed_play.client;

import com.fei0x.euchre.distributed_play.server.EuchreServer;
import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.Suit;
import com.fei0x.euchre.game.Trick;
import com.fei0x.euchre.game.PlayerAI;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class is instantiated by a Euchre Client to connect and communicate to the Euchre Server
 * @author jsweetman
 */
public class EuchreClientImpl extends UnicastRemoteObject implements EuchreClient{


    /**
	 *  Serialization ID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * Output for local messages
     */
    private PrintStream localout;

    
    
	/**
     * The AI for the player who is connecting into the server Euchre game as a client.
     * This AI is the REAL implementation
     * When the server calls the ai methods in this class, they will invoke the real ai logic in here
     */
    private PlayerAI playerAI;



    /*********************************************************
     * RMI DATA - as a client (sending calls to the server)
     *********************************************************/

    /**
     * The server object this client tries to connect to and then communicates to regularly
     */
    private EuchreServer server;

	/**
     * The name of the player, also acts as a unique ID for the connection to the server 
     * initially supplied by the client's startup arguments, but ultimately validated and assigned here by the server
     */
    private String playerName;
    
    /**
     * Session ID provided by the server.
     */
    private long sessionID = 0;
    

    /*********************************************************
     * RMI DATA - as a server (receiving calls from the server)
     *********************************************************/

    /**
     * The port for the server to connect to this client / remote ai
     */
    private int clientPort;

    /**
     * the host for the server to connect to this client / remote ai
     */
    private String clientAddr;
    
    /**
     * the registry for client connections via RMI
     */
    private Registry registry;


    /**
     * Create a new Euchre Client, this is used to connect to and communicate with the Euchre Server
     * @param playerName  the name of player client player, used as a session key with the server
     * @param playerName  the name of player client player, used as a session key with the server
     * @param clientport  the port that the client will receive calls on
     * @param localout    the location to print any setup and game information for the client
     * @throws RemoteException an exception thrown when trying to setup the client to receive calls from the server
     * @throws UnknownHostException an exception thrown if this machine's address cannot be located
     */
     public EuchreClientImpl(String playerName, PlayerAI playerAI, int clientport, PrintStream localout) throws RemoteException, UnknownHostException{
        this.localout = System.out;
        this.playerAI = playerAI;
        this.playerName = playerName;
        this.clientPort = clientport;
        InetAddress addr = InetAddress.getLocalHost();
        clientAddr = addr.getHostAddress();
        localout.println("Registering this address '" + clientAddr + "' and port '" + clientPort + "' as a client.");
        try{
            registry = LocateRegistry.createRegistry(clientPort);
            registry.rebind("EuchreClient", this);
        }catch(RemoteException re){
            throw re;
        }

    }

     

    /**
     * Let the client attempt to connect to the server.
     * This method is blocking and will only end when the connection is ended by the server, or the keep alive check fails
     * @param host the host to connect to
     * @param port the port to connect to
     */
    public void connectToServer(String host, int port) throws UnknownHostException{
        String serveraddr = "";
        Registry serverRegistry;
        localout.println("Trying to connect to host '" + host + "' on port '" + port + "'");
        try{
            //find server
        	InetAddress serverinet = InetAddress.getByName(host);
            serveraddr = serverinet.getHostAddress();
            serverRegistry = LocateRegistry.getRegistry(serveraddr,port);
            server = (EuchreServer)(serverRegistry.lookup("EuchreServer"));
            localout.println("Server Found. Sending client info: " + clientAddr + " : " + clientPort + " ");
            
            //attempt to connect, and get session
            sessionID = server.joinServer(playerName, clientAddr, clientPort);
            if(sessionID == 0 || sessionID == -1){
                if(sessionID == 0){
                    localout.println("Server declined connection.");
                }else if(sessionID == -1){
                    localout.println("Player has duplicate name, please choose a new name.");
                }
                server = null; //clear the server because you could not connect
            }
            
            //connection successful, set up player AI's communication route to the server via askGameProxy
            localout.println("Connection Successful.");
            playerAI.setAskGame(new AskGameProxy(server, playerName, sessionID));

        }catch(RemoteException re){
            localout.println("Remote Exception error.");
            localout.println("Trying to connect to host address '" + serveraddr + "' on port '" + port + "'");
            re.printStackTrace(localout);
        }catch(NotBoundException nbe){
            localout.println("Remote Exception error.");
            nbe.printStackTrace(localout);
        }

        //sit quietly while this client gets used by the server.
        while(sessionID != 0){
            try{
                Thread.sleep(1000);//wait 1 second,
                server.keepAlive();//check to see if the connection is still good.
            }catch(Exception e){
                e.printStackTrace(localout);
                sessionID = 0;
                localout.println("Client Player Disconnected.");
            }
        }
    }
    

    /*
     * Below are the methods available to the server.
     */
    
    /**
     * Print a message on the client output stream
     */
   @Override
   public void tellClient(String message) throws RemoteException {
       localout.println(message);
   }

    
    /**
     * Used by the server to tell the client to disconnect.
     * @throws RemoteException thrown if this function cannot be called.
     */
    @Override
    public void disconnect() throws RemoteException {
        sessionID = 0;
    }

    
    
    /*******************************
     * Calls to the Player functions
     * These calls are made by the server to ask the client player what they want to do
     * The server calls these using a Mock AI class: RemotePlayerAI
     *******************************/

    @Override
    public boolean callItUp(Card faceUpCard) throws RemoteException {
        return playerAI.callItUp(faceUpCard);
    }

    @Override
    public Card swapWithFaceUpCard(Card faceUpCard) throws RemoteException {
        return playerAI.swapWithFaceUpCard(faceUpCard);
    }

    @Override
    public Suit callSuit(Card turnedDownCard) throws RemoteException {
        return playerAI.callSuit(turnedDownCard);
    }

    @Override
    public Suit stickTheDealer(Card turnedDownCard) throws RemoteException {
        return playerAI.stickTheDealer(turnedDownCard);
    }

    @Override
    public boolean playAlone() throws RemoteException {
        return playerAI.playAlone();
    }

    @Override
    public Card playCard(Trick currentTrick) throws RemoteException {
        return playerAI.playCard(currentTrick);
    }




}
