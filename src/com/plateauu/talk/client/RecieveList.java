package com.plateauu.talk.client;

public class RecieveList implements Recieveable {

    private String names;

    public RecieveList(String names) {
        this.names = names;
        performAction();
    }

    @Override
    public void performAction() {
        System.out.println("Currently on server:");
        System.out.println(names);
    }

}
