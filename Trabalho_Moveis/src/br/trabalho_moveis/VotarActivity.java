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
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VotarActivity extends Activity {

	private ArrayList<Item> arrayItens;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_votacao);

		Intent intent = getIntent();
		if (intent != null) {
			Bundle params = intent.getExtras();
			if (params != null) {
				String jsonS = params.getString("itens");
				try {
					JSONObject json = new JSONObject(jsonS);
					JSONArray array = json.getJSONArray("array");
					Gson gson = new Gson();

					if (array != null) {
						for (int i = 0; i < array.length(); i++) {
							arrayItens.add((Item) gson.fromJson(
									array.getString(i), Item.class));
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		//colocar no ListView
		ListView list = (ListView) findViewById(R.id.lv_escolher_votar);
		
		ArrayList<String> nome_itens = new ArrayList<String>();
		for (Item string : arrayItens) {
			nome_itens.add(string.getNome());
		}
		
		list.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, nome_itens));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.votar, menu);
		return true;
	}
	

}
