package model;

/**
 * Estructura de los datos que se guardaran y se transmitiran entre el cliente y el servidor del usuario.
 * @author David Garcia, Maria Rodriguez
 * @version 1.0.0
 */
public class User {

    private String userName;
    private String password;

    /**
     * Constructor vacio
     */
    public User() {
    }

    /**
     * Constructor que inicializa todos los parametros
     * @param userName Nombre del usuario
     * @param password Clave del usuario
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
