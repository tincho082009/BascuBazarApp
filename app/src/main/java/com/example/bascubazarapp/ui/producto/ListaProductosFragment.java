package com.example.bascubazarapp.ui.producto;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bascubazarapp.R;
import com.example.bascubazarapp.adapters.ListProductoAdapter;
import com.example.bascubazarapp.modelos.Producto;

import java.util.ArrayList;
import java.util.List;


public class ListaProductosFragment extends Fragment {
    private ListView lv;
    private ListaProductosViewModel vm;
    private List<Integer> lista = new ArrayList<>();
    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ListaProductosViewModel.class);
        vm.getListaProducto().observe(this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                ArrayAdapter<Producto> adapter = new ListProductoAdapter(getContext(), R.layout.item_producto, productos, getLayoutInflater());
                lv.setAdapter(adapter);
            }
        });
        vm.getListaId().observe(this, new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                lista = integers;
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_lista_productos, container, false);
        v = view;
        lv = view.findViewById(R.id.lvListaProductos1);
        String x = getArguments().getString("id");

        vm.cargarDatos(Integer.parseInt(x));
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                String nose = lista.get(position) + "";
                bundle.putString("id", nose);
                Navigation.findNavController(view).navigate(R.id.nav_producto, bundle);

            }
        });
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
