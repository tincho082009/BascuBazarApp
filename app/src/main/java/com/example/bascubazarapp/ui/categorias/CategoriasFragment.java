package com.example.bascubazarapp.ui.categorias;

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
import android.widget.TextView;

import com.example.bascubazarapp.R;
import com.example.bascubazarapp.adapters.ListProductoAdapter;
import com.example.bascubazarapp.modelos.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;


public class CategoriasFragment extends Fragment {
    private CategoriasViewModel vm;
    private ListView lv;
    private List<Integer> lista = new ArrayList<>();
    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(CategoriasViewModel.class);
        vm.getListaCategorias().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strings);
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
        View view =  inflater.inflate(R.layout.fragment_categorias, container, false);
        v = view;
        lv = view.findViewById(R.id.listaCategorias);
        vm.cargarDatos();
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                String x = lista.get(position) + "";
                bundle.putString("id", x);
                Navigation.findNavController(view).navigate(R.id.nav_lista_productos, bundle);
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
