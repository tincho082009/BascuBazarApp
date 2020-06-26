package com.example.bascubazarapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bascubazarapp.modelos.Usuario;
import com.example.bascubazarapp.request.ApiClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<String> cartelEmail;
    private MutableLiveData<String> cartelContrasenia;
    private Context contexto;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        contexto = application.getApplicationContext();
    }

    public LiveData<String> getCartelEmail() {
        if (cartelEmail == null) {
            cartelEmail = new MutableLiveData<String>();
        }
        return cartelEmail;
    }

    public LiveData<String> getCartelContrasenia() {
        if (cartelContrasenia == null) {
            cartelContrasenia = new MutableLiveData<String>();
        }
        return cartelContrasenia;
    }

    public void validar(String email, String clave) {
        final int cantMail = email.length();
        final int cantContra = clave.length();
        if (cantMail <= 0) {
            cartelEmail.setValue("*Obligatorio");
        } else {
            cartelEmail.setValue("");
        }
        if (cantContra <= 0) {
            cartelContrasenia.setValue("*Obligatorio");
        } else {
            cartelEmail.setValue("");
        }
        Usuario u = new Usuario();
        u.setClave(clave);
        u.setEmail(email);
        Call<String> token = ApiClient.getMyApiClient().loginUsuario(u);
        token.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    SharedPreferences pref= contexto.getSharedPreferences("token",0);
                    SharedPreferences.Editor editor= pref.edit();
                    String t = "Bearer "+response.body();
                    editor.putString("token",t);
                    editor.commit();

                    SharedPreferences preferences= contexto.getSharedPreferences("indicador",0);
                    SharedPreferences.Editor edit= preferences.edit();
                    edit.putInt("numero",1);
                    edit.commit();

                    Intent i = new Intent(getApplication(), MainActivity.class);
                    i.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(i);
                }else{
                    try {
                        if(cantMail > 0 && cantContra >0){
                            cartelEmail.setValue(response.errorBody().string());
                            cartelContrasenia.setValue("");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }
}
