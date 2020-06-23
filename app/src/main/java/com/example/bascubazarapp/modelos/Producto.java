package com.example.bascubazarapp.modelos;

public class Producto {
    private int productoId;
    private double precio;
    private String color;
    private String descripcion;
    private String observaciones;


    public Producto() {
    }

    public Producto(int productoId, double precio, String color, String descripcion, String observaciones) {
        this.productoId = productoId;
        this.precio = precio;
        this.color = color;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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

}
