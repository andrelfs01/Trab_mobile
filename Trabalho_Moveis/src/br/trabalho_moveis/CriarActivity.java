package br.trabalho_moveis;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
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
import android.widget.Toast;
import br.model.CadastroPlanning;
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
				String nome = data.getExtras().getString("nome");
				Log.i("Script", "ITEM VINDO DA TELA DE CRIAR "+nome);
				Item item = new Item(edtText.getText().toString(),
						nome);
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

					// identificar radio button
					String termino = calcularTermino();
					Planning p = new Planning(nomeP, senhaP, termino);

					// setar o ID do planning em todos!!!!
					setarIDPlanning(nomeP);
					// enviar planning

					enviarPlanningToServer(new CadastroPlanning(p, itens));
				} else {
					Toast.makeText(this, "Lista de itens vazia", Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();

		}

	}

	private String calcularTermino() {
		int duracao = obterDuracao();
		Calendar c = Calendar.getInstance();

		int min = c.get(Calendar.MINUTE);
		int h = c.get(Calendar.HOUR);
		if (min + duracao > 59) {
			h = h + 1;
			min = min - 59;
		}
		return h + ":" + min;
	}

	private void enviarPlanningToServer(final CadastroPlanning cadastroPlanning) {
		Thread t = new Thread() {
			public void run() {
				Looper.prepare();
				Gson gson = new Gson();
				String stringJson = gson.toJson(cadastroPlanning);
				StringEntity se;

				HttpClient httpClient = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(
						httpClient.getParams(), 10000);
				HttpPost httpPost = new HttpPost(
						"http://10.0.2.2:8080/WebServiceRest/planning/inserir");//10.0.2.2
				try {
					se = new StringEntity(stringJson);
					httpPost.setHeader("Accept", "application/json");
					httpPost.setHeader("Content-type", "application/json");
					httpPost.setEntity(se);
					final HttpResponse response = httpClient.execute(httpPost);
					Log.i("Script", EntityUtils.toString(response.getEntity()));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		if (radioButton.getText().toString().startsWith("3")) {
			return 3;
		} else if (radioButton.getText().toString().startsWith("5")) {
			return 5;
		} else {
			return 7;
		}
	}

}
