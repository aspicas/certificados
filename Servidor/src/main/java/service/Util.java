package service;

import com.google.gson.Gson;
import database.DataBase;
import model.Certificado;
import model.Registry;
import model.User;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Map;

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
                        Gson gson = new Gson();
                        Map<String, User> db = DataBase.getUsers();
                        Socket s = ss.accept();
//                        SSLSession session = ((SSLSocket) s).getSession();
//                        Certificate[] cchain2 = session.getLocalCertificates();
//                        for (int i = 0; i < cchain2.length; i++) {
//                            System.out.println(((X509Certificate) cchain2[i]).getSubjectDN());
//                        }
//                        System.out.println("Peer host is " + session.getPeerHost());
//                        System.out.println("Cipher is " + session.getCipherSuite());
//                        System.out.println("Protocol is " + session.getProtocol());
//                        System.out.println("ID is " + new BigInteger(session.getId()));
//                        System.out.println("Session created in " + session.getCreationTime());
//                        System.out.println("Session accessed in " + session.getLastAccessedTime());

                        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        String comando = in.readLine();
                        System.out.println(comando);
                        switch (comando){
                            case "ingresar":
                                BufferedReader in1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
                                String json1 = in1.readLine();
                                User user1 = gson.fromJson(json1, User.class);
                                if (db.get(user1.getUserName()) != null){
                                    PrintStream out = new PrintStream(s.getOutputStream());
                                    out.println("Ingreso exitoso");
                                } else {
                                    PrintStream out = new PrintStream(s.getOutputStream());
                                    out.println("No esta registrado");
                                }
                                break;
                            case "registrar":
                                BufferedReader in2 = new BufferedReader(new InputStreamReader(s.getInputStream()));
                                String json2 = in2.readLine();
                                User user2 = gson.fromJson(json2, User.class);
                                db.put(user2.getUserName(), user2);
                                System.out.println(db.get(user2.getUserName()).getPassword());
                                break;
                            case "prueba":
                                break;
                            case "generar":
                                BufferedReader in4 = new BufferedReader(new InputStreamReader(s.getInputStream()));
                                String json4 = in4.readLine();
                                Certificado cert = gson.fromJson(json4, Certificado.class);
                                String gen = "-genkey " +
                                        "-dname \"cn="+ cert.getCn() +", ou="+ cert.getOu() +", o="+ cert.getO() +", " +
                                        "l="+ cert.getL() +", st="+ cert.getSt() +", c="+ cert.getC() +" \" " +
                                        "-keyalg RSA " +
                                        "-alias "+ cert.getAlias() +" " +
                                        "-keystore "+ Util.class.getClassLoader().getResource("serverKey.jks").getPath() +" " +
                                        "-storepass " + Registry.passwordCerts;
                                sun.security.tools.keytool.Main.main(gen.split("\\s+"));
                                break;
                        }

                        PrintStream out = new PrintStream(s.getOutputStream());
                        out.println("Final");
//                        out.close();
//                        in.close();
                        s.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
