package com.example.bascubazarapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bascubazarapp.R;
import com.example.bascubazarapp.modelos.Producto;
import com.example.bascubazarapp.modelos.ProductoCompra;

import org.w3c.dom.Text;

import java.util.List;

public class ListProductoCompradoAdapter extends ArrayAdapter<ProductoCompra> {
    private Context context;
    private List<ProductoCompra> lista;
    private LayoutInflater li;

    public ListProductoCompradoAdapter(@NonNull Context context, int resource, @NonNull List<ProductoCompra> objects, LayoutInflater li) {
        super(context, resource, objects);
        this.context = context;
        this.lista = objects;
        this.li = li;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if(itemView==null){
            itemView = li.inflate(R.layout.item_producto_compra, parent, false);
        }
        ProductoCompra productoCompra= lista.get(position);

        ImageView foto = itemView.findViewById(R.id.ivItemProductoCompra);
        foto.setImageResource(R.drawable.producto_x);

        TextView nombreItem = itemView.findViewById(R.id.tvDescripcionItemCompra);
        nombreItem.setText(productoCompra.getDescripcion());

        TextView precio = itemView.findViewById(R.id.tvPrecioItemCompra);
        precio.setText(productoCompra.getPrecio() + "");

        TextView cantidad = itemView.findViewById(R.id.tvCantidadItemCompra);
        cantidad.setText(productoCompra.getCantidad() + "");

        return itemView;

    }
}
