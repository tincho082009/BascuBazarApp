package com.example.bascubazarapp.ui.busqueda;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

public class BusquedaFragment extends Fragment {
    private ListView lv;
    ArrayList<String> lista = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_busqueda, container, false);
        lv = root.findViewById(R.id.lvListadoBusqueda);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view;
                Navigation.findNavController(view).navigate(R.id.nav_lista_productos);
            }
        });
        lista.add("ASDASD");
        lista.add("Nose");
        lista.add("Dinosaurio");
        lista.add("Pomelo");
        lista.add("Porteñito");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, lista);
        lv.setAdapter(adapter);
        return root;
    }
}

