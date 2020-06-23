package com.example.bascubazarapp.modelos;

public class User {
    public String Email;
    public String Clave;

    public User() {
    }

    public User(String email, String clave) {
        Email = email;
        Clave = clave;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }
}
