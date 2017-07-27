package com.master.interv.socket.chatdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {

	private static final String host = "localhost";
	private static final int port = 3923;
	private final Socket socket;

	public Client() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		System.out.println("Client is up: " + this);
	}

	public void start() {
		new MessageSender(socket).start();
		new MessageReceiver(socket).start();
	}

	class MessageSender extends Thread {

		private Socket socket;

		public MessageSender(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			BufferedReader localInputBufferedReader = null;
			PrintWriter socketOutputStreamPrintWriter = null;
			try {
				localInputBufferedReader = new BufferedReader(new InputStreamReader(System.in));
				socketOutputStreamPrintWriter = new PrintWriter(socket.getOutputStream());

				String lineRead = null;
				while ((lineRead = localInputBufferedReader.readLine()) != null) {
					socketOutputStreamPrintWriter.write(lineRead);
					socketOutputStreamPrintWriter.flush();
				}
			} catch (SocketException e) {
				System.err.println("server closed");
				System.exit(-1);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (localInputBufferedReader != null)
						localInputBufferedReader.close();
					if (socketOutputStreamPrintWriter != null)
						socketOutputStreamPrintWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	class MessageReceiver extends Thread {

		private Socket socket;

		public MessageReceiver(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			BufferedReader socketInputStreamBufferedReader = null;
			try {
				socketInputStreamBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String lineRead = null;
				while ((lineRead = socketInputStreamBufferedReader.readLine()) != null) {
					System.out.println(lineRead);
				}
			} catch (SocketException e) {
				System.err.println("server closed");
				System.exit(-1);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (socketInputStreamBufferedReader != null)
						socketInputStreamBufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			new Client().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}