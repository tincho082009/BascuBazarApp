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

import java.util.List;

public class ListProductoAdapter extends ArrayAdapter<Producto> {
    private Context context;
    private List<Producto> lista;
    private LayoutInflater li;
    public ListProductoAdapter(@NonNull Context context, int resource, @NonNull List<Producto> objects, LayoutInflater li) {
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
            itemView = li.inflate(R.layout.item_producto, parent, false);
        }
        Producto producto= lista.get(position);

        ImageView foto = itemView.findViewById(R.id.ivFotoItem);
        foto.setImageResource(R.drawable.producto_x);

        TextView nombreItem = itemView.findViewById(R.id.tvNombreItem);
        nombreItem.setText(producto.getDescripcion());

        TextView precio = itemView.findViewById(R.id.tvPrecioItem);
        precio.setText(producto.getPrecio() + "");

        return itemView;

    }
}
