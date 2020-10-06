package br.unip.ads.pim.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import br.unip.ads.pim.model.usuarios.Usuario;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM Usuario LIMIT 1")
    Usuario findOne();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Usuario usuario);

    @Delete
    void delete(Usuario usuario);
}
