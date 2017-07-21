package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by david on 7/21/17.
 */
public class Util {
    public static void startClient(final Socket client){
        System.out.println("Client start");
        new Thread(){
            public void run(){
                try {
                    PrintWriter output = new PrintWriter(client.getOutputStream());
                    output.println("Federico");
                    output.flush();
                    System.out.println("Federico sent");
                    BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String received = input.readLine();
                    System.out.println("Received: " + received);
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
