package com.example.bascubazarapp.ui.categorias;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bascubazarapp.modelos.Producto;

import java.util.ArrayList;
import java.util.List;

public class CategoriasViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> listaCategorias;
    public CategoriasViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<String>> getListaCategorias(){
        if(listaCategorias == null) {
            listaCategorias = new MutableLiveData<>();
        }
        return listaCategorias;
    }

    public void cargarDatos(){
        ArrayList<String> lista = new ArrayList<>();
        lista.add("Mate y accesorios");
        lista.add("Ropa y accesorios");
        lista.add("Tazas y vasos");
        lista.add("Articulos de baño y cocina");
        lista.add("Decoracion");
        lista.add("Llaveros");
        listaCategorias.setValue(lista);
    }
}
