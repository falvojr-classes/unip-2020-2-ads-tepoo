package br.unip.ads.pim.repository.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import static br.unip.ads.pim.BuildConfig.APPLICATION_ID;

public class LocalDataSingleton {

    private static LocalDataSingleton INSTANCE;

    public final AppDatabase db;
    public final SharedPreferences sharedPreferences;

    private LocalDataSingleton(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, APPLICATION_ID)
                //Estamos permitindo a execução na Main Thread para reduzir a complexidade, mas idealmente isso deve ser evitado.
                .allowMainThreadQueries()
                //Estamos limpando o SQLite a cada atualização para reduzir a complexidade, mas idealmente isso deve ser evitado.
                .fallbackToDestructiveMigration()
                .build();
        sharedPreferences = context.getSharedPreferences(APPLICATION_ID, Context.MODE_PRIVATE);
    }

    public static LocalDataSingleton get(Context context) {
        if (LocalDataSingleton.INSTANCE == null) {
            LocalDataSingleton.INSTANCE = new LocalDataSingleton(context);
        }
        return LocalDataSingleton.INSTANCE;
    }
}
