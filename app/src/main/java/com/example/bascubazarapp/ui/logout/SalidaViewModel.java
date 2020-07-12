package com.example.bascubazarapp.ui.logout;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.Room;

import com.example.bascubazarapp.modelos.CarritoRoomDatabase;
import com.example.bascubazarapp.repository.CarritoRepository;

public class SalidaViewModel extends AndroidViewModel {
    private Context context;
    private CarritoRepository mRepository;

    public SalidaViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CarritoRepository(application);
        context = application.getApplicationContext();
    }

    public void borrarDatos(){
        SharedPreferences preference= context.getSharedPreferences("carrito",0);
        SharedPreferences.Editor editorPref= preference.edit();
        editorPref.putInt("idCarrito", 1);
        editorPref.commit();

        mRepository.borrarAll();

    }
}
