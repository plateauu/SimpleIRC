package com.plateauu.talk.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;




public class UserService implements Runnable {

	Socket clientSocket;
	BufferedReader in;
	PrintWriter out;
	TalkServer server;

	public UserService(Socket clientSocket, PrintWriter out, TalkServer server) {
		this.clientSocket = clientSocket;
		this.server = server;
		this.out = out;
	}

	@Override
	public void run() {
		String message;

		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			while ((message = in.readLine()) != null) {
				System.out.println("Incoming message: " + message);
				resolveClientRequest(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void resolveClientRequest(String message) {
		String[] messageArray = message.split("//");
		String actualName = messageArray[0];

		if (message.contains("/name ")) {
			messageArray = message.split(" ");
			String newName = messageArray[1];
			if (newName.length() > 0) {
				Commandable changeName = new CommandChangeName(messageArray, actualName, newName, server);
				out.println(changeName.makeAnAction());
			}
		} else if (message.contains("/list")){
			Commandable list = new CommandList(server.getNamesList());
			out.println(list.makeAnAction());
			
		} else {
			messageArray = message.split("//");
			if (messageArray.length > 1) {
				server.broadcastToAll(messageArray);
			}
		}
	}

}
