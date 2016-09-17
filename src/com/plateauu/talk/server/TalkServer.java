package com.plateauu.talk.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TalkServer {

	int port = 13333;
	BufferedReader in;
	PrintWriter out;
	String inputLine, outputLine;
	List<PrintWriter> usersOutStreams;
	List<String> names;

	public TalkServer() {

		try {
			establishServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void establishServer() throws IOException {

		usersOutStreams = new ArrayList<>();
		names = new ArrayList<>();
		int counter = 0;

		ServerSocket serverSocket = new ServerSocket(port);

		while (true) {

			Socket clientSocket = serverSocket.accept();

			out = new PrintWriter(clientSocket.getOutputStream(), true);
			usersOutStreams.add(out);

			Runnable newUser = new UserService(clientSocket);
			Thread t = new Thread(newUser);
			t.setName(++counter + "user");
			t.start();

			System.out.println("[SERVER]" + t.getName() + " Connected");
			out.println("Welcome to localhost");
		}

	}

	public class UserService implements Runnable {

		Socket clientSocket;

		public UserService(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			String message;
			String[] messageArray;

			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
				while ((message = in.readLine()) != null) {
					System.out.println("Incoming message: " + message);
					
					if (message.contains("/name ")) {
						messageArray = message.split(" ");
						if (messageArray[1].length() > 0) {
							names.add(messageArray[1]);
							out.println("<Server> Welcome " + messageArray[1]);
						}
					} else {
						messageArray = message.split("//");
						if (messageArray.length > 1) {
							broadcastToAll(messageArray);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void broadcastToAll(String[] messageArray) {
		if (messageArray.length > 1) {
			for (PrintWriter userOutput : usersOutStreams) {
				userOutput.println("<" + messageArray[0] + "> " + messageArray[1]);
			}
		}

	}

}
