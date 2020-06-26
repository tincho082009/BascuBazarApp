package com.example.bascubazarapp.ui.logout;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bascubazarapp.R;

public class SalidaFragment extends Fragment {
    private View v;
    private SalidaViewModel vm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(SalidaViewModel.class);

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

}
