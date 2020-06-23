package com.example.bascubazarapp.ui.perfil;

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

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Usuario> usuarioMutableLiveData;
    private MutableLiveData<Boolean> estado;
    private  MutableLiveData<String> textoBoton;

    private Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
    }

    public LiveData<Usuario> getPropietario(){
        if(usuarioMutableLiveData == null) {
            usuarioMutableLiveData = new MutableLiveData<>();
        }
        return usuarioMutableLiveData;
    }

    public LiveData<Boolean> getEstado(){
        if(estado == null){
            estado = new MutableLiveData<>();
        }
        return estado;
    }

    public LiveData<String> getTextoBoton(){
        if(textoBoton == null){
            textoBoton = new MutableLiveData<>();
        }
        return textoBoton;
    }


    public void rellenar(){
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        String t = pref.getString("token", "vacio");
        Call<Usuario> user = ApiClient.getMyApiClient().obtenerUsuario(t);
        user.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    usuarioMutableLiveData.setValue(response.body());
                    estado.setValue(false);
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
    public void editar(){
        if(estado.getValue()){
            textoBoton.setValue("Guardar");

        }else{
            textoBoton.setValue("Editar");
        }
    }
    public void guardar(Usuario user){
        if(estado.getValue()){
            SharedPreferences pref = context.getSharedPreferences("token", 0);
            String t = pref.getString("token", "vacio");
            Call<Usuario> propietarioActualizado = ApiClient.getMyApiClient().editarUsuario(t, user);
            propietarioActualizado.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if(response.isSuccessful()){
                        usuarioMutableLiveData.setValue(response.body());
                        Toast.makeText(context,"Guardado exitoso!!!", Toast.LENGTH_LONG).show();
                        textoBoton.setValue("Editar");
                        estado.setValue(false);
                    }else{
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }else{
            estado.setValue(true);
        }
    }
}
