/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.n8id.n8euchredistributedplay;

import com.n8id.n8euchregame.AskGameImpl;
import com.n8id.n8euchregame.Card;
import com.n8id.n8euchregame.PlayerHand;
import com.n8id.n8euchregame.Suit;
import com.n8id.n8euchregame.Trick;
import com.n8id.n8euchreplayers.HumanPlayer;
import com.n8id.n8euchreplayers.Player;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author jsweetman
 */
public class EuchreClientImpl extends UnicastRemoteObject implements EuchreClient{


    /**
     * The player who is client-ing into the server euchre game.
     */
    private Player player;

    /**
     * Output for local messages
     */
    private PrintStream localout;


    /*************************
     * RMI (as a client) DATA
     ************************/

    /**
     * The server object this client tries to connect to and then communicates to regularily
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
     * The port for players to connect to this server.
     */
    private int clientPort;
    /**
     * the host for players to connect to this server.
     */
    private String clientAddr;
    /**
     * the registry for client connections via RMI
     */
     private Registry registry;


    public EuchreClientImpl(Player player,  int clientport, PrintStream localout) throws RemoteException, UnknownHostException{
        this.localout = System.out;
        this.player = player;
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
        String serveraddr ="";
        Registry serverRegistry;
        localout.println("Trying to connect to host '" + host + "' on port '" + port + "'");
        try{
            InetAddress serverinet = InetAddress.getByName(host);
            serveraddr = serverinet.getHostAddress();
            serverRegistry=LocateRegistry.getRegistry(serveraddr,port);
            server = (EuchreServer)(serverRegistry.lookup("EuchreServer"));
            localout.println("Server Found. Sending client info: " + clientAddr + " : " + clientPort + " ");
            sessionID = server.joinServer(player.getName(), clientAddr, clientPort);
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
            player.setAskGame(new AskRemoteGame(server, sessionID, player.getName()));

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
            }
        }
    }
    
    
    /**
     * Tell the client to disconnect.
     * @throws RemoteException thrown if this function cannot be called.
     */
    @Override
    public void disconnect() throws RemoteException {
        sessionID = 0;

    }


    
    
    /*******************************
     * Calls to the Player functions
     ****/

    @Override
    public boolean callItUp(Card faceUpCard) throws RemoteException {
        return player.callItUp(faceUpCard);
    }

    @Override
    public Card swapWithFaceUpCard(Card faceUpCard) throws RemoteException {
        return player.swapWithFaceUpCard(faceUpCard);
    }

    @Override
    public Suit callSuit(Card turnedDownCard) throws RemoteException {
        return player.callSuit(turnedDownCard);
    }

    @Override
    public Suit stickTheDealer(Card turnedDownCard) throws RemoteException {
        return player.stickTheDealer(turnedDownCard);
    }

    @Override
    public boolean playAlone() throws RemoteException {
        return player.playAlone();
    }

    @Override
    public Card playCard(Trick currentTrick) throws RemoteException {
        return player.playCard(currentTrick);
    }

    @Override
    public String getName() throws RemoteException {
        return player.getName();
    }

    @Override
    public void setHand(PlayerHand handCopy) throws RemoteException {
        player.setHand(handCopy);
    }



}
