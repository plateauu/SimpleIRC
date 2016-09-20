package com.plateauu.talk.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

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
