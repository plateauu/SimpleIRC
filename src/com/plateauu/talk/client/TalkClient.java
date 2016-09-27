package com.plateauu.talk.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// TODO: make a message object (isCommand:Boolean, messageBody:String, Command:Enum


public class TalkClient {

    private String name;
    private final String hostname;
    private final int portNumber;
    private BufferedReader in;
    private PrintWriter out;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TalkClient(String hostname, int porttNumber, String name) {
        this.hostname = hostname;
        this.portNumber = porttNumber;
        this.name = name;
        establishConnection();
    }

    private void establishConnection() {
        try {
            Socket clientSocket = new Socket(hostname, portNumber);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("[" + name + "] Connection established");

            Thread t = new Thread(new CommunicationReciever(in, this, clientSocket));
            t.start();

            logInServer();
            startTalking();

        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    private void logInServer() {
        out.println("/name " + name);

    }

    private void startTalking() {
        String message;
        Scanner scan = new Scanner(System.in);

        while (true) {
            message = scan.nextLine();
            if (message.length() > 0) {
                out.println(this.name + "//" + message);
            }
        }
    }
    
    

}
