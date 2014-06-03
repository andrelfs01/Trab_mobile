package br.trabalho_moveis;

import android.app.Activity;
import android.support.v4.app.DialogFragment;

public class NoticeDialogFragment extends DialogFragment{
	public interface NoticeDialogListener {
        public void onDialogPositiveClick(String nome, String descricao);
        public void onDialogNegativeClick();
    }
    
    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;
    
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
}
