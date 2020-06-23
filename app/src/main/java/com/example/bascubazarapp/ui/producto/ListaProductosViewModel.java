package com.example.bascubazarapp.ui.producto;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bascubazarapp.modelos.Producto;
import com.example.bascubazarapp.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Header;

public class ListaProductosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Producto>> listaProductos;
    private MutableLiveData<List<Integer>> listaId;
    private Context context;

    public ListaProductosViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<Integer>> getListaId(){
        if(listaId == null){
            listaId = new MutableLiveData<>();
        }
        return  listaId;
    }

    public LiveData<List<Producto>> getListaProducto(){
        if(listaProductos == null) {
            listaProductos = new MutableLiveData<>();
        }
        return listaProductos;
    }

    public void cargarDatos(int id){
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        String t = pref.getString("token", "vacio");

        Call<List<Producto>> productos = ApiClient.getMyApiClient().obtenerProductoXCategoria(t, id);
        productos.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if(response.isSuccessful()){
                    List<Integer> lista = new ArrayList<>();
                    for (Producto p: response.body()) {
                        lista.add(p.getProductoId());
                    }
                    listaId.setValue(lista);
                    listaProductos.setValue(response.body());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
