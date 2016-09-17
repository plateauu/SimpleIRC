package com.plateauu.talk.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.plateauu.talk.domain.UserService;

public class TalkServer {

	int port = 13333;
	BufferedReader in;
	PrintWriter out;
	String inputLine, outputLine;
	List<PrintWriter> usersOutStreams;

	public TalkServer() {

		try {
			establishServer();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void establishServer() throws IOException {

		usersOutStreams = new ArrayList<>();
		
		ServerSocket serverSocket = new ServerSocket(port);

		while (true) {
			Socket clientSocket = serverSocket.accept();

			out = new PrintWriter(clientSocket.getOutputStream(), true);
			usersOutStreams.add(out);
			System.out.println();
			
			Runnable newUser = new UserService(clientSocket, this);
			Thread t = new Thread(newUser);
			t.start();

			System.out.println("[SERVER] Connection established");
			out.println("Connection established");
			

		}
	}
	
	public void broadcastToAll(String[] messageArray) {
		for (PrintWriter userOutput : usersOutStreams){
			userOutput.println("<" + messageArray[0] + "> " + messageArray[1] );
		}
	}

}
