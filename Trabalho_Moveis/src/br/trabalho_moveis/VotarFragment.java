package br.trabalho_moveis;

import br.model.Item;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class VotarFragment extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_votar_frag);
		Intent intent = getIntent();
		if (intent != null) {
			Bundle params = intent.getExtras();
			if (params != null) {
				Item i = new Item(params.getString("id_plan"), params.getString("nome"));
				i.setId(params.getInt("id"));
				
				// receber um string para colocar no textview
				TextView tv = (TextView) findViewById(R.id.textView_votar);
				tv.setText(i.getNome());

			}
		}
	}

}
