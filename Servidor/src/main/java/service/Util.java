package service;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * Created by david on 7/21/17.
 */
public class Util {
    public static void startServer(final ServerSocket ss){
        System.out.println("Server start");
        new Thread(){
            public void run(){
                try {
                    while (true){
                        Socket s = ss.accept();
                        SSLSession session = ((SSLSocket) s).getSession();
                        Certificate[] cchain2 = session.getLocalCertificates();
                        for (int i = 0; i < cchain2.length; i++) {
                            System.out.println(((X509Certificate) cchain2[i]).getSubjectDN());
                        }
                        System.out.println("Peer host is " + session.getPeerHost());
                        System.out.println("Cipher is " + session.getCipherSuite());
                        System.out.println("Protocol is " + session.getProtocol());
                        System.out.println("ID is " + new BigInteger(session.getId()));
                        System.out.println("Session created in " + session.getCreationTime());
                        System.out.println("Session accessed in " + session.getLastAccessedTime());

                        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        String x = in.readLine();
                        System.out.println(x);

                        PrintStream out = new PrintStream(s.getOutputStream());
                        out.println("Hi");
                        out.close();
                        s.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
