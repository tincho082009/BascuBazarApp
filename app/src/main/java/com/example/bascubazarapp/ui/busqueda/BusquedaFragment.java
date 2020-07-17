package com.example.bascubazarapp.ui.busqueda;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
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

public class BusquedaFragment extends Fragment {
    private ListView lv;
    ArrayAdapter<String> adapter;
    private BusquedaViewModel vm;
    private List<Integer> lista = new ArrayList<>();
    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(BusquedaViewModel.class);
        vm.getListaProducto().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strings);
                lv.setAdapter(adapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_busqueda, container, false);
        v = root;
        lv = root.findViewById(R.id.lvListadoBusqueda);
        vm.cargarDatos();
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view;
                vm.navegar(tv.getText().toString(), view);
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem menuItem = menu.findItem(R.id.action_search);
        MenuItem menuItem2 = menu.findItem(R.id.action_carrito);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Busca aqui!!!");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        menuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", 0);
                bundle.putInt("cantidad",  0);
                Navigation.findNavController(v).navigate(R.id.nav_carrito, bundle);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}

