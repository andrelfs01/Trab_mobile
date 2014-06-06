package br.trabalho_moveis;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import br.model.Item;
import br.model.Planning;

public class CriarActivity extends Activity {
	private ArrayList<Item> itens;
	private ListView list;
	EditText edtText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		itens = new ArrayList<Item>();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_criar);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("PlanningPoker");
		list = (ListView) findViewById(R.id.lv_criar);
		preencherListView();
	}

	private void preencherListView() {
		
		ArrayList<String> array = new ArrayList<String>();

		for (Item item : itens) {
			array.add(item.getNome());
			Log.i("Script", item.getNome());
		}

		// ItemAdapter adapter = new ItemAdapter(this, itens);
		list.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, array));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.criar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add:
			// Toast.makeText(this, "Menu Item add", Toast.LENGTH_SHORT)
			// .show();
			criaTelaCadastroItem();
			break;
		}

		return true;
	}

	public void criaTelaCadastroItem() {
		Intent intent = new Intent(this, CriarItemActivity.class);
		startActivityForResult(intent, 1);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				edtText = (EditText) findViewById(R.id.nome_criarAct);
				Item item = new Item(edtText.getText().toString(),
						data.getStringExtra("nome"));
				itens.add(item);
				preencherListView();
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
	}

	public void cadastarPlanning(View v) {
		EditText nome = (EditText) findViewById(R.id.nome_criarAct);
		EditText senha = (EditText) findViewById(R.id.editText2);
		String nomeP = nome.getText().toString();
		String senhaP = senha.getText().toString();
		if (!nomeP.isEmpty()) {
			if (!senhaP.isEmpty()) {		
				if (!itens.isEmpty()) {
					// prosseguir
					Planning p = new Planning(nomeP, senhaP);
					// identificar radio button
					String termino = calcularTermino();
					
					
					//setar o ID do planning em todos!!!!
					setarIDPlanning(nomeP);
					//enviar planning
					enviarPlanningToServer(p);
					//enviar itens
					enviarItensToServer();
					//abrir tela de votacao
				} else {
					// nao tem itens
				}
			} else {
				// Campo senha vazio
			}
		} else {
			// Campo nome(ID) vazio

		}

	}

	private String calcularTermino() {
		int duracao = obterDuracao();
		Calendar c = Calendar.getInstance();
		
		int min = c.get(Calendar.MINUTE);
		int h = c.get(Calendar.HOUR);
		if(min + duracao > 59){
			h = h+1;
			min = min - 59;
		}
		return h+":"+min;
	}

	private void enviarItensToServer() {
		// TODO Auto-generated method stub
		
	}

	private void enviarPlanningToServer(Planning p) {
		// TODO Auto-generated method stub
		
	}

	private void setarIDPlanning(String nomeP) {
		for (Item item : itens) {
			item.setId_plan(nomeP);
		}
		
	}

	private int obterDuracao() {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio);
		int selectedId = radioGroup.getCheckedRadioButtonId();			         
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        if(radioButton.getText().toString().startsWith("3")){
        	return 3;
        }else if(radioButton.getText().toString().startsWith("5")){
        	return 5;
        }else{
        	return 7;
        }
	}

}
