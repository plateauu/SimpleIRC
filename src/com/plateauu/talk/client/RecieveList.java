package com.plateauu.talk.client;

public class RecieveList implements Recieveable {

    private String names;

    public RecieveList(String names) {
        this.names = names;
        actOnRecieve();
    }

    @Override
    public void actOnRecieve() {
        System.out.println("Currently on server:");
        System.out.println(names);
    }

}
