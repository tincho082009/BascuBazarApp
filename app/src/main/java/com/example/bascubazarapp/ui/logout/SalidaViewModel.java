package com.example.bascubazarapp.ui.logout;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.bascubazarapp.modelos.CarritoEntity;
import com.example.bascubazarapp.modelos.CarritoRoomDatabase;
import com.example.bascubazarapp.repository.CarritoRepository;

import java.util.List;

public class SalidaViewModel extends AndroidViewModel {
    private Context context;
    private LiveData<List<CarritoEntity>> mAllCarrito;
    private CarritoRepository mRepository;
    private List<CarritoEntity> segundaLista;

    public SalidaViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CarritoRepository(application.getApplicationContext());
        context = application.getApplicationContext();
        mAllCarrito = mRepository.getAllCarritos();
    }

    public LiveData<List<CarritoEntity>> getAllCarrito() {
        return mAllCarrito;
    }

    public void cargar(List<CarritoEntity> lis){
        segundaLista = lis;
    }

    public void borrarDatos(){
        if(segundaLista != null){
            for (CarritoEntity ce:segundaLista
                 ) {
                mRepository.borrarUno(ce);
            }
        }

    }
}
