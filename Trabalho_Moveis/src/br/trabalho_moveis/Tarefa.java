package br.trabalho_moveis;

import java.sql.Date;

public class Tarefa {
	private long id;
	private String nome;
	private Date ultima_data;
	//private long id_responsavel;//tem q ter? eu n sei
	private String descricao;
	private boolean alarme;
	//estudar API alarme
	private long id_criador;
	
	
	
	public long getId(){
		return id;
	}

	public String getNome() {
		// TODO Auto-generated method stub
		return nome;
	}

	public void setId(long long1) {
		this.id = long1;
	}

	public void setNome(String string) {
		this.nome = string;
		
	}
}
