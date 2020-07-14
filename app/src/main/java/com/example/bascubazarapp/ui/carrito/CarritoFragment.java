package com.example.bascubazarapp.ui.carrito;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.room.Query;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bascubazarapp.R;
import com.example.bascubazarapp.adapters.ListProductoAdapter;
import com.example.bascubazarapp.modelos.CarritoEntity;
import com.example.bascubazarapp.modelos.Producto;

import java.util.ArrayList;
import java.util.List;


public class CarritoFragment extends Fragment {
    private ListView lv;
    private CarritoViewModel vm;
    private List<Integer> lista = new ArrayList<>();
    private List<CarritoEntity> carritoEntitiesNuevo = new ArrayList<>();
    private Button btnBorrar, btnComprar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(CarritoViewModel.class);
        vm.getListaProducto().observe(this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                ArrayAdapter<Producto> adapter = new ListProductoAdapter(getContext(), R.layout.item_producto, productos, getLayoutInflater());
                lv.setAdapter(adapter);
            }
        });
         vm.getAllCarrito().observe(this, new Observer<List<CarritoEntity>>() {
            @Override
            public void onChanged(List<CarritoEntity> carritoEntities) {
                carritoEntitiesNuevo = carritoEntities;
                vm.cargarDatos(carritoEntities);
            }
        });
        vm.getCantidadItem().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Toast.makeText(getContext(), integer + "", Toast.LENGTH_LONG).show();
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
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        lv = view.findViewById(R.id.lvProductosEnCarrito);
        int id = getArguments().getInt("id");
        int cantidad = getArguments().getInt("cantidad");
        vm.guardarDatos(id, cantidad);
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
        btnBorrar = view.findViewById(R.id.btnBorrarProductoCarrito);
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.borrar();
            }
        });
        btnComprar = view.findViewById(R.id.btnComprarTodo);
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vm.comprar(carritoEntitiesNuevo);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem menuItem = menu.findItem(R.id.action_carrito);
        View v =  menuItem.getActionView();
        super.onCreateOptionsMenu(menu, inflater);
    }
}
