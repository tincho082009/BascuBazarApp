package com.example.bascubazarapp.modelos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CarritoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void agregarProductoCarrito(CarritoEntity carritoEntities);

    @Query("SELECT * FROM carrito_tabla")
    LiveData<List<CarritoEntity>> getProductosEnCarrito();

    @Delete
    void borrarProducto(CarritoEntity carritoEntity);

    @Query("DELETE FROM carrito_tabla")
    void borrarTodo();
}
