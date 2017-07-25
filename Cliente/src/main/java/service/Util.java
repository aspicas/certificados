package service;

import com.google.gson.Gson;
import model.Certificado;
import model.MensajeDameFichero;
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
    public final static int FILE_SIZE = 6022386; // file size temporary hard coded
    // should bigger than the file to be downloaded
    public final static String
            FILE_TO_RECEIVE = "target/certs/";

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

                            String ruta = "target/certs/"+cert.getCn().split("\\s+")[0]+".cer";

                            int bytesRead;
                            int current = 0;
                            FileOutputStream fos = null;
                            BufferedOutputStream bos = null;

                            try {
                                // receive file
                                byte [] mybytearray  = new byte [FILE_SIZE];
                                InputStream is = s.getInputStream();
                                fos = new FileOutputStream(FILE_TO_RECEIVE + cert.getCn().split("\\s+")[0]+".cer");
                                bos = new BufferedOutputStream(fos);
                                bytesRead = is.read(mybytearray,0,mybytearray.length);
                                current = bytesRead;

                                do {
                                    bytesRead =
                                            is.read(mybytearray, current, (mybytearray.length-current));
                                    if(bytesRead >= 0) current += bytesRead;
                                } while(bytesRead > -1);

                                bos.write(mybytearray, 0 , current);

                                bos.flush();
                                System.out.println("File " + FILE_TO_RECEIVE + cert.getCn().split("\\s+")[0]+".cer"
                                        + " downloaded (" + current + " bytes read)");
                            }
                            finally {
                                if (fos != null) fos.close();
                                if (bos != null) bos.close();
                            }
                            /*byte[] receivedData;
                            int in;
                            String file;
                            //Buffer de 1024 bytes
                            receivedData = new byte[8192];
                            BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
                            DataInputStream dis=new DataInputStream(s.getInputStream());
                            //Recibimos el nombre del fichero
                            file = dis.readUTF();
                            String prueba = bis.toString();
                            System.err.println("Contenido del archivo");
                            System.err.println(prueba);
                            file = file.substring(file.indexOf('\\')+1,file.length());
                            System.err.println(file);
                            //Para guardar fichero recibido
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("/home/david/Downloads/" + file));
                            while ((in = bis.read(receivedData)) != -1){
                                bos.write(receivedData,0,in);
                            }*/

//                            try {
//                                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
//                                int tam = ois.readInt();
//                                FileOutputStream file = new FileOutputStream(ruta);
//                                byte[] buf = new byte[tam];
////                                while (true){
////                                    int len = ois.read(buf);
////                                    if (len == -1) break;
////                                    file.write(buf, 0, len);
////                                }
//                                for (int i = 0; i < buf.length; i++){
//                                    buf[i] = (byte)ois.read();
//
//                                }
//                                file.write(buf);
//                            } catch (StreamCorruptedException e) {
//                                e.printStackTrace();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }

                            status = false;
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
