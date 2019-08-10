/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTPClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus-Pc
 */
public class FTPClientt implements Runnable{
    Socket clientSocket;	
    
    public FTPClientt(String addr,int port){
        try {
            clientSocket=new Socket(addr,5000);
        } catch (IOException ex) {
            Logger.getLogger(FTPClientt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
		

    @Override
    public void run() {
       
        try {
                   
                        String str=null;
                        
                        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        DataOutputStream outToServer=new DataOutputStream(clientSocket.getOutputStream());
                        str=inFromServer.readLine();
                        System.out.println("S >>> C: " + str);
                        
                        
                        
                        str="USER anonymous\n";
                        System.out.println("C >>> S: " +str);
                        outToServer.writeBytes(str);
                        outToServer.flush();
                        
                        str=inFromServer.readLine();
                        System.out.println("S >>> C: "+str);
                        
                        str="PASS\n";
                        System.out.println("C >>> S: "+str);
                        outToServer.writeBytes(str);
                        outToServer.flush();
                        
                        str=inFromServer.readLine();
                        System.out.println("S >>> C: "+str);
                        
                        str="LIST\n";
                        System.out.println("C >>> S: "+str);
                        outToServer.writeBytes(str);
                        outToServer.flush();
                       
                        
                       
                        while(!"end".equals(str=inFromServer.readLine())){
                           
                            System.out.println("S >>> C: "+str);
                        }
                        
                        str="RETR\n";
                        System.out.println("C>>>S: "+str);
                        outToServer.writeBytes(str);
                        outToServer.writeBytes("utku.txt\n");
                        outToServer.flush();
                        
                        str="PASV\n";
                        System.out.println("C >>> S: "+str);
                        outToServer.writeBytes(str);
                        outToServer.flush();
                        
                        str=inFromServer.readLine();
                        System.out.println(str);
                        String portNumber=str;
                        int port=Integer.parseInt(portNumber);
                        TakeFile takeit=new TakeFile(port);
                        takeit.takeIt();
                        
                        str="QUIT\n";
                        System.out.println("C >>> S: "+str);
                        outToServer.writeBytes(str);
                        outToServer.flush();
                        
                        str=inFromServer.readLine();
                        System.out.println("S >>> C: "+str);
                        
                        while((str=inFromServer.readLine()) != null){
                            str=inFromServer.readLine();
                            System.out.println("S >>> C: "+str);
                        }
                    
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
}
