package com.plateauu.simpleirc.server.services;

import com.plateauu.simpleirc.repository.Message;
import com.plateauu.simpleirc.server.TalkServer;

public class CommandName implements Commandable {

    String actualName;
    String newName;
    TalkServer server;
    Message message;

    public CommandName(Message message, TalkServer server) {
        this.actualName = message.getName();
        this.newName = message.getCommandParameter(0);
        this.server = server;
        this.message = message;
    }

    @Override
    public Message perform() {

        int actualNameIndex = server.getUserNameIndex(actualName);

        if (!server.isUserExists(newName)) {
            if (server.isUserExists(actualName)) {
                server.getNamesList().remove(actualNameIndex);
            }
            server.addName(newName);
            message.setSenderName("Server");
            Message broadcastMessage = new Message("Server", Boolean.FALSE, this.actualName + " change name into " + this.newName);
            if (!actualName.equals(newName)) {
                server.broadcastToAll(broadcastMessage);
            }
            return message;

        } else {
            return new Message("Server", Boolean.FALSE, "Name " + newName + " is not available");
        }
    }

}
