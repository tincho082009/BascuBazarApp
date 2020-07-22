package com.example.bascubazarapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.bascubazarapp.R;
import com.example.bascubazarapp.modelos.CarritoEntity;
import com.example.bascubazarapp.modelos.Foto;
import com.example.bascubazarapp.modelos.Producto;
import com.example.bascubazarapp.repository.CarritoRepository;
import com.example.bascubazarapp.request.ApiClient;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductoCarritoAdapter extends RecyclerView.Adapter<ListProductoCarritoAdapter.ViewHolderDatos> implements View.OnClickListener {
    private List<CarritoEntity> lista;
    private View.OnClickListener listener;
    private Context context;
    private String PATH="http://192.168.1.105:45455";

    public ListProductoCarritoAdapter(List<CarritoEntity> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, null,false);

        view.setOnClickListener(this);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.asignarDatos(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView foto;
        TextView tvDescripcion;
        TextView tvCantidad;
        TextView tvPrecio;
        ImageButton btnBorrar;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.ivFotoItemCarrito);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionCarrito);
            tvCantidad = itemView.findViewById(R.id.tvCantidadCarrito);
            tvPrecio = itemView.findViewById(R.id.tvPrecioCarrito);
            btnBorrar = itemView.findViewById(R.id.ibBorrarIndividualCarrito);
            btnBorrar.setOnClickListener(this);
        }

        public void asignarDatos(CarritoEntity carritoEntity) {
            int id = carritoEntity.getIdProducto();

            SharedPreferences pref = context.getSharedPreferences("token", 0);
            String t = pref.getString("token", "vacio");

            Call<List<Foto>> fotos = ApiClient.getMyApiClient().obtenerFotosProducto(t, id);
            fotos.enqueue(new Callback<List<Foto>>() {
                @Override
                public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                    if(response.isSuccessful()){
                        for (Foto f:response.body()) {
                            Glide.with(context)
                                    .load(PATH + f.getUrl())
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(foto);
                        }

                     Call<Producto> productoCall = ApiClient.getMyApiClient().obtenerProducto(t, id);
                        productoCall.enqueue(new Callback<Producto>() {
                            @Override
                            public void onResponse(Call<Producto> call, Response<Producto> response) {
                                if(response.isSuccessful()){
                                    tvDescripcion.setText(response.body().getDescripcion());
                                    tvPrecio.setText(response.body().getPrecio() * carritoEntity.getCantidad() + "");
                                    tvCantidad.setText(carritoEntity.getCantidad()+"");

                                }else{
                                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Producto> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Foto>> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            CarritoEntity carritoEntity = lista.get(position);
            CarritoRepository carritoRepository = new CarritoRepository(v.getContext());
            carritoRepository.borrarUno(carritoEntity);
            Toast.makeText(context, "Borrado correctamente", Toast.LENGTH_LONG).show();
        }
    }


}
