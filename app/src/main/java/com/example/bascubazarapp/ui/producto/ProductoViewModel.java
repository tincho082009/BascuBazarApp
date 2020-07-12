package com.example.bascubazarapp.ui.producto;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bascubazarapp.modelos.Compra;
import com.example.bascubazarapp.modelos.Foto;
import com.example.bascubazarapp.modelos.Producto;
import com.example.bascubazarapp.modelos.ProductoCompra;
import com.example.bascubazarapp.request.ApiClient;

import java.net.PortUnreachableException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Producto> producto;
    private Compra c = new Compra();
    private  MutableLiveData<Foto> productoFoto;
    private ProductoCompra pc = new ProductoCompra();

    public LiveData<Producto> getProducto(){
        if(producto == null) {
            producto = new MutableLiveData<>();
        }
        return producto;
    }

    public LiveData<Foto> getProductoFoto(){
        if (productoFoto==null){
            productoFoto = new MutableLiveData<>();
        }
        return productoFoto;
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
        Call<List<Foto>> fotos = ApiClient.getMyApiClient().obtenerFotosProducto(t, id);
        fotos.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                if(response.isSuccessful()){
                    for (Foto f:response.body()) {
                        productoFoto.setValue(f);
                    }
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void comprar(ProductoCompra productoCompra){
        pc = productoCompra;
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        final String t = pref.getString("token", "vacio");

        //c.setFechaCompra(LocalDate.now());
        c.setFechaCompra("2020-06-24T16:56:00Z");
        c.setEstado(false);

        Call<Compra> comprita = ApiClient.getMyApiClient().crearCompra(t, c);
        comprita.enqueue(new Callback<Compra>() {
            @Override
            public void onResponse(Call<Compra> call, Response<Compra> response) {
                if (response.isSuccessful()){
                    c = response.body();
                    pc.setCompraId(c.getCompraId());
                    double precioCompra = pc.getPrecio() * pc.getCantidad();
                    pc.setPrecio(precioCompra);

                    Call<ProductoCompra> miProductoComprado = ApiClient.getMyApiClient().crearComprarProducto(t, pc);
                    miProductoComprado.enqueue(new Callback<ProductoCompra>() {
                        @Override
                        public void onResponse(Call<ProductoCompra> call, Response<ProductoCompra> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(context, "Compra realizada correctamente!", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ProductoCompra> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Compra> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
