package br.unip.ads.pim.service;

import java.util.List;

import br.unip.ads.pim.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("usuarios")
    Call<List<Usuario>> buscarTodos();

    @GET("usuarios/{id}")
    Call<Usuario> buscarUm(@Path("id") Long id);

    @POST("usuarios")
    Call<Void> inserir(@Body Usuario entidade);

    @PUT("usuarios/{id}")
    Call<Void> alterar(@Path("id") Long id, @Body Usuario entidade);

    @DELETE("usuarios/{id}")
    Call<Void> excluir(@Path("id") Long id);
}
