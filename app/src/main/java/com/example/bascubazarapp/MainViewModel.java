package com.example.bascubazarapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bascubazarapp.modelos.Usuario;
import com.example.bascubazarapp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> texto;

    public MainViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<String> getText() {
        if (texto == null) {
            texto = new MutableLiveData<String>();
        }
        return texto;
    }

    public void cargar(){
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        final String t = pref.getString("token", "vacio");

        Call<Usuario> user = ApiClient.getMyApiClient().obtenerUsuario(t);
        user.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                  texto.setValue(response.body().getEmail());
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
