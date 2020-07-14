package com.example.bascubazarapp.ui.carrito;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.bascubazarapp.modelos.CarritoRoomDatabase;
import com.example.bascubazarapp.modelos.CarritoEntity;
import com.example.bascubazarapp.modelos.Compra;
import com.example.bascubazarapp.modelos.Producto;
import com.example.bascubazarapp.modelos.ProductoCompra;
import com.example.bascubazarapp.repository.CarritoRepository;
import com.example.bascubazarapp.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarritoViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Producto>> listaProductos;
    private MutableLiveData<List<Integer>> listaId;
    private CarritoRepository mRepository;
    private LiveData<List<CarritoEntity>> mAllCarrito;
    private LiveData<Integer> mCantidadItem;
    private List<CarritoEntity> listilla2;
    private CarritoEntity carritoEntity;
    private ProductoCompra pc;

    public CarritoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CarritoRepository(application.getApplicationContext());
        context = application.getApplicationContext();
        mAllCarrito = mRepository.getAllCarritos();
        mCantidadItem = mRepository.getCantidadItems();
    }

    public LiveData<List<CarritoEntity>> getAllCarrito() {
        return mAllCarrito;
    }

    public LiveData<Integer> getCantidadItem(){ return mCantidadItem;}

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

    public void guardarDatos(int idProducto, int cant) {
        if(idProducto != 0 && cant != 0){
            CarritoEntity ca = new CarritoEntity(idProducto, cant);
            mRepository.insert(ca);
        }
    }

    public void cargarDatos(List<CarritoEntity> listilla){
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        String t = pref.getString("token", "vacio");

        if(listilla == null){
            Toast.makeText(context, "No se puede lola", Toast.LENGTH_LONG).show();
        }else{
            Call<List<Producto>> productos = ApiClient.getMyApiClient().obtenerTodosProductos(t);
            productos.enqueue(new Callback<List<Producto>>() {
                @Override
                public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                    if(response.isSuccessful()){
                        List<Producto> listita = new ArrayList<>();
                        List<Integer> ids = new ArrayList<>();
                        for (Producto p: response.body()) {
                            for (CarritoEntity ca: listilla) {
                                if(p.getProductoId() == ca.getIdProducto()){
                                    ids.add(p.getProductoId());
                                    listita.add(p);
                                }
                            }
                        }
                        listaId.setValue(ids);
                        listaProductos.setValue(listita);
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

    public void borrar(){
        mRepository.borrarAll();
    }

    public void comprar(List<CarritoEntity> listilla){
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        final String t = pref.getString("token", "vacio");

        listilla2 = listilla;

        Compra c = new Compra();
        c.setFechaCompra("2020-07-13T16:56:00Z");
        c.setEstado(false);

        Call<Compra> compras = ApiClient.getMyApiClient().crearCompra(t, c);
        compras.enqueue(new Callback<Compra>() {
            @Override
            public void onResponse(Call<Compra> call, Response<Compra> response) {
                if(response.isSuccessful()){
                    for (CarritoEntity ce:
                            listilla2) {
                        pc = new ProductoCompra();
                        pc.setCompraId(response.body().getCompraId());

                        Call<Producto> productoCall = ApiClient.getMyApiClient().obtenerProducto(t, ce.getIdProducto());
                        productoCall.enqueue(new Callback<Producto>() {
                            @Override
                            public void onResponse(Call<Producto> call, Response<Producto> response) {
                                if(response.isSuccessful()){
                                    pc.setDescripcion(response.body().getDescripcion());
                                    double precio = ce.cantidad * response.body().getPrecio();
                                    pc.setPrecio(precio);
                                    pc.setProductoId(response.body().getProductoId());
                                    pc.setCantidad(ce.getCantidad());
                                    Call<ProductoCompra> productoCompraCall = ApiClient.getMyApiClient().crearComprarProducto(t, pc);
                                    productoCompraCall.enqueue(new Callback<ProductoCompra>() {
                                        @Override
                                        public void onResponse(Call<ProductoCompra> call, Response<ProductoCompra> response) {
                                            if(response.isSuccessful()){
                                                Toast.makeText(context, "Compra realizada exitosamente!!!", Toast.LENGTH_LONG).show();
                                                mRepository.borrarAll();
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
                            public void onFailure(Call<Producto> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
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
