package com.example.bascubazarapp.ui.carrito;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bascubazarapp.modelos.Producto;
import com.example.bascubazarapp.request.ApiClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarritoViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Producto>> listaProductos;
    private MutableLiveData<List<Integer>> listaId;
    private List<String> listadito;
    private Set<String> set = new HashSet<>();

    public CarritoViewModel(@NonNull Application application) {
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

    public void guardarIds(int id) {
        SharedPreferences indicador = context.getSharedPreferences("indicador", 0);
        int numero = indicador.getInt("numero", 0);

        if(numero == 1){
            SharedPreferences pref= context.getSharedPreferences("idsDeProductos",0);
            SharedPreferences.Editor editor= pref.edit();
            set.add(id +"");
            editor.putStringSet("setIds", set);
            editor.commit();

            SharedPreferences indicadorNuevo= context.getSharedPreferences("indicador",0);
            SharedPreferences.Editor editarIndicador= indicadorNuevo.edit();
            editarIndicador.putInt("numero",0);
            editarIndicador.commit();

        }else{
            Set<String> error = new HashSet<>();
            SharedPreferences prefIdsProductos = context.getSharedPreferences("idsDeProductos", 0);
            set = prefIdsProductos.getStringSet("setIds", error);

            SharedPreferences preference= context.getSharedPreferences("idsDeProductos",0);
            SharedPreferences.Editor editorPref= preference.edit();
            set.add(id +"");
            editorPref.putStringSet("setIdsYCant", set);
            editorPref.commit();
        }
    }

    public void cargarDatos(){
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        String t = pref.getString("token", "vacio");

        Call<List<Producto>> listitaProductos = ApiClient.getMyApiClient().obtenerTodosProductos(t);
        listitaProductos.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if(response.isSuccessful()){
                    List<Producto> listaRenovada = new ArrayList<>();
                    List<Integer> lista = new ArrayList<>();
                    for (Producto p: response.body()) {
                        lista.add(p.getProductoId());
                        listadito = new ArrayList<>(set);
                        for (int i = 0; i < listadito.size(); i++) {
                            int ids = Integer.parseInt(listadito.get(i));
                            if(ids == p.getProductoId()){
                                listaRenovada.add(p);
                            }
                        }
                    }
                    listaId.setValue(lista);
                    listaProductos.setValue(listaRenovada);
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
