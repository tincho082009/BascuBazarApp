package com.example.bascubazarapp.modelos;

import java.time.LocalDate;
import java.util.List;

public class Compra {
    private int compraId;
    private String fechaCompra;
    private int usuarioId;
    private boolean estado;
    private List<ProductoCompra> listadoProductosCompras;

    public Compra() {
    }

    public Compra(int compraId, String fechaCompra, int usuarioId, boolean estado) {
        this.compraId = compraId;
        this.fechaCompra = fechaCompra;
        this.usuarioId = usuarioId;
        this.estado = estado;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<ProductoCompra> getListadoProductosCompras() {
        return listadoProductosCompras;
    }

    public void setListadoProductosCompras(List<ProductoCompra> listadoProductosCompras) {
        this.listadoProductosCompras = listadoProductosCompras;
    }
}
