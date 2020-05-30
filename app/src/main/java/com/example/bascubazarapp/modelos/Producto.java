package com.example.bascubazarapp.modelos;

public class Producto {
    private int id;
    private double precio;
    private String tipo;
    private String color;
    private String descripcion;
    private String observaciones;

    public Producto(double precio, String tipo, String color, String descripcion, String observaciones) {
        this.precio = precio;
        this.tipo = tipo;
        this.color = color;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", precio=" + precio +
                ", tipo='" + tipo + '\'' +
                ", color='" + color + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
