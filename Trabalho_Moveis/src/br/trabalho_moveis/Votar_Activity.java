package br.trabalho_moveis;

import java.util.ArrayList;

import br.model.Item;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Votar_Activity extends Activity {
	ArrayList<Item> itens;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_votar);
		
		Intent intent = getIntent();
		if(intent != null){
			Bundle  params = intent.getExtras();
			if(params != null){
				itens = (ArrayList<Item>)params.get("itens");
				
				preencherListView();
			}
		}
	}

	private void preencherListView() {
		ListView list = (ListView) findViewById(R.id.listView1);  
		ArrayList<String>array= new ArrayList<String>();
		
		for(Item item: itens){
			array.add(item.getNome());
			
		}
		list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, array));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.votar_, menu);
		return true;
	}

}
