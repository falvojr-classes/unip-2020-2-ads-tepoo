package br.unip.ads.pim.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.unip.ads.pim.model.usuarios.Usuario;

@Database(entities = {Usuario.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();
}
