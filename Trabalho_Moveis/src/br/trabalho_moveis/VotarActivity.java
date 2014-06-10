package br.trabalho_moveis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import br.model.*;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class VotarActivity extends Activity {

	private ArrayList<Item> arrayItens;
	int count;
	private ArrayList<Voto> votos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		votos = new ArrayList<Voto>();
		count = 0;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_votacao);
		arrayItens = new ArrayList<Item>();
		Intent intent = getIntent();
		if (intent != null) {
			Bundle params = intent.getExtras();
			if (params != null) {
				String jsonS = params.getString("itens");

				Log.i("Script", "json recebido via intent: " + jsonS);
				Gson gson = new Gson();
				try {
					jsonS = "{\"itens\":" + jsonS + "}";
					Log.i("Script", jsonS);
					JSONObject json = new JSONObject(jsonS);
					JSONArray array = json.getJSONArray("itens");

					if (array != null) {
						for (int i = 0; i < array.length(); i++) {
							arrayItens.add((Item) gson.fromJson(
									array.getString(i), Item.class));
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					String object = jsonS.replace("[", "");
					object = object.replace("]", "");
					arrayItens.add((Item) gson.fromJson(object, Item.class));
				}
			}
			// colocar no ListView
			ListView list = (ListView) findViewById(R.id.lv_escolher_votar);
			ArrayList<String> nome_itens = new ArrayList<String>();
			for (Item string : arrayItens) {
				nome_itens.add(string.getNome());
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, nome_itens);
			list.setAdapter(adapter);

			list.setOnItemClickListener(chamaAtividades());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.votar, menu);
		return true;
	}

	public OnItemClickListener chamaAtividades() {
		return (new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int position,
					long id) {
				Intent intent;
				Item i = arrayItens.get(position);
				Bundle b = new Bundle();
				b.putString("nome", i.getNome());
				b.putInt("id", i.getId());
				b.putString("id_plan", i.getId_plan());
				intent = new Intent(getBaseContext(), VotarFragment.class);
				intent.putExtras(b);
				startActivityForResult(intent, 1);
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				Bundle b = data.getExtras();
				int valor = b.getInt("valor");
				int id_item = b.getInt("id_item");
				Voto v = new Voto();
				v.setId_item(id_item);
				v.setValor(valor);
				votos.add(v);
				count++;
			}
			if (resultCode == RESULT_CANCELED) {
				// Write your code if there's no result
			}
		}
	}

	public void enviarVotos(View v) throws InterruptedException {
		// para cada voto enviar: /planning/{id}/voto/{valor}
		if (count >= votos.size()) {
			sendVotosToServer();
		} else {
			Toast.makeText(this, "Alguns itens não foram avaliados!",
					Toast.LENGTH_LONG);

		}

	}

	private void sendVotosToServer() throws InterruptedException {
		Thread t = new Thread() {
			public void run() {
				HttpClient httpClient = new DefaultHttpClient();
				// url aki>>			
				
				for (Voto v : votos) {
					HttpGet httpGet = new HttpGet(
						"http://10.0.2.2:8080/WebServiceRest/planning/"
								+ v.getId_item() + "/valor/" + v.getValor());//
					// busca por id
					HttpResponse response;
					try {
						response = httpClient.execute(httpGet);
						String jsonString = EntityUtils.toString(response
							.getEntity());
						Log.i("Script", "Resp de enviar voto: "+jsonString);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			
		};
		t.start();
		t.join();
	}
}