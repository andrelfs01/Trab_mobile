package br.trabalho_moveis;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class CriarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_criar);
		ActionBar actionBar = getActionBar();
	    actionBar.setTitle("PlanningPoker");
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
	            //Toast.makeText(this, "Menu Item add", Toast.LENGTH_SHORT)
	            //        .show();
	        	showNoticeDialog();
	            break;
	    }
	 
	    return true;
	}
	
	public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
       // DailogActivity dialog = new DailogActivity();
       // dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }
	
	
}