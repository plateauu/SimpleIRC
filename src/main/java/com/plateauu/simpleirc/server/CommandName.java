package com.plateauu.simpleirc.server;

import com.plateauu.simpleirc.repository.Message;

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
            return message;

        } else {
            return new Message("Server", Boolean.FALSE, "Name " + newName + " is not available");
        }
    }

}
