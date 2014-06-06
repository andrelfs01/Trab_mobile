package br.trabalho_moveis;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.model.Item;

import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Login_Activity extends Activity {

	private ArrayList<Item> array;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_, menu);
		return true;
	}
	
	
	public void entrarPlanning(View view) throws InterruptedException{
		
		Thread t = new Thread(){
			public void run(){
				ArrayList<Item> arrayItens = new ArrayList<Item>();
				HttpClient httpClient = new DefaultHttpClient();
				//url aki>>
				EditText idED = (EditText) findViewById(R.id.editTextNomePlanning);
				String id = idED.getText().toString();
				HttpPost httpPost = new HttpPost();
				HttpGet httpGet = new HttpGet(
						"http://10.0.2.2:8080/WebServiceRest/planning/itens/"+ id);
				//busca por id
				HttpResponse response;
				try {
					response = httpClient.execute(httpGet);
					String jsonString = EntityUtils.toString(response
							.getEntity());
					System.out.println(jsonString);
					JSONObject jsonObj = new JSONObject(jsonString);
					JSONArray array = jsonObj.getJSONArray("itens");
					Gson gson= new Gson();
					
					if (array != null){ 
						   for (int i = 0;i < array.length(); i++){
							   arrayItens.add((Item)gson.fromJson(array.getString(i),Item.class));
						   } 
						   System.out.println("passou");
						   setArrayItens(arrayItens);
						   //
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t.start();
		t.join();
		abrirTelaVotacao();		
	}

	protected void abrirTelaVotacao() {
	//	Intent intent = new Intent(this, Votar_Activity.class);
		//intent.putExtra("itens", array);
		//startActivity(intent);
		//é só isso msm?
	}

	protected void setArrayItens(ArrayList<Item> arrayItens) {
		this.array = arrayItens;
		
	}

	public void criarPlanning(View v){
		Intent intent = new Intent(this, CriarActivity.class);
		startActivity(intent);
	}
}
