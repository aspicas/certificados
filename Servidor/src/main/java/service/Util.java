package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by david on 7/21/17.
 */
public class Util {
    public static void startServer(final ServerSocket serverSocket){
        System.out.println("Server start");
        new Thread(){
            public void run(){
                try {
                    Socket client = serverSocket.accept();
                    System.out.println("Client accepted");
                    client.setSoLinger(true, 1000);
                    BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String recibido = input.readLine();
                    System.out.println("Recibido " + recibido);
                    PrintWriter output = new PrintWriter(client.getOutputStream());
                    output.println("Hello " + recibido);
                    output.flush();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
