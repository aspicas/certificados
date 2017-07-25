package service;

import com.google.gson.Gson;
import database.DataBase;
import model.Certificado;
import model.Registry;
import model.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * Crea un hilo y permite manejar las peticiones del cliente.
 * @author David Garcia, Maria Rodriguez
 * @version 1.0.0
 */
public class Util {
    /**
     * Inicializa la actividad de escucha del servidor.
     * Maneja el ingreso y el registro al sistema y la generacion del certificado del cliente.
     * @param ss ServerSocket que sirve de escucha.
     * @see ServerSocket
     */
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
                                break;
                            case "prueba":
                                break;
                            case "generar":
                                BufferedReader in4 = new BufferedReader(new InputStreamReader(s.getInputStream()));
                                String json4 = in4.readLine();
                                Certificado cert = gson.fromJson(json4, Certificado.class);
                                cert.setAlias(cert.getCn().split("\\s+")[0]);
                                String gen = "-genkey " +
                                        "-dname cn="+ cert.getCn() +",ou="+ cert.getOu() +",o="+ cert.getO() +"," +
                                        "l="+ cert.getL() +",st="+ cert.getSt() +",c="+ cert.getC() +" " +
                                        "-keyalg RSA " +
                                        "-alias "+cert.getAlias()+" " +
                                        "-keystore "+ Util.class.getClassLoader().getResource("serverKey.jks").getPath() +" " +
                                        "-storepass " + Registry.passwordCerts + " " +
                                        "-keypass " + Registry.passwordCerts;
                                System.out.println(gen);
                                sun.security.tools.keytool.Main.main(gen.split("\\s+"));
                                gen = "-export " +
                                        "-keystore "+Util.class.getClassLoader().getResource("serverKey.jks").getPath()+" " +
                                        "-alias "+cert.getAlias()+" " +
                                        "-file target/certs/"+cert.getAlias()+".cer " +
                                        "-keypass "+Registry.passwordCerts+" " +
                                        "-storepass " + Registry.passwordCerts;
                                System.out.println(gen);
                                sun.security.tools.keytool.Main.main(gen.split("\\s"));



                                String ruta = "target/certs/"+cert.getAlias()+".cer";
//                                String ruta = "target/certs/telecapp.pdf";
                                File archivo = new File(ruta);
                                while (!archivo.exists()){
                                    System.err.println("El archivo aun no esta terminado");
                                    archivo = new File(ruta);
                                }

                                FileInputStream fis = null;
                                BufferedInputStream bis = null;
                                OutputStream os = null;

                                try {
                                    // send file
                                    File myFile = new File (ruta);
                                    byte [] mybytearray  = new byte [(int)myFile.length()];
                                    fis = new FileInputStream(myFile);
                                    bis = new BufferedInputStream(fis);
                                    bis.read(mybytearray,0,mybytearray.length);
                                    os = s.getOutputStream();
                                    System.out.println("Sending " + ruta + "(" + mybytearray.length + " bytes)");
                                    os.write(mybytearray,0,mybytearray.length);

                                    os.flush();
                                    System.out.println("Done.");
                                }
                                finally {
                                    if (bis != null) bis.close();
                                    if (os != null) os.close();
                                }

                                /*int con;
                                final File localFile = new File( ruta );
                                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(archivo));
                                BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());
                                //Enviamos el nombre del fichero
                                DataOutputStream dos=new DataOutputStream(s.getOutputStream());
                                dos.writeUTF(archivo.getName());
                                //Enviamos el fichero
                                byte[] byteArray = new byte[(int)archivo.length()];
                                while ((con = bis.read(byteArray)) != -1){
                                    bos.write(byteArray,0,con);
                                }*/

//                                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
//                                oos.writeInt((int) archivo.length());
//                                FileInputStream file = new FileInputStream(ruta);
//                                byte[] buf = new byte[(int) archivo.length()];
////                                while (true){
////                                    int len = file.read(buf);
////                                    if (len == -1) break;
////                                    oos.write(buf, 0, len);
////                                }
//                                for (int i = 0; i < buf.length; i++){
//                                    o.write(buf[i]);
//                                }
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

    /**
     * Imprime un conjunto de caracteres divididos por una expresion regular.
     * @param s
     */
    public static void imprimir(String s){

        for (int i = 0; i<s.split("\\s").length; i++){
            System.out.println(s.split("\\s")[i] + " " + i);
        }
    }
}
