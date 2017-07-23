package service;

import model.Registry;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by david on 7/20/17.
 */
public class SocketSSL {

    private ServerSocket ss;

    public SocketSSL() throws IOException {
//        System.setProperty("javax.net.ssl.keyStore", "/home/gbsojo/aspicas/certificados/Servidor/src/main/certs/serverKey.jks");
        System.setProperty("javax.net.ssl.keyStore", SocketSSL.class.getClassLoader().getResource("serverKey.jks").getPath());
        System.setProperty("javax.net.ssl.keyStorePassword", Registry.passwordCerts);
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        ss = ssf.createServerSocket(Registry.port);
    }

    public void start(){
        Util.startServer(ss);
    }

}
