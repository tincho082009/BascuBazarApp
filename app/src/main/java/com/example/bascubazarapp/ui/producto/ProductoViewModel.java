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

public class ProductoViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Producto> producto;

    public LiveData<Producto> getProducto(){
        if(producto == null) {
            producto = new MutableLiveData<>();
        }
        return producto;
    }

    public ProductoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void cargarDatos(int id){
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        String t = pref.getString("token", "vacio");

        Call<Producto> miProducto = ApiClient.getMyApiClient().obtenerProducto(t, id);
        miProducto.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if(response.isSuccessful()){
                    producto.setValue(response.body());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
