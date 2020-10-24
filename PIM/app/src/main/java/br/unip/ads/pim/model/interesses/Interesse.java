package br.unip.ads.pim.model.interesses;

public class Interesse {
	public Long id;
	public String descricao;

	public Interesse() {
		super();
	}

	public Interesse(Long id) {
		this();
		this.id = id;
	}
}
