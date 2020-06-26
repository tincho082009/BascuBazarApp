package com.example.bascubazarapp.ui.logout;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SalidaViewModel extends AndroidViewModel {
    private Context context;
    public SalidaViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void borrarDatos(){
        SharedPreferences preference= context.getSharedPreferences("idsDeProductos",0);
        SharedPreferences.Editor editor= preference.edit();
        editor.clear();
        editor.commit();
    }
}
