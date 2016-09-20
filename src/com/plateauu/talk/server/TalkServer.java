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
			int actualNameIndex = getUserNameIndex(actualName);

			if (message.contains("/name ")) {
				messageArray = message.split(" ");
				String newName = messageArray[1];

				if (newName.length() > 0) {
					commandChangeName(messageArray, actualName, actualNameIndex, newName);
				}
			} else

			{
				messageArray = message.split("//");
				if (messageArray.length > 1) {
					broadcastToAll(messageArray);
				}
			}
		}

		public void commandChangeName(String[] messageArray, String actualName, int actualNameIndex, String newName) {
			int index = getUserNameIndex(newName);
			if (index == -1) {
				if (actualNameIndex != -1) {
					names.remove(actualNameIndex);
				}
				names.add(newName);
				out.println("commands//name//" + newName);

			} else {
				System.out.println("[" + actualName + "]" + "Name " + messageArray[1] + " is not available");
				out.println("[" + actualName + "]" + "Name " + messageArray[1] + " is not available");
			}
		}

		private int getUserNameIndex(String name) {
			int index = -1;
			for (String user : names) {
				if (user.equals(name))
					index = names.indexOf(user);
			}
			return index;
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
