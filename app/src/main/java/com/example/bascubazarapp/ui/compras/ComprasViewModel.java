package com.example.bascubazarapp.ui.compras;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bascubazarapp.modelos.Producto;
import com.example.bascubazarapp.modelos.ProductoCompra;
import com.example.bascubazarapp.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComprasViewModel extends AndroidViewModel {
    private MutableLiveData<List<ProductoCompra>> listaProductos;
    private Context context;

    public ComprasViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<List<ProductoCompra>> getListaProducto(){
        if(listaProductos == null) {
            listaProductos = new MutableLiveData<>();
        }
        return listaProductos;
    }

    public void cargarDatos(){
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        String t = pref.getString("token", "vacio");

        Call<List<ProductoCompra>> productos = ApiClient.getMyApiClient().obtenerProductoComprado(t);
        productos.enqueue(new Callback<List<ProductoCompra>>() {
            @Override
            public void onResponse(Call<List<ProductoCompra>> call, Response<List<ProductoCompra>> response) {
                if(response.isSuccessful()){
                    List<ProductoCompra> a = response.body();
                    listaProductos.setValue(response.body());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<ProductoCompra>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
