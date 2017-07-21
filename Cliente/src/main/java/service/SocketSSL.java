package service;

import model.Registry;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by david on 7/21/17.
 */
public class SocketSSL {
    private SSLSocket client;

    public SocketSSL() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        FileInputStream file = new FileInputStream(SocketSSL.class.getClassLoader().getResource("clientKey.jks").getPath());
        keyStore.load(file, Registry.passwordCerts.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, Registry.passwordCerts.toCharArray());

        KeyStore trustedStore = KeyStore.getInstance("JKS");
        FileInputStream trustedFile = new FileInputStream(SocketSSL.class.getClassLoader().getResource("clientTrustedCerts.jks").getPath());
        trustedStore.load(trustedFile, Registry.passwordCerts.toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        SSLContext sc = SSLContext.getInstance("TLS");
        TrustManager[] trustManagers = tmf.getTrustManagers();
        KeyManager[] keyManagers = kmf.getKeyManagers();
        sc.init(keyManagers, trustManagers, null);

        SSLSocketFactory ssf = sc.getSocketFactory();
        client = (SSLSocket) ssf.createSocket(Registry.serverAddress, Registry.port);
        client.startHandshake();
    }

    public void start(){
        Util.startClient(client);
    }
}
