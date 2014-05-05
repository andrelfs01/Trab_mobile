package br.trabalho_moveis;

import java.util.List;
import android.app.ListActivity;
import android.os.Bundle;

public class ListTarefasActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BD bd = new BD(this);
		List<Tarefa> list = bd.buscar();
		setListAdapter(new TarefaAdapter(this, list));
	}
}
