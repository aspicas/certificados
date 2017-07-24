package service;

import com.google.gson.Gson;
import model.Certificado;
import model.User;

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
    public static int intentos = 0;
    public static boolean status = false;
    public static Certificado cert = null;

    public static void startClient(final Socket s, final User user, final String comando){
        System.out.println("Client start");
        new Thread(){
            public void run(){
                try {
                    Gson gson = new Gson();
                    SSLSession session = ((SSLSocket) s).getSession();
//                    Certificate[] cchain = session.getPeerCertificates();
//                    System.out.println("The Certificates used by peer");
//                    for (int i = 0; i < cchain.length; i++) {
//                        System.out.println(((X509Certificate) cchain[i]).getSubjectDN());
//                    }4
//                    System.out.println("Peer host is " + session.getPeerHost());
//                    System.out.println("Cipher is " + session.getCipherSuite());
//                    System.out.println("Protocol is " + session.getProtocol());
//                    System.out.println("ID is " + new BigInteger(session.getId()));
//                    System.out.println("Session created in " + session.getCreationTime());
//                    System.out.println("Session accessed in " + session.getLastAccessedTime());

                    PrintStream comand = new PrintStream(s.getOutputStream());
                    comand.println(comando);
                    System.err.println(comando);
                    switch (comando){
                        case "ingresar":
                            PrintStream out1 = new PrintStream(s.getOutputStream());
                            out1.println(gson.toJson(user));
                            BufferedReader in1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
                            String x = in1.readLine();
                            System.out.println(x);
                            if (x.equals("No esta registrado")){
                                intentos++;
                                System.out.println("Intentos: " + intentos);
                            } else {
                                status = true;
                            }
                            break;
                        case "registrar":
                            PrintStream out2 = new PrintStream(s.getOutputStream());
                            out2.println(gson.toJson(user));
                            break;
                        case "prueba":
                            break;
                        case "generar":
                            PrintStream out4 = new PrintStream(s.getOutputStream());
                            out4.println(gson.toJson(cert));
                            status = false;
                            System.err.println("Aviso2");
                            break;
                    }

                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String x = in.readLine();
                    System.out.println(x);
//                    in.close();
//                    comand.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
