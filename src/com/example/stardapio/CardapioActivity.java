package com.example.stardapio;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stardapio.adapter.MenuAdapter;
import com.example.stardapio.bean.Item;
import com.example.stardapio.webservice.RestaurantREST;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class CardapioActivity extends ListActivity {
	ImageLoaderConfiguration config;

	private class GetAsync extends AsyncTask<Void, Void, List<Item>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(CardapioActivity.this);
			dialog.show();
		}

		@Override
		protected List<Item> doInBackground(Void... arg0) {
			RestaurantREST rest = new RestaurantREST();
			List<Item> itens = null;
			String id = getIntent().getExtras().getString("idRestaurante");
			try {
				itens = rest.getListaItem(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return itens;
		}

		@Override
		protected void onPostExecute(List<Item> result) {
			ListView lv = getListView();

			MenuAdapter adapter = new MenuAdapter(getApplicationContext(),
					result, config);

			lv.setAdapter(adapter);
			dialog.dismiss();
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cardapio);
		new GetAsync().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cardapio, menu);
		return true;
	}

	protected void onListItemClick(ListView l, View view, int position, long id) {
		// faz uma requisicao/pedido do item selecionado
		// ==============================================
		// apenas para depuracao
		String selection = l.getItemAtPosition(position).toString();
		Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
		// ==============================================
	}

}