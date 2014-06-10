package br.trabalho_moveis;

import br.model.Item;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class VotarFragment extends Activity {
	Item item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_votar_frag);
		Intent intent = getIntent();
		if (intent != null) {
			Bundle params = intent.getExtras();
			if (params != null) {
				item = new Item(params.getString("id_plan"), params.getString("nome"));
				item.setId(params.getInt("id"));
				
				// receber um string para colocar no textview
				TextView tv = (TextView) findViewById(R.id.textView_votar);
				tv.setText(item.getNome());

			}
		}
	}
	
	public void adicionarItem(View v){
		Intent returnIntent = new Intent();
		//pegar valor dos radiogroup
		int valor = obterValor();
		Bundle b = new Bundle();
		b.putInt("valor", valor);
		b.putInt("id_item", item.getId());
		returnIntent.putExtras(b);
		setResult(RESULT_OK,returnIntent);
		finish();
	}
	
	private int obterValor() {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		int selectedId = radioGroup.getCheckedRadioButtonId();
		RadioButton radioButton = (RadioButton) findViewById(selectedId);
		if (radioButton.getText().toString().equals("1")) {
			return 1;
		} else if (radioButton.getText().toString().equals("2")) {
			return 2;
		}else if (radioButton.getText().toString().equals("3")) {
			return 3;
		}else if (radioButton.getText().toString().equals("5")) {
			return 5;
		}else if (radioButton.getText().toString().equals("8")) {
			return 8;
		}else if (radioButton.getText().toString().equals("13")) {
			return 13;
		} else {
			return 21;
		}
	}

}
