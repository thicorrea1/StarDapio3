package com.example.stardapio;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stardapio.adapter.TypeAdapter;
import com.example.stardapio.adapter.TypesAdapter;
import com.example.stardapio.bean.ContainerTypeAndSubType;
import com.example.stardapio.bean.Type;
import com.example.stardapio.webservice.RestaurantREST;

public class TypesActivity extends Activity {
	Activity activity;
	ExpandableListAdapter adapter;
	ExpandableListView eListView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.types);

		activity = this;
		eListView
				.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {
						Toast.makeText(getApplicationContext(),
								adapter.getGroup(groupPosition).toString(),
								Toast.LENGTH_SHORT).show();
						return false;
					}
				});

		eListView
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						Intent intent = new Intent(getApplicationContext(), MenuSlideActivity.class);
						String idType = String.valueOf(id);
						intent.putExtra("idRestaurante", getIntent().getExtras().getString("idRestaurante"));
						intent.putExtra("idType", idType);
						startActivity(intent);
						return false;
					}
				});
	}
	
	private class GetAsync extends AsyncTask<Void, Void, ContainerTypeAndSubType> {

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

			eListView = (ExpandableListView) findViewById(R.id.expandableType);
			adapter = new TypesAdapter(activity, result);
			eListView.setAdapter(adapter);
			dialog.dismiss();
		}

	}

}
