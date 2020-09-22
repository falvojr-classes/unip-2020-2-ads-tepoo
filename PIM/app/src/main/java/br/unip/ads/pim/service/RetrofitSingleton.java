package br.unip.ads.pim.service;

import com.google.gson.Gson;

import br.unip.ads.pim.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private static RetrofitSingleton instance = new RetrofitSingleton();

    public final ApiService apiService;

    private RetrofitSingleton() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                // Adicionar o suporte para serialização/deserialização de JSON
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static RetrofitSingleton get() {
        return RetrofitSingleton.instance;
    }
}
