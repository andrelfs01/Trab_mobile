package br.trabalho_moveis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCore extends SQLiteOpenHelper {
	private static final String NOME_BD = "banco_trab";
	private static final int VERSAO_BD = 1;
	
	
	public BDCore(Context ctx){
		super(ctx, NOME_BD, null, VERSAO_BD);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase bd) {
		bd.execSQL("create table tarefa(_id integer primary key autoincrement, nome text not null);");
		//bd.execSQL("create table moradores(_id integer primary key autoincrement, nome text not null);");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
		bd.execSQL("drop table tarefa;");
		onCreate(bd);
	}

}
