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

import com.example.stardapio.adapter.TypeAdapter;
import com.example.stardapio.bean.Type;
import com.example.stardapio.webservice.RestaurantREST;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class TypeActivity extends ListActivity {
	ImageLoaderConfiguration config;

	private class GetAsync extends AsyncTask<Void, Void, List<Type>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(TypeActivity.this);
			dialog.show();
		}

		@Override
		protected List<Type> doInBackground(Void... arg0) {
			RestaurantREST rest = new RestaurantREST();
			List<Type> types = null;
			String id = getIntent().getExtras().getString("idRestaurante");
			Log.i("ID_RESTAURANTE_ACTIVITY", id);
			try {
				types = rest.getListaType(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return types;
		}

		@Override
		protected void onPostExecute(List<Type> result) {
			ListView lv = getListView();

			TypeAdapter adapter = new TypeAdapter(getApplicationContext(),
					result, config);

			lv.setAdapter(adapter);
			dialog.dismiss();
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.type);
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
		getMenuInflater().inflate(R.menu.cardapio, menu);
		return true;
	}

	protected void onListItemClick(ListView l, View view, int position, long id) {
		Intent intent = new Intent(this, CardapioActivity.class);
		String idType = String.valueOf(id);
		Log.i("long id", idType);
		intent.putExtra("idType", idType);
		intent.putExtra("idRestaurante", getIntent().getExtras().getString("idRestaurante"));
		startActivity(intent);
	}
}
