package com.example.bascubazarapp.ui.logout;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

import com.example.bascubazarapp.R;
import com.example.bascubazarapp.modelos.CarritoEntity;

import java.util.List;

public class SalidaFragment extends Fragment {
    private View v;
    private SalidaViewModel vm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(SalidaViewModel.class);
        vm.getAllCarrito().observe(this, new Observer<List<CarritoEntity>>() {
            @Override
            public void onChanged(List<CarritoEntity> carritoEntities) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_salida, container, false);
        v = root;
        notificacion();
        return root;
    }

    private void notificacion() { new AlertDialog.Builder(getContext()).setTitle("Cerrar sesion")
            .setMessage("¿Desea cerrar sesion?")
            .setCancelable(false)
            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    vm.borrarDatos();
                    System.exit(0);
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Navigation.findNavController(v).navigate(R.id.nav_home, null);
                }
            }).show();

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
