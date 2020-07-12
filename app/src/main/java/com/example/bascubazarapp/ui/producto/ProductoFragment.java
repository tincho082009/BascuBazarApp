package com.example.bascubazarapp.ui.producto;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bascubazarapp.R;
import com.example.bascubazarapp.modelos.Foto;
import com.example.bascubazarapp.modelos.Producto;
import com.example.bascubazarapp.modelos.ProductoCompra;

import java.util.List;


public class ProductoFragment extends Fragment {
    private ImageView foto;
    private TextView tvDescripcion, tvPrecio, tvColor, tvObservaciones;
    private EditText etCantidad;
    private Button btnComprar, btnAgregarCarrito;
    private ProductoViewModel vm;
    private String id;
    private String PATH="http://192.168.1.111:45455";

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
        vm.getProductoFoto().observe(this, new Observer<Foto>() {
            @Override
            public void onChanged(Foto fotos) {
                Glide.with(getContext())
                        .load(PATH + fotos.getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(foto);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_producto, container, false);
        foto = root.findViewById(R.id.imvProducto);
        tvDescripcion = root.findViewById(R.id.tvDescripcionProducto);
        tvPrecio = root.findViewById(R.id.tvPrecioProducto);
        etCantidad = root.findViewById(R.id.etCantidadProducto);
        tvColor = root.findViewById(R.id.tvColorProducto);
        tvObservaciones = root.findViewById(R.id.tvObservacionesProducto);
        btnComprar = root.findViewById(R.id.btnComprarProducto);
        btnAgregarCarrito = root.findViewById(R.id.btnAgregarCarrito);

        id = getArguments().getString("id");
        vm.cargarDatos(Integer.parseInt(id));

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductoCompra pc = new ProductoCompra();
                pc.setProductoId(Integer.parseInt(id));
                pc.setCantidad(Integer.parseInt(etCantidad.getText().toString()));
                pc.setDescripcion(tvDescripcion.getText().toString());
                pc.setPrecio(Double.valueOf(tvPrecio.getText().toString()));
                vm.comprar(pc);
                Navigation.findNavController(v).navigate(R.id.nav_compras);
            }
        });

        btnAgregarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", Integer.parseInt(id));
                bundle.putInt("cantidad",  Integer.parseInt(etCantidad.getText().toString()));
                Navigation.findNavController(v).navigate(R.id.nav_carrito, bundle);
            }
        });
        return root;
    }
}
