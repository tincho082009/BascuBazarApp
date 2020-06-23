package com.example.bascubazarapp.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bascubazarapp.modelos.Producto;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<Producto>> listaProductos;
    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Producto>> getListaProducto(){
        if(listaProductos == null) {
            listaProductos = new MutableLiveData<>();
        }
        return listaProductos;
    }

    public void cargarDatos(){
        /*ArrayList<Producto> lista = new ArrayList<>();
        lista.add(new Producto(100.0, "mate", "rosa", "Mate ddss Rosa", "ESTA LINDO"));
        lista.add(new Producto(200.0, "mate", "rosa", "Mate ssgg Rosa", "ESTA LINDO"));
        listaProductos.setValue(lista);
         */
    }
}