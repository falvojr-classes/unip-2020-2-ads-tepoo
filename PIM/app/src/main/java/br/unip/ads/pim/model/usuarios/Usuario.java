package br.unip.ads.pim.model.usuarios;

public class Usuario {

	public Long id;
	public String nome;
	public String documento;
	public String email;
	public String senha;
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
