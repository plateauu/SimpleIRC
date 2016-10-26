package com.plateauu.simpleirc.server.repository;

import com.plateauu.simpleirc.repository.Commands;
import com.plateauu.simpleirc.repository.Message;
import com.plateauu.simpleirc.server.TalkServer;
import com.plateauu.simpleirc.server.services.*;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by plateauu on 25.10.16.
 */
public class MessageResolver {


    private TalkServer server;
    private ObjectOutputStream out;


    private MessageResolver(TalkServer server, ObjectOutputStream out) {
        this.server = server;
        this.out = out;
    }

    public static MessageResolver getInstance(ObjectOutputStream out, TalkServer server) {
        return new MessageResolver(server, out);
    }

    public void resolveRequest(Message message) throws IOException {
        Commandable command;
        boolean isCommand = message.isCommand();
        if (isCommand) {
            Commands commandList = message.getCommand();
            switch (commandList) {
                case name:
                    command = new CommandName(message, server);
                    break;
                case list:
                    command = new CommandList(server.getNamesList());
                    break;
                case exit:
                    command = new CommandExit(server, message.getName(), out);
                    break;
                case topic:
                    command = new CommandTopic(server, message);
                    break;
                default:
                    command = new CommandNull();
                    break;
            }

            out.writeObject(command.perform());

        }

        if (!isCommand) {
            server.broadcastToAll(message);
        }


    }


}
