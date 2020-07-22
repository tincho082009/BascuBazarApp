package com.example.bascubazarapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bascubazarapp.R;
import com.example.bascubazarapp.modelos.Foto;
import com.example.bascubazarapp.modelos.Producto;
import com.example.bascubazarapp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductoAdapter extends ArrayAdapter<Producto> {
    private Context context;
    private List<Producto> lista;
    private LayoutInflater li;
    private String PATH="http://192.168.1.105:45455";

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
        final View item2 = itemView;
        Producto producto= lista.get(position);
        int id = producto.getProductoId();
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        String t = pref.getString("token", "vacio");

        Call<List<Foto>> fotos = ApiClient.getMyApiClient().obtenerFotosProducto(t, id);
        fotos.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                if(response.isSuccessful()){
                    for (Foto f:response.body()) {
                        ImageView foto = item2.findViewById(R.id.ivFotoItem);
                        Glide.with(getContext())
                                .load(PATH + f.getUrl())
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(foto);
                    }
                }else{
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        TextView nombreItem = itemView.findViewById(R.id.tvNombreItem);
        nombreItem.setText(producto.getDescripcion());

        TextView precio = itemView.findViewById(R.id.tvPrecioItem);
        precio.setText(producto.getPrecio() + "");

        return itemView;

    }
}
