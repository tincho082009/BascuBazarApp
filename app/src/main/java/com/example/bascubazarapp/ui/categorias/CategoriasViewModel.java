package com.example.bascubazarapp.ui.categorias;

import android.app.Application;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bascubazarapp.modelos.Categoria;
import com.example.bascubazarapp.modelos.Producto;
import com.example.bascubazarapp.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriasViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> listaCategorias;
    private MutableLiveData<List<Integer>> listaId;
    private Context context;

    public CategoriasViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Integer>> getListaId(){
        if(listaId == null){
            listaId = new MutableLiveData<>();
        }
        return  listaId;
    }

    public LiveData<List<String>> getListaCategorias(){
        if(listaCategorias == null) {
            listaCategorias = new MutableLiveData<>();
        }
        return listaCategorias;
    }

    public void cargarDatos(){
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        String t = pref.getString("token", "vacio");
        Call<List<Categoria>> categoria = ApiClient.getMyApiClient().obtenerCategorias(t);
        categoria.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()){
                    List<String> lista = new ArrayList<>();
                    List<Integer> ids = new ArrayList<>();
                    for (Categoria c: response.body()) {
                        String nombre = c.getNombre();
                        ids.add(c.getCategoriaId());
                        lista.add(nombre);
                    }
                    listaId.setValue(ids);
                    listaCategorias.setValue(lista);
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
