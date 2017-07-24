import model.Certificado;
import model.Registry;
import model.User;
import service.SocketSSL;
import service.Util;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

/**
 * Created by david on 7/21/17.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        User user = new User();
        Scanner teclado = new Scanner(System.in);
        System.out.println("Cliente starts");
        while (true){
            SocketSSL s = new SocketSSL();
            if (Util.status == false){
                System.out.println("Opciones: ");
                System.out.println("1. Ingresar");
                System.out.println("2. Registrarse");
                System.out.println("3. Probar");
                switch (teclado.nextLine().toLowerCase()){
                    case "1":
                        if (Util.intentos < 3){
                            System.out.print("Nombre de usuario: ");
                            user.setUserName(teclado.nextLine().toLowerCase());
                            System.out.print("Clave: ");
                            user.setPassword(s.encriptar(teclado.nextLine().toLowerCase()));
                            s.start(user, "ingresar");
                        } else {
                            System.out.print("Usted ha agotado el numero de intentos");
                        }
                        break;
                    case "2":
                        System.out.print("Nombre de usuario: ");
                        user.setUserName(teclado.nextLine().toLowerCase());
                        System.out.print("Clave: ");
                        String pass = teclado.nextLine().toLowerCase();
                        while (pass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*(_|[^\\w])).{6,}$")){
                            System.out.println("Minimo de 6, un caracter especial y una mayuscula");
                            System.out.print("Clave: ");
                            pass = teclado.nextLine().toLowerCase();
                        }
                        user.setPassword(s.encriptar(pass));
                        s.start(user, "registrar");
                        System.out.println("Registro exitoso");
                        break;
                    case "3":

                        break;
                }
            } else {
                Certificado cert = new Certificado();
                System.out.print("Common Name: ");
                cert.setCn(teclado.nextLine().toLowerCase());
                System.out.print("Organiza Unit: ");
                cert.setOu(teclado.nextLine().toLowerCase());
                System.out.print("Organization: ");
                cert.setO(teclado.nextLine().toLowerCase());
                System.out.print("Locale: ");
                cert.setL(teclado.nextLine().toLowerCase());
                System.out.print("Country: ");
                cert.setSt(teclado.nextLine().toLowerCase());
                System.out.print("Country code: ");
                cert.setC(teclado.nextLine().toLowerCase());
            }
        }
    }
}
