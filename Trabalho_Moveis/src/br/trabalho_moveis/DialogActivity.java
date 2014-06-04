package br.trabalho_moveis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.EditText;

public class DialogActivity extends DialogFragment {
	NoticeDialogListener mListener;
	
	public DialogActivity() {
		
	}
	
	public interface NoticeDialogListener {
        public void onDialogPositiveClick(String string);
        public void onDialogNegativeClick();
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " precisa implementar NoticeDialogListener");
        }
    }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();

	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(inflater.inflate(R.layout.dialog_descricao, null));
	    View rootView = inflater.inflate(R.layout.dialog_descricao, null);
	    
	    builder.setTitle("Test").setView(rootView);
	    
	    /*final EditText edtNome = (EditText) getView().findViewById(R.id.nome_item);*/
	    
	    
	    //final String s = edtNome.getText().toString();
	    // Add action buttons
	    builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   Log.i("Script",getActivity().getTitle()+" -> titulo da activity");
	            	   EditText edtNome = (EditText)getActivity().findViewById(R.id.nome_item);
	            	   Log.i("Script","texto da dialog: "+edtNome.getText().toString());
	            	   mListener.onDialogPositiveClick(edtNome.getText().toString());
	            	   
	               }
	           })
	           .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   
	               }
	           });      
	    return builder.create();
	}

}
