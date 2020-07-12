package com.example.bascubazarapp.modelos;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Foto implements Serializable {
    private String tipo;
    private String url;
    private Bitmap bitmap;

    public Foto() {
    }

    public Foto(String url, String tipo) {
        this.tipo = tipo;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}