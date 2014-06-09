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

import br.model.Item;

import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Activity extends Activity {

	private ArrayList<Item> array;
	private boolean encerrado;
	private boolean senhaCorreta;

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

	public void entrarPlanning(View view) throws InterruptedException {

		Thread t = new Thread() {
			public void run() {
				senhaCorreta = false;
				ArrayList<Item> arrayItens = new ArrayList<Item>();
				HttpClient httpClient = new DefaultHttpClient();
				// url aki>>
				EditText idED = (EditText) findViewById(R.id.editTextNomePlanning);
				EditText senhaED = (EditText) findViewById(R.id.editTextSenhaPlanning);
				String senha = senhaED.getText().toString();
				Calendar c = Calendar.getInstance();
				int h = c.get(Calendar.HOUR);
				int min = c.get(Calendar.MINUTE);
				String id = idED.getText().toString();

				HttpGet httpGet = new HttpGet(
						"http://10.0.2.2:8080/WebServiceRest/planning/autenticar/"
								+ id + "/" + senha);//
				// busca por id
				HttpResponse response;
				try {
					response = httpClient.execute(httpGet);
					String jsonString = EntityUtils.toString(response
							.getEntity());
					Log.i("Script","jsonString-> "+jsonString);
					if (jsonString.equals("autenticado")) {
						setAutenticado(true);
					} else {
						setAutenticado(false);
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (senhaCorreta) {
						try {
							httpGet = new HttpGet(
									"http://10.0.2.2:8080/WebServiceRest/planning/itens/"
											+ id + "/" + h + ":" + min);
							response = httpClient.execute(httpGet);
							String jsonString = EntityUtils.toString(response
									.getEntity());
							Log.i("Script", "Resposta servlet" + jsonString);
							JSONObject jsonObj = new JSONObject(jsonString);
							boolean encerrado = jsonObj.getBoolean("encerrado");
							setEncerrado(encerrado);
							Gson gson = new Gson();
							try{
								JSONArray jarray = jsonObj.getJSONArray("itens");
								if (jarray != null) {
									for (int i = 0; i < jarray.length(); i++) {
										
										arrayItens.add((Item) gson.fromJson(
												jarray.getString(i), Item.class));
										Log.i("Script", "item dentro do for"+jarray.getString(i));
									}
								}
							}catch(Exception e){
								JSONObject jarray = jsonObj.getJSONObject("itens");
								arrayItens.add((Item) gson.fromJson(
										jarray.toString(), Item.class));
							}			
							System.out.println("passou");
							setArrayItens(arrayItens);						

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
					} else {
						setAutenticado(false);
					}
				}
			}

		};
		t.start();
		t.join();

		if (!senhaCorreta) {
			senhaInvalida();
		} else {

			if (!encerrado) {
				abrirTelaVotacao();
			} else {
				abrirTelaResultado();
			}
		}
	}

	private void setAutenticado(boolean b) {
		senhaCorreta = b;

	}

	private void abrirTelaResultado() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "abrirTelaResultado", Toast.LENGTH_LONG).show();
	}

	protected void abrirTelaVotacao() {
		// Toast.makeText(this,"abrirTelaVotacao",Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, VotarActivity.class);
		Bundle b = new Bundle();
		Gson gson = new Gson();
		printArray();
		String i = gson.toJson(array);
		Log.i("Script", "Escrevendo json para mandar via intent: " + i);
		b.putString("itens", i);
		intent.putExtras(b);
		startActivity(intent);
	}

	private void printArray() {
		for (Item item : array) {
			Log.i("Script","cada item: "+item.getNome());
		}
		
	}

	private void setEncerrado(boolean encerrado) {
		this.encerrado = encerrado;

	}

	protected void setArrayItens(ArrayList<Item> arrayItens) {
		this.array = arrayItens;

	}

	public void criarPlanning(View v) {
		Intent intent = new Intent(this, CriarActivity.class);
		startActivity(intent);
	}

	private void senhaInvalida() {
		Toast.makeText(this, "ID Planning ou Senha invalidos",
				Toast.LENGTH_LONG).show();
		EditText senha = (EditText) findViewById(R.id.editTextSenhaPlanning);
		senha.setText("");
	}
}
