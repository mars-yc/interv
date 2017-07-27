package com.master.interv.socket.chatdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class Server {

	private Map<String, Socket> socketMap = new HashMap<>();
	private final ServerSocket serverSocket;
	private static final int port = 3923;

	public Server() throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("Server is up: " + this);
	}

	public void monitorConnections() {
		System.out.println("Server is monitoring the connections.");
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				socketMap.put(socket.getInetAddress().getHostAddress(), socket);
				System.out.println(socket.getInetAddress().getHostAddress() + ": connected.");
				new MessageHandlerThread(socket).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class MessageHandlerThread extends Thread {

		private static final String encoding = "ISO-8859-1";
		private Socket socket;

		public MessageHandlerThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), encoding));
				String lineRead = null;
				lineRead = br.readLine();
				while (!"end".equals(lineRead)) {
					System.out.println("Received message: " + lineRead);
					lineRead = br.readLine();
				}
				System.out.println("no message, sleep");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				System.out.println("connection reset");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		try {
			new Server().monitorConnections();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}