package model;

/**
 * Estructura del certificado que sirve para el traspaso entre cliente y servidor
 * @author David Garcia, Maria Rodriguez
 * @version 1.0.0
 */
public class Certificado {
    private String alias;
    private String cn;
    private String ou;
    private String o;
    private String l;
    private String st;
    private String c;

    /**
     * Constructor vacio
     */
    public Certificado() {
    }

    /**
     * Constructor que inicializa todos los parametros.
     * @param alias Alias
     * @param cn Common Name
     * @param ou Organization Unit
     * @param o Organization
     * @param l Locale
     * @param st State or Province
     * @param c State de manera corta Ej: VE
     */
    public Certificado(String alias, String cn, String ou, String o, String l, String st, String c) {
        this.alias = alias;
        this.cn = cn;
        this.ou = ou;
        this.o = o;
        this.l = l;
        this.st = st;
        this.c = c;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
