package com.example.bascubazarapp.request;

import android.util.Log;

import com.example.bascubazarapp.modelos.Categoria;
import com.example.bascubazarapp.modelos.Producto;
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

    private static final String PATH="http://192.168.0.31:45455/api/";
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

        @GET("Producto/search/{categoriaId}")
        Call<List<Producto>> obtenerProductoXCategoria(@Header("Authorization") String token, @Path("categoriaId") int categoriaId);

        @GET("Producto/{id}")
        Call<Producto> obtenerProducto(@Header("Authorization") String token, @Path("id") int id);

      /*  @GET("Inmueble")
        Call<List<Inmueble>> obtenerMisInmuebles(@Header("Authorization") String token);

        @PUT("Inmueble/{id}")
        Call<Inmueble> editarInmueble(@Header("Authorization") String token, @Path("id") int id, @Body Inmueble i);



        @GET("ContratoAlquiler/{id}")
        Call<ContratoAlquiler>  obtenerContratoPorInmueble(@Header("Authorization") String token, @Path("id") int id);

        @DELETE("Inmueble/{id}")
        Call<String> borrarInmueble(@Header("Authorization") String token, @Path("id") int id);

        @POST("Inmueble")
        Call<Inmueble> agregarInmueble(@Header("Authorization") String token, @Body Inmueble inm);
       */

    }

}
