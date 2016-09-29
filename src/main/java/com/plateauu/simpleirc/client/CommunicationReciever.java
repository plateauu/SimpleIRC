package com.plateauu.simpleirc.client;

import com.plateauu.simpleirc.Message;
import java.io.ObjectInputStream;
import java.net.Socket;

public class CommunicationReciever implements Runnable {

    private ObjectInputStream in;
    private TalkClient client;

    public CommunicationReciever(ObjectInputStream in, TalkClient client, Socket clientSocket) {
        this.in = in;
        this.client = client;
    }

    @Override
    public void run() {
        Message message;
        try {
            while ((message = (Message) in.readObject()) != null) {
                boolean command = message.getIsCommand();
                if (!command) {
                    printMessage(message);
                }
                if (command) {
                    recieveCommands(message);
                }
            }
        } catch (Exception e) {
            System.out.println("Oups");
            e.printStackTrace();
        }
    }

    private void recieveCommands(Message message) {
        switch (message.getCommand()) {
            case name:
                client.setName(message.getCommandParameter(0));
                System.out.println("Switched name to " + client.getName());
                break;
            case list:
                new RecieveList(message.getCommandParameter(0));
                break;
            default:
                break;
        }
    }

    private void printMessage(Message message) {
        System.out.println("<" + message.getName() +
        "> " + message.getMessage()
    

);    

    }
}
