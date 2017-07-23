package service;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * Created by david on 7/21/17.
 */
public class Util {
    public static void startClient(final Socket s){
        System.out.println("Client start");
        new Thread(){
            public void run(){
                try {
                    SSLSession session = ((SSLSocket) s).getSession();
                    Certificate[] cchain = session.getPeerCertificates();
                    System.out.println("The Certificates used by peer");
                    for (int i = 0; i < cchain.length; i++) {
                        System.out.println(((X509Certificate) cchain[i]).getSubjectDN());
                    }
                    System.out.println("Peer host is " + session.getPeerHost());
                    System.out.println("Cipher is " + session.getCipherSuite());
                    System.out.println("Protocol is " + session.getProtocol());
                    System.out.println("ID is " + new BigInteger(session.getId()));
                    System.out.println("Session created in " + session.getCreationTime());
                    System.out.println("Session accessed in " + session.getLastAccessedTime());

                    PrintStream out = new PrintStream(s.getOutputStream());
                    out.println("Hi1");

                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String x = in.readLine();
                    System.out.println(x);
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
