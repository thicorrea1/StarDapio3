package com.example.stardapio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.stardapio.adapter.TypesAdapter;
import com.example.stardapio.bean.ContainerTypeAndSubType;
import com.example.stardapio.webservice.RestaurantREST;

public class TypesActivity extends ActionBarActivity {
	Activity activity;
	ExpandableListAdapter adapter;
	ExpandableListView eListView;
	private TextView buttonScan;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.types);

		Button button = (Button) findViewById(R.id.scan_button);

		if (MyApp.getMesa() == null) {
			((Button) button).setText("Scan");
		} else {
			((Button) button).setText("Mesa " + MyApp.getMesa());
		}
		new GetAsync().execute();

		eListView = (ExpandableListView) findViewById(R.id.expandableType);
		activity = this;
		eListView
				.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {
						// Log.i("TAG", noChild() + "");
						if (adapter.getChildrenCount(groupPosition) == 0) {
							Log.i("TAG", "NOCHILD");
							goMenuSlide(id);
						} else {
							Log.i("TAG", "HASCHILD");
						}
						return false;
					}
				});

		eListView
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						goMenuSlide(id);
						return false;
					}
				});
	}

	protected void goMenuSlide(long id) {
		Intent intent = new Intent(getApplicationContext(),
				MenuSlideActivity.class);
		String idType = String.valueOf(id);
		intent.putExtra("idRestaurante",
				getIntent().getExtras().getString("idRestaurante"));
		intent.putExtra("idType", idType);
		startActivity(intent);
	}

	public void scan(View button) {
		startActivity(new Intent(this, QRCodeActivity.class));
		buttonScan = (Button) button;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (buttonScan != null)
			buttonScan.setText("Mesa " + MyApp.getMesa());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i("TAG", " " + item.getItemId());
		switch (item.getItemId()) {
		case R.id.action_pedidos:
			showPedidos();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showPedidos() {
		Intent intent = new Intent(getApplicationContext(),
				PedidosActivity.class);
		startActivity(intent);
	}

	private class GetAsync extends
			AsyncTask<Void, Void, ContainerTypeAndSubType> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(TypesActivity.this);
			dialog.show();
		}

		@Override
		protected ContainerTypeAndSubType doInBackground(Void... arg0) {
			RestaurantREST rest = new RestaurantREST();
			ContainerTypeAndSubType container = null;
			String id = getIntent().getExtras().getString("idRestaurante");
			Log.i("ID_RESTAURANTE_ACTIVITY", id);
			try {
				container = rest.getListaTypeAndSubType(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return container;
		}

		@Override
		protected void onPostExecute(ContainerTypeAndSubType result) {
			Log.i("RESULT", result.toString());
			adapter = new TypesAdapter(activity, result);
			eListView.setAdapter(adapter);
			dialog.dismiss();
		}
	}
}
