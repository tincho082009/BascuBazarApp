package com.example.bascubazarapp;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<String> cartelEmail;
    private MutableLiveData<String> cartelContrasenia;

    public LoginViewModel(@NonNull Application application) {
        super(application);
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

    public void validar(String mail, String contra) {
        int cantMail = mail.length();
        int cantContra = contra.length();
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
        if ((mail.equals("a@a")) && (contra.equals("a"))) {
            Intent i = new Intent(getApplication(), MainActivity.class);
            i.addFlags(FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(i);

        } else if (cantMail > 0 && cantContra > 0) {
            cartelEmail.setValue("User o pass incorrectas");
            cartelContrasenia.setValue("");
        }
    }
}
