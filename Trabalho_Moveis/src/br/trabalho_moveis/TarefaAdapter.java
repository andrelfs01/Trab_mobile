package br.trabalho_moveis;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TarefaAdapter extends BaseAdapter {

	private Context context;
	private List<Tarefa> list;

	public TarefaAdapter(Context context, List<Tarefa> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int arg0) {

		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return list.get(arg0).getId();
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		final int auxPosition = position;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.activity_tarefas, null);

		TextView tv = (TextView) layout.findViewById(R.id.nome);
		tv.setText(list.get(position).getNome());

		Button detalhesBt = (Button) layout.findViewById(R.id.detalhes);
		detalhesBt.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, Nova_TarefaActivity.class);
				intent.putExtra("nome", list.get(auxPosition).getNome());
				intent.putExtra("id", list.get(auxPosition).getId());
				context.startActivity(intent);
			}
		});

		return layout;
	}

}
