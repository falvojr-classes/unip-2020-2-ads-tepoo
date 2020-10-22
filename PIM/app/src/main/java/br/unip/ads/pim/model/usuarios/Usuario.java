package br.unip.ads.pim.model.usuarios;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

import br.unip.ads.pim.model.interesses.Interesse;

@Entity
public class Usuario {

	@PrimaryKey
	public Long id;
	public String nome;
	public String documento;
	public String email;
	public String telefone;
	@Ignore
	public String senha;
	public TipoUsuario tipo;
	public List<Interesse> interesses;

	public Usuario() {
		super();
	}

	public Usuario(String email, String senha) {
		this();
		this.email = email;
		this.senha = senha;
	}
}
