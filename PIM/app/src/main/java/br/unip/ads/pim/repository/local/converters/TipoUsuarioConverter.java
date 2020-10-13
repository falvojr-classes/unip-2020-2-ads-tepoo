package br.unip.ads.pim.repository.local.converters;

import androidx.room.TypeConverter;

import br.unip.ads.pim.model.usuarios.TipoUsuario;

public class TipoUsuarioConverter {

    @TypeConverter
    public TipoUsuario fromString(String value) {
        return TipoUsuario.valueOf(value);
    }

    @TypeConverter
    public String fromEnum(TipoUsuario tipoUsuario) {
        return tipoUsuario.name();
    }
}
