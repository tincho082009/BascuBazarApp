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
import com.example.bascubazarapp.modelos.ProductoCompra;
import com.example.bascubazarapp.request.ApiClient;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductoCompradoAdapter extends ArrayAdapter<ProductoCompra> {
    private Context context;
    private List<ProductoCompra> lista;
    private LayoutInflater li;
    private String PATH="http://192.168.1.100:45455";


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
        final View item2 = itemView;
        ProductoCompra productoCompra= lista.get(position);
        int id = productoCompra.getProductoId();
        SharedPreferences pref = context.getSharedPreferences("token", 0);
        String t = pref.getString("token", "vacio");

        Call<List<Foto>> fotos = ApiClient.getMyApiClient().obtenerFotosProducto(t, id);
        fotos.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                if(response.isSuccessful()){
                    for (Foto f:response.body()) {
                        ImageView foto = item2.findViewById(R.id.ivItemProductoCompra);
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

        TextView nombreItem = itemView.findViewById(R.id.tvDescripcionItemCompra);
        nombreItem.setText(productoCompra.getDescripcion());

        TextView precio = itemView.findViewById(R.id.tvPrecioItemCompra);
        precio.setText(productoCompra.getPrecio() + "");

        TextView cantidad = itemView.findViewById(R.id.tvCantidadItemCompra);
        cantidad.setText(productoCompra.getCantidad() + "");

        return itemView;

    }
}
