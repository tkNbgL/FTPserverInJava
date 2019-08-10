package five;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServerSender implements Runnable{
	private ServerSocket senderSocket;
	private String fileName;
	public FTPServerSender(int port,String fileName){
		this.fileName=fileName;
		try {
			senderSocket=new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Socket senderClientSocket=senderSocket.accept();
			System.out.println("client "+senderClientSocket.getInetAddress() + "baglandi" );
			BufferedReader inFromClient=new BufferedReader(new InputStreamReader(senderClientSocket.getInputStream()));
			File file=new File("C:/Users/Asus-Pc/Desktop/SuperNetworkKodlarým/"+fileName);
			FileInputStream fis=new FileInputStream(file);
			BufferedInputStream bis=new BufferedInputStream(fis);
			DataOutputStream outToClient=new DataOutputStream(senderClientSocket.getOutputStream());
			byte[] contents;
			long fileLength=file.length();
			long current=0;
			
			while(current != fileLength){
				int size=10000;
				if(fileLength - current >= size){
					current=current+size;
				}else{
					size=(int)(fileLength-current);
					current=fileLength;
				}
				
				contents=new byte[size];
				bis.read(contents,0,size);
				outToClient.write(contents);
				System.out.print("Sending file ... "+(current*100)/fileLength+"% complete!");
			}
			
			outToClient.flush();
			senderClientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
