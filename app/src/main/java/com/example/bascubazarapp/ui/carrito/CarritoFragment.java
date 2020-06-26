package com.example.bascubazarapp.ui.carrito;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
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

public class CarritoFragment extends Fragment {
    private ListView lv;
    private CarritoViewModel vm;
    private List<Integer> lista = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(CarritoViewModel.class);
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
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        lv = view.findViewById(R.id.lvProductosEnCarrito);
        int id = getArguments().getInt("id");
        int cantidad = getArguments().getInt("cantidad");
        vm.guardarIds(id);
        vm.cargarDatos();
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* Bundle bundle = new Bundle();
                String nose = lista.get(position) + "";
                bundle.putString("id", nose);
                Navigation.findNavController(view).navigate(R.id.nav_producto, bundle);
                */
            }
        });
        return view;
    }
}
