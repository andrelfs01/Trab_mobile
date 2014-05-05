package br.trabalho_moveis;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BD {
	private SQLiteDatabase bd;
	
	public BD(Context context){
		BDCore auxBd = new BDCore(context);
		Log.i("Script","dentro do construtor");
		bd = auxBd.getWritableDatabase();
	}
	
	
	public void inserir(Tarefa tarefa){
		ContentValues valores = new ContentValues();
		valores.put("nome", tarefa.getNome());
		valores.put("id", tarefa.getId());
		bd.insert("tarefa", null, valores);	
	}
	
	
	public void atualizar(Tarefa tarefa){
		ContentValues valores = new ContentValues();
		valores.put("nome", tarefa.getNome());
		
		bd.update("tarefa", valores, "_id = ?", new String[]{""+tarefa.getId()});
	}
	
	
	public void deletar(Tarefa tarefa){
		bd.delete("tarefa", "_id = "+tarefa.getId(), null);
	}
	
	
	public List<Tarefa> buscar(){
		List<Tarefa> list = new ArrayList<Tarefa>();
		String[] colunas = new String[]{"_id", "nome", "email"};
		
		Cursor cursor = bd.query("usuario", colunas, null, null, null, null, "nome ASC");
		
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			
			do{
				Tarefa u = new Tarefa();
				u.setId(cursor.getLong(0));
				u.setNome(cursor.getString(1));
				list.add(u);
				
			}while(cursor.moveToNext());
		}
		
		return(list);
	}
}
