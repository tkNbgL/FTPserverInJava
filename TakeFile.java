/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTPClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus-Pc
 */
public class TakeFile {
    private Socket receiveSocket;
    
    public TakeFile(int port){
        try {
            receiveSocket=new Socket("localhost",port);
        } catch (IOException ex) {
            Logger.getLogger(TakeFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void takeIt(){
        try {
            InputStream is =receiveSocket.getInputStream();
            BufferedInputStream bis=new BufferedInputStream(is);
            byte[] contents=new byte[10000];
            FileOutputStream fos=new FileOutputStream("dede.txt");
            BufferedOutputStream bos=new BufferedOutputStream(fos);
             int bytesRead=0;
            
            while((bytesRead=bis.read(contents))!=-1){
                bos.write(contents, 0, bytesRead);
            }
            
            bos.flush();
            receiveSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(TakeFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
