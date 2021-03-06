package com.example.stardapio;

import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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
			String idType = getIntent().getExtras().getString("idType");
			String idRestaurant = getIntent().getExtras().getString("idRestaurante");
			Log.i("ID_TYPE", idType);
			try {
				itens = rest.getListaItemType(idType, idRestaurant);
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
		Button button = (Button) findViewById(R.id.scan_button);

		if (MyApp.getMesa() == null) {
			((Button) button).setText("Scan");
		} else {
			((Button) button).setText("Chamar Garcon..Nao Implementado Yet");
		}
		new GetAsync().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cardapio, menu);
		return true;
	}

	protected void onListItemClick(ListView l, View view, int position, long id) {
		Intent intent = new Intent(this, MenuSlideActivity.class);
		String idSelect = String.valueOf(id);
		intent.putExtra("idRestaurante", getIntent().getExtras().getString("idRestaurante"));
		intent.putExtra("idType", getIntent().getExtras().getString("idType"));
		intent.putExtra("idSelect", idSelect);
		startActivity(intent);
	}

	public void scan(View button) {
		if (MyApp.getMesa() == null) {
			startActivity(new Intent(this, QRCodeActivity.class));
			((Button) button).setText("Chamar Garcon..Nao Implementado Yet");
		} else {
			// Chamar o garcon ou nao sei oq!!
		}
	}
}