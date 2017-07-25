package database;

import model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Maneja el almacenamiento de los usuarios en la RAM mientras el sistema esta activo.
 * Funciona como base de datos.
 * @author David Garcia, Maria Rodriguez
 * @version 1.0.0
 */
public class DataBase {
    private static Map<String, User> users = new HashMap<>();

    /**
     * Retorna todos los usuarios.
     * @return Usuario registrados en el sistema.
     * @see User
     */
    public static Map<String, User> getUsers() {
        return users;
    }

}
