import database.DataBase;
import model.Registry;
import model.User;
import service.SocketSSL;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * Clase principal que corre el servidor.
 * @author David Garcia, Maria Rodriguez
 * @version 1.0.0
 */
public class Main {

    /**
     * Inicializa y limpia la base de datos.
     * Instancia e inicia la escucha del socket.
     * @param args argumentos vacios
     * @throws CertificateException CertificateException
     * @throws UnrecoverableKeyException UnrecoverableKeyException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws IOException IOException
     * @throws KeyManagementException KeyManagementException
     * @throws KeyStoreException KeyStoreException
     * @see DataBase
     * @see SocketSSL
     */
    public static void main(String[] args) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyManagementException, KeyStoreException {
        Map<String, User> db = DataBase.getUsers();
        db.clear();
        SocketSSL servidor = new SocketSSL();
        servidor.start();
    }
}
