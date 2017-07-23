package service;

import model.Registry;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by david on 7/21/17.
 */
public class SocketSSL {
    private Socket s;

    public SocketSSL() throws IOException {
//        System.setProperty("javax.net.ssl.trustStore", "/home/gbsojo/aspicas/certificados/Cliente/src/main/certs/clientTrustedCerts.jks");
        System.setProperty("javax.net.ssl.trustStore", SocketSSL.class.getClassLoader().getResource("clientTrustedCerts.jks").getPath());
        System.setProperty("javax.net.ssl.trustStorePassword", Registry.passwordCerts);
        SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        s = ssf.createSocket(Registry.serverAddress, Registry.port);
    }

    public void start(){
        Util.startClient(s);
    }
}
