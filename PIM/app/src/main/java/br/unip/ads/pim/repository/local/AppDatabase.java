package br.unip.ads.pim.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.unip.ads.pim.model.usuarios.Usuario;
import br.unip.ads.pim.repository.local.converters.InteresseConverter;
import br.unip.ads.pim.repository.local.converters.TipoUsuarioConverter;

@Database(entities = {Usuario.class}, version = 3)
@TypeConverters({TipoUsuarioConverter.class, InteresseConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();
}
