package com.plateauu.simpleirc.client.services;

import com.plateauu.simpleirc.client.TalkClient;
import com.plateauu.simpleirc.repository.Commands;
import com.plateauu.simpleirc.repository.Message;
import com.plateauu.simpleirc.server.TalkServer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by plateauu on 21.10.16.
 */
public class CommunicationRecieverTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void ifPrintMessageMethodHasBeenTaken() throws IOException {
        Message message = new Message("PLateauu", false, "Hello World");
        TalkServer talkServer = new TalkServer();

        TalkClient talkClient = new TalkClient("localhost", 13333, "Plateauu");
        Socket localhostSocket = new Socket("localhost", 13333);
        ObjectInputStream objectInputStream = new ObjectInputStream(localhostSocket.getInputStream());

        CommunicationReciever communicationReciever = new CommunicationReciever(objectInputStream, talkClient);
        CommunicationReciever spyCR = Mockito.spy(communicationReciever);
        spyCR.run();
        Mockito.verify(spyCR).printMessage(message);



    }

    @Test
    public void ifProperCommandHasBeenChoose() throws IOException {
        List<String> params = null;
        Message message = new Message("PLateauu", true, Commands.exit, params);

        TalkClient talkClient = new TalkClient("localhost", 13333, "Plateauu");
        Socket localhostSocket = new Socket("localhost", 13333);
        ObjectInputStream objectInputStream = new ObjectInputStream(localhostSocket.getInputStream());

        CommunicationReciever communicationReciever = new CommunicationReciever(objectInputStream, talkClient);
        CommunicationReciever spyCR = Mockito.spy(communicationReciever);
        spyCR.recieveCommands(message);
        Mockito.verify(spyCR).System.out.println(getTimeStamp(message) + "Good bye");


    }

}