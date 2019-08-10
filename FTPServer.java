package five;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import two.ConcurrentTCPworker;

public class FTPServer {
	public static void main(String[] args){
		try {
			ServerSocket serverSocket=new ServerSocket(5000);
			System.out.println("TCP server is waiting on port 5000dad");
			int num=0;
			while(true){
				Socket clientSocket=serverSocket.accept();
				System.out.println("CLÝENT" + " " + clientSocket.getInetAddress()+ ":" + clientSocket.getPort() + "is connected.");
				FTPServerWorker worker=new FTPServerWorker(clientSocket,num);
				new Thread(worker).start();
				num++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
