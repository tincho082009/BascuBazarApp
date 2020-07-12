package com.example.bascubazarapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bascubazarapp.modelos.CarritoDao;
import com.example.bascubazarapp.modelos.CarritoEntity;
import com.example.bascubazarapp.modelos.CarritoRoomDatabase;

import java.util.List;

public class CarritoRepository {
    private CarritoDao mCarritoDao;
    private LiveData<List<CarritoEntity>> mAllCarritos;

    public CarritoRepository(Application application) {
        CarritoRoomDatabase db = CarritoRoomDatabase.getDatabase(application);
        mCarritoDao = db.getCarritoDao();
        mAllCarritos = mCarritoDao.getProductosEnCarrito();
    }

    public LiveData<List<CarritoEntity>> getAllCarritos() {
        return mAllCarritos;
    }

    public void insert(CarritoEntity carritoEntity) {
        CarritoRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCarritoDao.agregarProductoCarrito(carritoEntity);
        });
    }
    public void borrarAll(){
        CarritoRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCarritoDao.borrarTodo();
        });
    }
}
