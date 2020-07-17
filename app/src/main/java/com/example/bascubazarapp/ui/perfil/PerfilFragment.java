package com.example.bascubazarapp.ui.perfil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bascubazarapp.R;
import com.example.bascubazarapp.modelos.Usuario;

public class PerfilFragment extends Fragment {
    private EditText etDni, etApellido, etNombre, etTelefono, etEmail, etContrasenia;
    private Button btnEditarPerfil;
    private PerfilViewModel vm;
    private Usuario u = new Usuario();
    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(PerfilViewModel.class);

        vm.getPropietario().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario user) {
                etDni.setText(user.getDni());
                etApellido.setText(user.getApellido());
                etNombre.setText(user.getNombre());
                etTelefono.setText(user.getTelefono());
                etEmail.setText(user.getEmail());
                etContrasenia.setText(user.getClave());

                u.setUsuarioId(user.getUsuarioId());
                u.setDni(user.getDni());
                u.setApellido(user.getApellido());
                u.setNombre(user.getNombre());
                u.setTelefono(user.getTelefono());
                u.setEmail(user.getEmail());
                u.setClave(user.getClave());
            }
        });
        vm.getEstado().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                etDni.setEnabled(aBoolean);
                etApellido.setEnabled(aBoolean);
                etNombre.setEnabled(aBoolean);
                etTelefono.setEnabled(aBoolean);
                etEmail.setEnabled(aBoolean);
                etContrasenia.setEnabled(false);
            }
        });
        vm.getTextoBoton().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                btnEditarPerfil.setText(s);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_perfil, container, false);
        v = root;
        etDni = root.findViewById(R.id.etDni);
        etApellido = root.findViewById(R.id.etApellido);
        etNombre = root.findViewById(R.id.etNombre);
        etTelefono = root.findViewById(R.id.etTelefono);
        etEmail = root.findViewById(R.id.etEmailPerfil);
        etContrasenia = root.findViewById(R.id.etClavePerfil);
        btnEditarPerfil = root.findViewById(R.id.btnEditarPerfil);

        vm.rellenar();

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u.setDni(etDni.getText().toString());
                u.setApellido(etApellido.getText().toString());
                u.setNombre(etNombre.getText().toString());
                u.setTelefono(etTelefono.getText().toString());
                u.setEmail(etEmail.getText().toString());
                u.setClave(etContrasenia.getText().toString());
                vm.guardar(u);
                vm.editar();
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem menuItem = menu.findItem(R.id.action_carrito);
        MenuItem menuItem2 = menu.findItem(R.id.action_search);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 0);
                bundle.putInt("cantidad",  0);
                Navigation.findNavController(v).navigate(R.id.nav_carrito, bundle);
                return true;
            }
        });
        menuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Navigation.findNavController(v).navigate(R.id.nav_busqueda);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
