package service;

import model.Registry;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Se encarga de inicializar el socketssl con el almacen de claves y su clave con su puerto de escucha.
 * @author David Garcia, Maria Rodriguez
 * @version 1.0.0
 */
public class SocketSSL {

    private ServerSocket ss;

    /**
     * Contructor vacio que inicializa el socketssl en el puerto del registry con el almacen de claves serverKey.jks
     * y su respectiva clave.
     * @throws IOException InputOutputException
     * @see Registry
     */
    public SocketSSL() throws IOException {
//        System.setProperty("javax.net.ssl.keyStore", "/home/gbsojo/aspicas/certificados/Servidor/src/main/certs/serverKey.jks");
        System.setProperty("javax.net.ssl.keyStore", SocketSSL.class.getClassLoader().getResource("serverKey.jks").getPath());
        System.setProperty("javax.net.ssl.keyStorePassword", Registry.passwordCerts);
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        ss = ssf.createServerSocket(Registry.port);
    }

    /**
     * Inicializa los servicios del servidor
     */
    public void start(){
        Util.startServer(ss);
    }

}
