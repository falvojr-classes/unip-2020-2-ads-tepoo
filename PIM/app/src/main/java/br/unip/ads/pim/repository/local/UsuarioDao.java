package br.unip.ads.pim.repository.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import br.unip.ads.pim.model.usuarios.Usuario;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM Usuario LIMIT 1")
    Usuario findOne();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Usuario usuario);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Usuario usuario);

    @Query("DELETE FROM Usuario")
    void deleteAll();
}
