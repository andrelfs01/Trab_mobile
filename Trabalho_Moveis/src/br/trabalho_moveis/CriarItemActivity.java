package br.trabalho_moveis;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class CriarItemActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_criar_item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.criar_item, menu);
		return true;
	}
	
	
	public void adicionarItem(View v){
		Intent returnIntent = new Intent();
		EditText et = (EditText) findViewById(R.id.nome_item);
		String result = et.getText().toString();
		returnIntent.putExtra("nome",result);
		setResult(RESULT_OK,returnIntent);
		finish();
	}

	public void cancelar(View v){
		Intent returnIntent = new Intent();
		setResult(RESULT_CANCELED, returnIntent);
		finish();
	}
}
