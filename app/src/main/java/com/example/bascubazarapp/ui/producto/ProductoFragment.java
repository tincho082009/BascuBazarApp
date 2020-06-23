package com.example.bascubazarapp.ui.producto;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bascubazarapp.R;
import com.example.bascubazarapp.modelos.Producto;


public class ProductoFragment extends Fragment {
    private TextView tvDescripcion, tvPrecio, tvColor, tvObservaciones;
    private Button btnComprar, btnAgregarCarrito;
    private ProductoViewModel vm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ProductoViewModel.class);
        vm.getProducto().observe(this, new Observer<Producto>() {
            @Override
            public void onChanged(Producto producto) {
                tvDescripcion.setText(producto.getDescripcion());
                tvPrecio.setText(producto.getPrecio() + "");
                tvColor.setText(producto.getColor());
                tvObservaciones.setText(producto.getObservaciones());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_producto, container, false);
        tvDescripcion = root.findViewById(R.id.tvDescripcionProducto);
        tvPrecio = root.findViewById(R.id.tvPrecioProducto);
        tvColor = root.findViewById(R.id.tvColorProducto);
        tvObservaciones = root.findViewById(R.id.tvObservacionesProducto);
        btnComprar = root.findViewById(R.id.btnComprarProducto);
        btnAgregarCarrito = root.findViewById(R.id.btnAgregarCarrito);

        String x = getArguments().getString("id");
        vm.cargarDatos(Integer.parseInt(x));


        return root;
    }
}
