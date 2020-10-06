package br.unip.ads.pim.model.usuarios;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {

	@PrimaryKey
	public Long id;
	public String nome;
	public String documento;
	public String email;
	@Ignore
	public String senha;
	@Ignore
	public TipoCliente tipo;

	public Usuario() {
		super();
	}

	public Usuario(String email, String senha) {
		this();
		this.email = email;
		this.senha = senha;
	}
}
