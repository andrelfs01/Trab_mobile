package br.model;

import java.io.Serializable;

public class Item implements Serializable{
	
	
	private String id_plan;
	private int id;
	private String nome;
	
	public Item(String id_plan, String nome) {
		this.id_plan = id_plan;
		this.nome = nome;
	}
	
	public String getId_plan() {
		return id_plan;
	}
	public void setId_plan(String id_plan) {
		this.id_plan = id_plan;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
