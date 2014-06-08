package br.model;


public class Planning {

	private String id;
	private String senha;
	private String termino;

	public Planning() {

	}

	public Planning(String id, String senha, String termino) {
		this.id = id;
		this.senha = senha;
		this.termino = termino;
	}

	public String getTermino() {
		return termino;
	}

	public void setTermino(String termino) {
		this.termino = termino;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
