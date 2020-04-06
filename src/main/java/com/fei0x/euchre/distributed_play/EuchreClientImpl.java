/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.distributed_play;

import com.fei0x.euchre.game.Card;
import com.fei0x.euchre.game.Suit;
import com.fei0x.euchre.game.Trick;
import com.fei0x.euchre.game.Player;

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
     * The player who is connecting into the server Euchre game as a client.
     * This Player hosts the REAL AI implementation
     * When the server calls the ai methods in this class, they will invoke the real ai logic in here
     */
    private Player remotePlayer;

    /**
     * Output for local messages
     */
    private PrintStream localout;


    /*************************
     * RMI (as a client) DATA
     ************************/

    /**
     * The server object this client tries to connect to and then communicates to regularly
     */
    EuchreServer server;

    /**
     * Session ID provided by the server.
     */
    private long sessionID = 0;
    

    /*************************
     * RMI (as a server) DATA
     *************************/

    /**
     * The port for the server to connect to this client/ remote player
     */
    private int clientPort;

    /**
     * the host for the server to connect to this client/ remote player
     */
    private String clientAddr;
    
    /**
     * the registry for client connections via RMI
     */
     private Registry registry;


    /**
     * Create a new Euchre Client, this is used to connect to and communicate with the Euchre Server
     * @param player the player playing remotely
     * @param clientport
     * @param localout
     * @throws RemoteException
     * @throws UnknownHostException
     */
     public EuchreClientImpl(Player player, int clientport, PrintStream localout) throws RemoteException, UnknownHostException{
        this.localout = System.out;
        this.remotePlayer = player;
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
      * Print a message on the client output stream
      */
    @Override
    public void tellClient(String message) throws RemoteException {
        localout.println(message);
    }

    /**
     * Let the client attempt to connect to the server.
     * @param host the host to connect to
     * @param port the port to connect to
     */
    public void connectToServer(String host, int port) throws UnknownHostException{
        String serveraddr = "";
        Registry serverRegistry;
        localout.println("Trying to connect to host '" + host + "' on port '" + port + "'");
        try{
            InetAddress serverinet = InetAddress.getByName(host);
            serveraddr = serverinet.getHostAddress();
            serverRegistry = LocateRegistry.getRegistry(serveraddr,port);
            server = (EuchreServer)(serverRegistry.lookup("EuchreServer"));
            localout.println("Server Found. Sending client info: " + clientAddr + " : " + clientPort + " ");
            sessionID = server.joinServer(remotePlayer.getName(), clientAddr, clientPort);
            if(sessionID == 0 || sessionID == -1){
                if(sessionID == 0){
                    localout.println("Server declined connection.");
                }else if(sessionID == -1){
                    localout.println("Player has duplicate name, please choose a new name.");
                }
                server = null; //clear the server because you could not connect
            }
            //connection successful, set up player's askRemoteGame communication route
            localout.println("Connection Successful.");
            remotePlayer.setAskGame(new AskRemoteGame(server, sessionID, remotePlayer));

        }catch(RemoteException re){
            localout.println("Remote Exception error.");
            localout.println("Trying to connect to host address '" + serveraddr + "' on port '" + port + "'");
            re.printStackTrace(localout);
        }catch(NotBoundException nbe){
            localout.println("Remote Exception error.");
            nbe.printStackTrace(localout);
        }

        //sit idle/quietly while this client gets used by the server.
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
        return remotePlayer.getAi().callItUp(faceUpCard);
    }

    @Override
    public Card swapWithFaceUpCard(Card faceUpCard) throws RemoteException {
        return remotePlayer.getAi().swapWithFaceUpCard(faceUpCard);
    }

    @Override
    public Suit callSuit(Card turnedDownCard) throws RemoteException {
        return remotePlayer.getAi().callSuit(turnedDownCard);
    }

    @Override
    public Suit stickTheDealer(Card turnedDownCard) throws RemoteException {
        return remotePlayer.getAi().stickTheDealer(turnedDownCard);
    }

    @Override
    public boolean playAlone() throws RemoteException {
        return remotePlayer.getAi().playAlone();
    }

    @Override
    public Card playCard(Trick currentTrick) throws RemoteException {
        return remotePlayer.getAi().playCard(currentTrick);
    }




}
