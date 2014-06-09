package br.trabalho_moveis;

import java.util.ArrayList;

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
import android.widget.ListView;

public class VotarActivity extends Activity {

	private ArrayList<Item> arrayItens;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
			public void onItemClick(AdapterView<?> av, View v, int position, long id) {
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

//	public void sair(View view) {
//		finish();
//	}

}
