package com.example.bascubazarapp.ui.carrito;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bascubazarapp.R;
import com.example.bascubazarapp.adapters.ListProductoCarritoAdapter;
import com.example.bascubazarapp.modelos.CarritoEntity;

import java.util.ArrayList;
import java.util.List;


public class CarritoFragment extends Fragment {
    private RecyclerView rv;
    private CarritoViewModel vm;
    private List<CarritoEntity> carritoEntitiesNuevo = new ArrayList<>();
    private Button btnBorrar, btnComprar;
    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(CarritoViewModel.class);
        vm.getListCarritoMutable().observe(this, new Observer<List<CarritoEntity>>() {
            @Override
            public void onChanged(List<CarritoEntity> carritoEntities) {
                ListProductoCarritoAdapter adapter = new ListProductoCarritoAdapter(carritoEntities, getContext());
                adapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        //String nose = lista.get(rv.getChildAdapterPosition(v)) + "";
                        String nose = carritoEntities.get(rv.getChildAdapterPosition(v)).getIdProducto() + "";
                        bundle.putString("id", nose);
                        Navigation.findNavController(v).navigate(R.id.nav_producto, bundle);
                    }
                });
                rv.setAdapter(adapter);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        v = view;
        rv = view.findViewById(R.id.rvListaCarrito);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        int id = getArguments().getInt("id");
        int cantidad = getArguments().getInt("cantidad");
        vm.guardarDatos(id, cantidad);

        btnBorrar = view.findViewById(R.id.btnBorrarProductoCarrito);
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setTitle("Borrar")
                        .setMessage("¿Desea borrar los productos del carrito?")
                        .setCancelable(true)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                vm.borrar();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
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
        super.onCreateOptionsMenu(menu, inflater);
    }
}
