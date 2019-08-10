/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTPClient;

/**
 *
 * @author Asus-Pc
 */
public class FTPClientmain {
    public static void main(String[] args){
       for(int i=0; i<5; i++){
           new Thread(new FTPClientt("localhost", 5000)).start();
       }
        
    }
}
