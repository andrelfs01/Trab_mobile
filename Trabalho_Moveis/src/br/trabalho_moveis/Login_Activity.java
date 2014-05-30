package br.trabalho_moveis;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Login_Activity extends Activity {

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
	
	
	public void entrarPlanning(View view){
		
		new Thread(){
			public void run(){
				HttpClient httpClient = new DefaultHttpClient();
				//url aki>>
				EditText idED = (EditText) findViewById(R.id.editTextNomePlanning);
				String id = idED.getText().toString();
				HttpGet httpGet = new HttpGet(
						"http://10.0.43.218:8080/WebServiceRest/planning/" + id);
				//busca por id
				HttpResponse response;
				try {
					response = httpClient.execute(httpGet);
					String jsonString = EntityUtils.toString(response
							.getEntity());
//					JSONArray array = new JSONArray(jsonString);
					JSONObject jsonObj = new JSONObject(jsonString);
					JSONArray array = jsonObj.getJSONArray("itens");
					
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
		}.start();
		
		Intent intent = new Intent();
		ArrayList<String> arrayItens = new ArrayList<String>(); 
		/*    
		JSONArray jArray = (JSONArray)jsonObject; 
		if (jArray != null) { 
		   for (int i=0;i<jArray.length();i++){ 
		    arrayItens.add(jArray.get(i).toString());
		   } 
		}
		 */
		
		//arrayItens = JSONArray
		intent.putStringArrayListExtra("itens", arrayItens);
		
		
		
	}
}
