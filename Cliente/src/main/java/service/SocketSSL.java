package service;

import model.Registry;
import model.User;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;
import org.apache.commons.codec.binary.Base64;

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

    public void start(User user, String comando){
        Util.startClient(s, user, comando);
    }

    public String encriptar(String texto){
        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
        String base64EncryptedString = "";

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public String desencriptar(String textoEncriptado) throws Exception {

        String secretKey = "qualityinfosolutions"; //llave para desenciptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }
}
