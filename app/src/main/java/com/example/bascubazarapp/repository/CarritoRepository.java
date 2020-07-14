package com.example.bascubazarapp.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.bascubazarapp.modelos.CarritoDao;
import com.example.bascubazarapp.modelos.CarritoEntity;
import com.example.bascubazarapp.modelos.CarritoRoomDatabase;

import java.util.List;

public class CarritoRepository {
    private Context context;
    private CarritoDao mCarritoDao;
    private LiveData<List<CarritoEntity>> mAllCarritos;

    public CarritoRepository(Context context){
        this.context = context.getApplicationContext();
    }

    public LiveData<List<CarritoEntity>> getAllCarritos() {
        return CarritoRoomDatabase.getIntance(context).getCarritoDao().getProductosEnCarrito();
    }

    public void insert(CarritoEntity carritoEntity) {
        AsyncTask.execute(()->CarritoRoomDatabase.getIntance(context).getCarritoDao().agregarProductoCarrito(carritoEntity));
    }

    public LiveData<Integer> getCantidadItems(){
        return CarritoRoomDatabase.getIntance(context).getCarritoDao().cantidadItems();
    }

    public void borrarUno(CarritoEntity ca){
        AsyncTask.execute(()->CarritoRoomDatabase.getIntance(context).getCarritoDao().borrarProducto(ca));
    }

    public void borrarAll(){
        AsyncTask.execute(()->CarritoRoomDatabase.getIntance(context).getCarritoDao().borrarTodo());
    }
}
