package br.model;

import java.util.ArrayList;

public class CadastroPlanning {
	private Planning p;
	private ArrayList<Item> itens;
	
	public CadastroPlanning(Planning planning, ArrayList<Item> itens) {
		this.p = planning;
		this.itens = itens;
	}
	
	public Planning getP() {
		return p;
	}
	public void setP(Planning p) {
		this.p = p;
	}
	public ArrayList<Item> getItens() {
		return itens;
	}
	public void setItens(ArrayList<Item> itens) {
		this.itens = itens;
	}
}
