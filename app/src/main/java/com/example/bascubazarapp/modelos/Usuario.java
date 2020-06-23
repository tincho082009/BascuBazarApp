package com.example.bascubazarapp.modelos;

public class Usuario {
    private int usuarioId;
    private String dni;
    private String apellido;
    private String nombre;
    private String telefono;
    private int rol;
    private String tokenNotificacion;
    private String email;
    private String clave;

    public Usuario() {
    }

    public Usuario(int usuarioId, String dni, String apellido, String nombre, String telefono, int rol, String tokenNotificacion, String email, String clave) {
        this.usuarioId = usuarioId;
        this.dni = dni;
        this.apellido = apellido;
        this.nombre = nombre;
        this.telefono = telefono;
        this.rol = rol;
        this.tokenNotificacion = tokenNotificacion;
        this.email = email;
        this.clave = clave;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getTokenNotificacion() {
        return tokenNotificacion;
    }

    public void setTokenNotificacion(String tokenNotificacion) {
        this.tokenNotificacion = tokenNotificacion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuarioId=" + usuarioId +
                ", dni='" + dni + '\'' +
                ", apellido='" + apellido + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", rol=" + rol +
                ", tokenNotificacion='" + tokenNotificacion + '\'' +
                ", email='" + email + '\'' +
                ", clave='" + clave + '\'' +
                '}';
    }
}
