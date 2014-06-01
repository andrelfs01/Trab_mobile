package br.model;


public class Planning {

	private String id;
	private String senha;
	
	public Planning(String id, String senha) {
		this.id = id;
		this.senha = senha;
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
