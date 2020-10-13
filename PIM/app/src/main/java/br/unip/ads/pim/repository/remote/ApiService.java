package br.unip.ads.pim.repository.remote;

import java.util.List;

import br.unip.ads.pim.model.interesses.Interesse;
import br.unip.ads.pim.model.usuarios.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    String AUTHORIZATION = "Authorization";

    //region Endpoint do domínio de Usuarios

    @GET("usuarios")
    Call<List<Usuario>> buscarUsuarios();

    @GET("usuarios/{id}")
    Call<Usuario> buscarUm(@Path("id") Long id);

    @POST("usuarios")
    Call<Usuario> inserir(@Body Usuario entidade);

    @PUT("usuarios/{id}")
    Call<Usuario> alterar(@Path("id") Long id, @Body Usuario entidade);

    @DELETE("usuarios/{id}")
    Call<Void> excluir(@Path("id") Long id);

    //endregion

    //region Endpoint do domínio de Login

    @POST("login")
    Call<Usuario> login(@Body Usuario credenciais);

    //endregion

    //region Endpoint do domínio de Interesses

    @GET("interesses")
    Call<List<Interesse>> buscarInteresses(@Header(AUTHORIZATION) String basicAuth);

    //endregion

}
