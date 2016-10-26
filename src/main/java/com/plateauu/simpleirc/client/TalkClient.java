package com.plateauu.simpleirc.client;

import com.plateauu.simpleirc.client.repository.MessageCreator;
import com.plateauu.simpleirc.client.services.CommunicationReceiver;
import com.plateauu.simpleirc.repository.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/*
TODO  - alter user name
TODO  - prevent before ugly name handling during change name;
TODO -  remove name after kill server
*/

public class TalkClient {

    private String name;
    private final String hostname;
    private final int portNumber;
    private ObjectInputStream in;
    private ObjectOutputStream out;


    public TalkClient(String hostname, int porttNumber, String name) {
        this.hostname = hostname;
        this.portNumber = porttNumber;
        this.name = name;
        establishConnection();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void establishConnection() {
        try {
            Socket clientSocket = new Socket(hostname, portNumber);
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            in = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("[" + name + "] Connection established");

            Thread t = new Thread(new CommunicationReceiver(in, this));
            t.start();

            logInServer();
            startTalking();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logInServer() {
        try {
            Message message = new MessageCreator(this.name, "/name " + name).getMessage();
            out.writeObject(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private void startTalking() {
        String messageBody;
        Scanner scan = new Scanner(System.in);

        while (true) {
            try {
                messageBody = scan.nextLine();
                if (messageBody.length() > 0) {
                    Message message = new MessageCreator(this.name, messageBody).getMessage();
                    out.writeObject(message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }



}
