package com.plateauu.simpleirc.server;

import com.plateauu.simpleirc.repository.Commands;
import com.plateauu.simpleirc.repository.Message;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandList implements Commandable {

    List<String> namesList;

    public CommandList(List<String> namesList) {
        this.namesList = namesList;
    }

    @Override
    public Message perform() {
        StringBuilder list = new StringBuilder();

        for (String name : namesList) {
            list.append(name);
            list.append(", ");
        }
        
        List<String> paramList = new ArrayList<>(Arrays.asList(list.toString()));

        return new Message("Server", Boolean.TRUE, Commands.list , paramList);
    }

}
