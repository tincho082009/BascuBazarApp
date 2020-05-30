package com.example.bascubazarapp.ui.producto;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bascubazarapp.modelos.Producto;

import java.util.ArrayList;
import java.util.List;

public class ListaProductosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Producto>> listaProductos;

    public ListaProductosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Producto>> getListaProducto(){
        if(listaProductos == null) {
            listaProductos = new MutableLiveData<>();
        }
        return listaProductos;
    }

    public void cargarDatos(){
        ArrayList<com.example.bascubazarapp.modelos.Producto> lista = new ArrayList<>();
        lista.add(new com.example.bascubazarapp.modelos.Producto(100.0, "mate", "rosa", "Mate ddss Rosa", "ESTA LINDO"));
        lista.add(new Producto(200.0, "mate", "rosa", "Mate ssgg Rosa", "ESTA LINDO"));
        listaProductos.setValue(lista);
    }

}
