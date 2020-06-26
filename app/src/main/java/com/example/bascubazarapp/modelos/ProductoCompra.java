package com.example.bascubazarapp.modelos;

public class ProductoCompra {
    private int productoCompraId;
    private int cantidad ;
    private double precio;
    private String descripcion;
    private int productoId;
    private int compraId;

    public ProductoCompra() {
    }

    public ProductoCompra(int productoCompraId, int cantidad, double precio, String descripcion, int productoId, int compraId) {
        this.productoCompraId = productoCompraId;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
        this.productoId = productoId;
        this.compraId = compraId;
    }

    public int getProductoCompraId() {
        return productoCompraId;
    }

    public void setProductoCompraId(int productoCompraId) {
        this.productoCompraId = productoCompraId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }
}
