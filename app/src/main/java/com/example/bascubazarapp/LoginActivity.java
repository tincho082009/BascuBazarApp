package com.example.bascubazarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView tvUsuario, tvClave;
    EditText etUsuario, etClave;
    Button btIngresar;
    LoginViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        configView();
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        vm.getCartelEmail().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvUsuario.setText(s);
                tvUsuario.setTextColor(0xffff0000);
            }
        });
        vm.getCartelContrasenia().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvClave.setText(s);
                tvClave.setTextColor(0xffff0000);
            }
        });

    }
    public void configView(){
        tvUsuario = findViewById(R.id.tvUsuarioLogin);
        tvClave = findViewById(R.id.tvClaveLogin);
        etUsuario = findViewById(R.id.etUsuarioLogin);
        etClave = findViewById(R.id.etClaveLogin);
        btIngresar = findViewById(R.id.btnIngresarLogin);
        btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.validar(etUsuario.getText().toString(), etClave.getText().toString());
            }
        });
    }
}
