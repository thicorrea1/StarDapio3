package com.example.stardapio;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stardapio.adapter.TypesAdapter;
import com.example.stardapio.bean.ContainerTypeAndSubType;
import com.example.stardapio.bean.Item;
import com.example.stardapio.bean.Pedido;
import com.example.stardapio.webservice.RestaurantREST;

public class PedidosActivity extends Activity {

	private ViewGroup mContainerView;
	private List<Item> itens;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pedidos);
		mContainerView = (ViewGroup) findViewById(R.id.container);
		// getIntent().getExtras().getString("");
		itens = MyApp.getPedido().getItens();

		if (itens.size() > 0)
			findViewById(android.R.id.empty).setVisibility(View.GONE);

		for (Item i : itens) {
			addItem(i);
		}
	}

	private void addItem(Item i) {
		// Instantiate a new "row" view.
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(this)
				.inflate(R.layout.list_item, mContainerView, false);

		// Set the text in the new row to a random country.
		((TextView) newView.findViewById(android.R.id.text1)).setText(i
				.getName());

		final Item itemFinal = i;

		// Set a click listener for the "X" button in the row that will remove
		// the row.
		newView.findViewById(R.id.delete_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						// Remove the row from its parent (the container view).
						// Because mContainerView has
						// android:animateLayoutChanges set to true,
						// this removal is automatically animated.
						mContainerView.removeView(newView);

						MyApp.getPedido().deletePedido(itemFinal);

						// If there are no rows remaining, show the empty view.
						if (mContainerView.getChildCount() == 0) {
							findViewById(android.R.id.empty).setVisibility(
									View.VISIBLE);
						}
					}
				});

		// Because mContainerView has android:animateLayoutChanges set to true,
		// adding this view is automatically animated.
		mContainerView.addView(newView, 0);
	}

	public void send(View button) {
		new GetAsync().execute();
	}

	private class GetAsync extends AsyncTask<Void, Void, Void> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(PedidosActivity.this);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			RestaurantREST rest = new RestaurantREST();
			Pedido pedido = MyApp.getPedido();
			Log.i("TAG", pedido + "");
			if (pedido.getItens().size() == 0) {
				Toast.makeText(getApplicationContext(),
						"Adicione itens antes de enviar o pedido",
						Toast.LENGTH_LONG).show();
			} else {
				try {
					rest.addPedido(pedido);
					MyApp.getPedido().setItens(new ArrayList<Item>());
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Ocorreu um erro ao enviar o pedido",
							Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mContainerView.removeAllViews();
			dialog.dismiss();
		}
	}

}
