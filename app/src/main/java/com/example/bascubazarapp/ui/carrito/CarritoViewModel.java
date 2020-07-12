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
import com.example.bascubazarapp.modelos.Producto;
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

    public CarritoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new CarritoRepository(application);
        context = application.getApplicationContext();
        mAllCarrito = mRepository.getAllCarritos();
    }

    public LiveData<List<CarritoEntity>> getAllCarrito() {
        return mAllCarrito;
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

    public void guardarDatos(int idProducto, int cant) {
        if(idProducto != 0 && cant != 0){
            CarritoEntity ca = new CarritoEntity(idProducto, cant);
            mRepository.insert(ca);
        }
    }

    public void cargarDatos(){
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        String t = pref.getString("token", "vacio");

        List<CarritoEntity> listCarrito = mAllCarrito.getValue();
        Call<List<Producto>> productos = ApiClient.getMyApiClient().obtenerTodosProductos(t);
        productos.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if(response.isSuccessful()){
                    List<Producto> listita = new ArrayList<>();
                    for (Producto p: response.body()) {
                        for (CarritoEntity ca: listCarrito) {
                            if(p.getProductoId() == ca.getIdProducto()){
                                listita.add(p);
                            }
                        }
                    }
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
