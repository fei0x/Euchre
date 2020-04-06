/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fei0x.euchre.distributed_play;

import java.io.PrintStream;

/**
 * A PrintStream that can relay messages to Server Clients.
 * Passes all the print and println messages to the respective server clients
 * @author jsweetman
 */
public class ServerPrintStream extends PrintStream{

    /**
     * Holds the string to write if print(*) was called.
     * Cleans itself out after a println(*) is called.
     */
    private String stringToWrite = "";

    /**
     * The servers containing the clients to write to.
     */
    private EuchreServerImpl server;

    /**
     * Create a printstream that can relay messages to remote clients, but can still send messages locally.
     * @param localPrintStream the printstream to extend. it'll deal wiht local prints.
     */
    public ServerPrintStream(EuchreServerImpl server, PrintStream localPrintStream){
        super(localPrintStream);
        this.server = server;
    }

    @Override
    public void print(boolean b){
        stringToWrite += String.valueOf(b);
        super.print(b);
    }

    @Override
    public void print(char c){
        stringToWrite += String.valueOf(c);
        super.print(c);
    }

    @Override
    public void print(char[] s){
        stringToWrite += String.valueOf(s);
        super.print(s);
    }

    @Override
    public void print(double d){
        stringToWrite += String.valueOf(d);
        super.print(d);
    }

    @Override
    public void print(float f){
        stringToWrite += String.valueOf(f);
        super.print(f);
    }

    @Override
    public void print(int i){
        stringToWrite += String.valueOf(i);
        super.print(i);
    }

    @Override
    public void print(long l){
        stringToWrite += String.valueOf(l);
        super.print(l);
    }

    @Override
    public void print(Object obj){
        stringToWrite += String.valueOf(obj);
        super.print(obj);
    }

    @Override
    public void print(String s){
        stringToWrite += s;
        super.print(s);
    }


    @Override
    public void println(){
        server.sendMessageToAll(stringToWrite);
        super.println();
        stringToWrite = "";
    }

    @Override
    public void println(boolean b){
        stringToWrite += String.valueOf(b);
        server.sendMessageToAll(stringToWrite);
        super.println(b);
        stringToWrite = "";
    }

    @Override
    public void println(char c){
        stringToWrite += String.valueOf(c);
        server.sendMessageToAll(stringToWrite);
        super.println(c);
        stringToWrite = "";
    }

    @Override
    public void println(char[] s){
        stringToWrite += String.valueOf(s);
        server.sendMessageToAll(stringToWrite);
        super.println(s);
        stringToWrite = "";
    }

    @Override
    public void println(double d){
        stringToWrite += String.valueOf(d);
        server.sendMessageToAll(stringToWrite);
        super.println(d);
        stringToWrite = "";
    }

    @Override
    public void println(float f){
        stringToWrite += String.valueOf(f);
        server.sendMessageToAll(stringToWrite);
        super.println(f);
        stringToWrite = "";
    }

    @Override
    public void println(int i){
        stringToWrite += String.valueOf(i);
        server.sendMessageToAll(stringToWrite);
        super.println(i);
        stringToWrite = "";
    }

    @Override
    public void println(long l){
        stringToWrite += String.valueOf(l);
        server.sendMessageToAll(stringToWrite);
        super.println(l);
        stringToWrite = "";
    }

    @Override
    public void println(Object obj){
        stringToWrite += String.valueOf(obj);
        server.sendMessageToAll(stringToWrite);
        super.println(obj);
        stringToWrite = "";
    }

    @Override
    public void println(String s){
        stringToWrite += s;
        server.sendMessageToAll(stringToWrite);
        super.println(s);
        stringToWrite = "";
    }


}
