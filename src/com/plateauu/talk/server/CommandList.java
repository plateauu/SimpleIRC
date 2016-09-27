package com.plateauu.talk.server;

import java.util.List;

public class CommandList implements Commandable {

    List<String> namesList;

    public CommandList(List<String> namesList) {
        this.namesList = namesList;
    }

    @Override
    public String performCommand() {
        StringBuilder list = new StringBuilder();

        list.append("commands//list//");
        for (String name : namesList) {
            list.append(name.toString());
            list.append(", ");
        }

        return list.toString();
    }

}
