package com.master.interv.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * telnet localhost 80
 *
 * This demo is not yet working
 */
public class SocketDemo {

	public static void main(String[] args) {
		try {
			new Server().run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class Server {

		private static final int port = 80;
		private static final String ENCODING = "ISO-8859-1";
		private boolean shutdownRequested = false;

		public void run() throws IOException {
			ServerSocket ss = new ServerSocket(port);
			try {
				while (!shutdownRequested) {
					Socket s = ss.accept();
					try {
						process(s);
					} finally {
						s.close();
					}
				}
			} finally {
				ss.close();
			}
		}

		private void process(Socket s) throws IOException {
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, ENCODING));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(os), ENCODING));

			String line = br.readLine();
			int ixSpace = line.indexOf(' ');
			try {
				if (ixSpace == -1)
					throw new Exception("Illegal syntax");
				String command = line.substring(0, ixSpace).trim();
				double d = Double.parseDouble(line.substring(ixSpace).trim());
				double ret;
				if("SQRT".equalsIgnoreCase(command))
					ret = Math.sqrt(d);
				else if("SIN".equalsIgnoreCase(command))
					ret = Math.sin(d);
				else
					throw new Exception("unrecognized command: " + command);
				pw.println(ret);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}