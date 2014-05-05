package br.trabalho_moveis;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Nova_TarefaActivity extends Activity {
	private Tarefa tarefa = new Tarefa();
	private EditText nomeEt;
	private Button salvarBt;
	private long a = 1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nova_tarefa);
		
		nomeEt = (EditText) findViewById(R.id.nome);
		salvarBt = (Button) findViewById(R.id.salvar);
		
		Intent intent = getIntent();
		if(intent != null){
			Bundle bundle = intent.getExtras();
			if(bundle != null){
				tarefa.setId(bundle.getLong("id"));
				tarefa.setNome(bundle.getString("nome"));
				nomeEt.setText(tarefa.getNome());
				salvarBt.setVisibility(View.GONE);
			}
		}
	}

	public void salvarTarefa(View view){
		Log.i("Script", "entrou");
		tarefa.setNome(nomeEt.getText().toString());
		tarefa.setId(a);
		a++;
		BD bd = new BD(this);
		Log.i("Script", "abriu");
		bd.inserir(tarefa);
		Log.i("Script", "inseriu");
		Toast.makeText(this, "Tarefa inserida com sucesso!", Toast.LENGTH_SHORT).show();
	}
	
	public void listar(View view){
		//abrir tela Tarefas_Activity
		Button bt = (Button) view;
		Intent intent;
		
		//if(bt.getText().toString().equalsIgnoreCase("Listar Tarefas")){
			intent = new Intent(this, Tarefas_Activity.class);
		//}
		//else{
			//intent = new Intent(this, ListUsersActivity.class);
		//}S
		startActivity(intent);
	}

}
