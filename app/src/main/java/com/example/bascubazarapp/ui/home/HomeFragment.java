package com.example.bascubazarapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.bascubazarapp.R;
import com.example.bascubazarapp.adapters.ListProductoAdapter;
import com.example.bascubazarapp.modelos.Producto;

import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel vm;
    private ListView lv;
    private ImageButton ibMates, ibTazas, ibRopa, ibMas;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(HomeViewModel.class);
        vm.getListaProducto().observe(this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                ArrayAdapter<Producto> adapter = new ListProductoAdapter(getContext(), R.layout.item_producto, productos, getLayoutInflater());
                lv.setAdapter(adapter);
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ibMates = view.findViewById(R.id.ibMates);
        ibTazas = view.findViewById(R.id.ibTazas);
        ibRopa = view.findViewById(R.id.ibRopa);
        ibMas = view.findViewById(R.id.ibCargarMas);
        ibMates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_lista_productos);
            }
        });
        ibTazas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_lista_productos);
            }
        });
        ibRopa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_lista_productos);
            }
        });
        ibMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nav_categorias);
            }
        });
        lv = view.findViewById(R.id.lvArticulosHome);
        vm.cargarDatos();
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* TextView tv = (TextView)view;
                Bundle bundle = new Bundle();
                bundle.putString("direccion", tv.getText().toString());
                Navigation.findNavController(view).navigate(R.id.nav_conenedor_inquilino, bundle);
                */
            }
        });
        return view;
    }
}
