package com.example.bascubazarapp.ui.compras;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bascubazarapp.R;
import com.example.bascubazarapp.adapters.ListProductoAdapter;
import com.example.bascubazarapp.adapters.ListProductoCompradoAdapter;
import com.example.bascubazarapp.modelos.ProductoCompra;

import java.util.ArrayList;
import java.util.List;

public class ComprasFragment extends Fragment {
    private ListView lv;
    private ComprasViewModel vm;
    private List<Integer> lista = new ArrayList<>();
    private View v;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ComprasViewModel.class);
        vm.getListaProducto().observe(this, new Observer<List<ProductoCompra>>() {
            @Override
            public void onChanged(List<ProductoCompra> productoCompras) {
                ArrayAdapter<ProductoCompra> adapter = new ListProductoCompradoAdapter(getContext(), R.layout.item_producto_compra, productoCompras, getLayoutInflater());
                lv.setAdapter(adapter);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_compras, container, false);
        v = view;
        lv = view.findViewById(R.id.lvListaProductosComprados);
        vm.cargarDatos();

        return view;
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
