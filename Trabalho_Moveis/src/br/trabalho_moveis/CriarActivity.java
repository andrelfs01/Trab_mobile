package br.trabalho_moveis;

import java.util.ArrayList;

import br.model.Item;
import br.model.ItemAdapter;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class CriarActivity extends FragmentActivity implements
		DialogActivity.NoticeDialogListener {
	private ArrayList<Item> itens;
	private ListView list;

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
		/*
		 * ArrayList<User> arrayOfUsers = new ArrayList<User>(); // Create the
		 * adapter to convert the array to views 
		 * UsersAdapter adapter = new UsersAdapter(this, arrayOfUsers); // Attach the adapter to a ListView
		 * 
		 * listView.setAdapter(adapter);
		 */
		ArrayList<String> array = new ArrayList<String>();

		for (Item item : itens) {
			array.add(item.getNome());
			Log.i("Script", item.getNome());
		}
		
		//ItemAdapter adapter = new ItemAdapter(this, itens);
		list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, array));

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
			showNoticeDialog();
			break;
		}

		return true;
	}

	public void showNoticeDialog() {
		// Create an instance of the dialog fragment and show it
		DialogActivity dialog = new DialogActivity();
		
		//AlertDialog alertTeste = new AlertDialog(getApplicationContext());
		//dialog.onAttach(this);
		dialog.show(getSupportFragmentManager(), "DialogActivity");
		
		
		
	}

	@Override
	public void onDialogPositiveClick(String string) {
	
		Log.i("Script", "Confirmou!");
		EditText id = (EditText) findViewById(R.id.nome_criarAct);
		Log.i("Script","nome do plan: "+id.getText().toString());
		Log.i("Script","nome do item: "+string);
		Item i = new Item(id.getText().toString(), string);
		itens.add(i);
		// atualizar
		preencherListView();
	}

	@Override
	public void onDialogNegativeClick() {
		Log.i("Script", "Negou!");
	}

}
