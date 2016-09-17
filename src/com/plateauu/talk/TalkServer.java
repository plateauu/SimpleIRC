package com.plateauu.talk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TalkServer {

	int port = 13333;
	BufferedReader in;
	PrintWriter out;
	String inputLine, outputLine;

	public TalkServer() {
		try {
			establishServer();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void establishServer() throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		Socket clientSocket = serverSocket.accept();
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		System.out.println("[SERVER] Connection established");
		out.println("Connection established to " + serverSocket.getInetAddress());
		startListen();
	}

	void startListen() {
		try {
		
			while ((inputLine = in.readLine()) != null) {
				System.out.println("<CLIENT> " + inputLine );
				out.println("SERVER >> " + inputLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
