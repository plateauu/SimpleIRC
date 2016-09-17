package com.plateauu.talk.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.ClosedByInterruptException;
import java.util.List;

import com.plateauu.talk.server.TalkServer;

public class UserService implements Runnable {
	BufferedReader in;
	Socket clientSocket;
	TalkServer server;

	public UserService(Socket clientSocket, TalkServer server) {
		this.clientSocket = clientSocket;
		this.server = server;
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String message;
		String[] messageArray;

		try {
			while ((message = in.readLine()) != null) {
				System.out.println("Incoming message: " + message);
				messageArray = message.split("//");
				if (messageArray.length > 1) {
					server.broadcastToAll(messageArray);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
