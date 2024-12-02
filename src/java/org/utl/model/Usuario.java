
package org.utl.model;

/**
 *
 * @author corte
 */
public class Usuario {

    private int IdUsuario;
    private String usuario;
    private String password;
    private String token;
    private String Apaterno;
    private String Amaterno;
    private String nombre;
    private String rol;

    public Usuario(int IdUsuario, String usuario, String password, String token, String Apaterno, String Amaterno, String nombre, String rol) {
        this.IdUsuario = IdUsuario;
        this.usuario = usuario;
        this.password = password;
        this.token = token;
        this.Apaterno = Apaterno;
        this.Amaterno = Amaterno;
        this.nombre = nombre;
        this.rol = rol;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApaterno() {
        return Apaterno;
    }

    public void setApaterno(String Apaterno) {
        this.Apaterno = Apaterno;
    }

    public String getAmaterno() {
        return Amaterno;
    }

    public void setAmaterno(String Amaterno) {
        this.Amaterno = Amaterno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    

}
