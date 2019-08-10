package five;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

public class FTPServerWorker implements Runnable{
	private Socket clientSocket;
	private int num;
	private boolean open=true;
	private String fileName;
	public FTPServerWorker(Socket clientSocket, int num){
		this.clientSocket=clientSocket;
		this.num=num;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("worket" +num+ " has started serving the client");
		
		try {
			DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outToServer.writeBytes("hello "+clientSocket.getInetAddress()+"\n");
			outToServer.flush();

			
			while(open){
				String clientResponse=inFromClient.readLine();
				System.out.println(clientResponse);
				switch(clientResponse){
					case "USER anonymous":
						outToServer.writeBytes("specify the password\n");
						outToServer.flush();
						break;
					case "PASS":	
						outToServer.writeBytes("login successful\n");
						outToServer.flush();
						break;
					case "QUIT":
						outToServer.writeBytes("bye\n");
						outToServer.flush();
						open=false;
						break;
					case "LIST":
						File dir=new File("C:/Users/Asus-Pc/Desktop/SuperNetworkKodlarým/");
						File[] listOfFiles=dir.listFiles();
						for(File file:listOfFiles){
							if(file.isFile()){
								System.out.println(file.getName());
								String fname=file.getName();
								outToServer.writeBytes(fname+"\n");
							}
						}
						outToServer.writeBytes("end\n");
						outToServer.flush();
						break;
					case "PASV":
						int randomPort=ThreadLocalRandom.current().nextInt(5500,6000);
						String randomPortString=Integer.toString(randomPort);
						System.out.println("1");
						outToServer.writeBytes(randomPortString+"\n");
						System.out.println("2");
						//diðer portu hazýr hale geitr
						FTPServerSender threadSender=new FTPServerSender(randomPort,fileName);
						new Thread(threadSender).start();
						break;
					case "RETR":
						fileName=inFromClient.readLine();
						break;
					default:
						outToServer.writeBytes("wrong !!!!\n");
						outToServer.flush();
						open=false;
				}
			}
			
			
			clientSocket.close();
			
		/*	String clientSentence=inFromClient.readLine();
			
			if(clientSentence == "USER anonymous\n"){
				outToServer.writeBytes("specify the password");
				outToServer.flush();
			}else{
				outToServer.writeBytes("socket is going to close");
				outToServer.flush();
				clientSocket.close();
			}
			
			clientSentence=inFromClient.readLine();
			
			if(clientSentence == "PASS \n"){
				outToServer.writeBytes("login successful");
				outToServer.flush();
			}else{
				outToServer.writeBytes("login unseccful, bye");
				outToServer.flush();
				clientSocket.close();
			}*/
					
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
