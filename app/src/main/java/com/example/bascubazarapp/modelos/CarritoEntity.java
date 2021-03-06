package com.example.bascubazarapp.modelos;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "carrito_tabla")
public class CarritoEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int idProducto;

    public int cantidad;

    public CarritoEntity(){}

    public CarritoEntity(int idProducto, int cantidad){
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getCantidad(){return cantidad;}
}
