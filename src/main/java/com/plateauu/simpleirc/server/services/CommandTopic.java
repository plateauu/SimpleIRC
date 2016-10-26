package com.plateauu.simpleirc.server.services;

import com.plateauu.simpleirc.repository.Message;
import com.plateauu.simpleirc.server.TalkServer;

import java.util.stream.Collectors;

/**
 * Created by plateauu on 25.10.16.
 */
public class CommandTopic implements Commandable {

    private final TalkServer server;
    private final Message message;

    public CommandTopic(TalkServer server, Message message) {
        this.message = message;
        this.server = server;
    }

    @Override
    public Message perform() {

        String executorName = message.getName();

        String newTopic = message
                .getCommandParameter()
                .stream()
                .map(i -> i.toString())
                .collect(Collectors.joining(" "));

        server.setChannelTopic(newTopic);
        return new Message("Server", Boolean.FALSE, executorName + " has changed topic into : [" + newTopic + "]");
    }
}
