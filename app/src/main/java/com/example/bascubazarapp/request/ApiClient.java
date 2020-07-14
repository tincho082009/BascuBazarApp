package com.example.bascubazarapp.request;

import android.util.Log;

import com.example.bascubazarapp.modelos.Categoria;
import com.example.bascubazarapp.modelos.Compra;
import com.example.bascubazarapp.modelos.Foto;
import com.example.bascubazarapp.modelos.Producto;
import com.example.bascubazarapp.modelos.ProductoCompra;
import com.example.bascubazarapp.modelos.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class ApiClient {

    private static final String PATH="http://192.168.1.107:45455/api/";
    private static  MyApiInterface myApiInteface;

    public static MyApiInterface getMyApiClient(){

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        myApiInteface=retrofit.create(MyApiInterface.class);
        Log.d("salida",retrofit.baseUrl().toString());
        return myApiInteface;
    }

    public interface MyApiInterface {

        @POST("Usuario/login")
        Call<String> loginUsuario(@Body Usuario u);

        @GET("Usuario")
        Call<Usuario> obtenerUsuario(@Header("Authorization") String token);

        @PUT("Usuario")
        Call<Usuario> editarUsuario(@Header("Authorization") String token, @Body Usuario u);

        @GET("Categoria")
        Call<List<Categoria>> obtenerCategorias(@Header("Authorization") String token);

        @GET("Producto")
        Call<List<Producto>> obtenerTodosProductos(@Header("Authorization") String token);

        @GET("Producto/search/{categoriaId}")
        Call<List<Producto>> obtenerProductoXCategoria(@Header("Authorization") String token, @Path("categoriaId") int categoriaId);

        @GET("Producto/{id}")
        Call<Producto> obtenerProducto(@Header("Authorization") String token, @Path("id") int id);

        @GET("Producto/search/descripcion/{descripcion}")
        Call<Producto> obtenerProductoXDescripcion(@Header("Authorization") String token, @Path("descripcion") String descripcion);

        @GET("ProductoCompra")
        Call<List<ProductoCompra>> obtenerProductoComprado(@Header("Authorization") String token);

        @GET("Foto/{id}")
        Call<List<Foto>> obtenerFotosProducto(@Header("Authorization") String token, @Path("id") int id);

        @POST("Compra")
        Call<Compra> crearCompra(@Header("Authorization") String token, @Body Compra entidad);

        @POST("ProductoCompra")
        Call<ProductoCompra> crearComprarProducto(@Header("Authorization") String token, @Body ProductoCompra entidad);



    }

}
