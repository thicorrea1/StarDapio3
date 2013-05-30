package com.example.stardapio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class StarDapioActivity extends FragmentActivity {

	private GoogleMap mMap = null;
	private GoogleMapOptions options = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setUpMapIfNeeded();

		options = new GoogleMapOptions();
		options.mapType(GoogleMap.MAP_TYPE_HYBRID).compassEnabled(false)
				.rotateGesturesEnabled(false).tiltGesturesEnabled(false);
		SupportMapFragment.newInstance(options);

		mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title(
				"Cara de Fome"));

		mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
			
			@Override
			public View getInfoContents(Marker marker) {
				TextView view = ((TextView) findViewById(R.id.info));
				view.setText("Rua: Lá na PQP!");
				return view;
			}

			@Override
			public View getInfoWindow(Marker marker) {

				return null;
			}
		});
		
		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				goCardapio();
				return true;
			}
		});
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (mMap != null) {
				// Ok (y)
				mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			}
		} else {
			throw new RuntimeException("AHahahha");
		}
	}
	
	private void goCardapio() {
		Intent intent = new Intent(this, CardapioActivity.class);
		startActivity(intent);
	}
}

/*
 *
 * package com.example.stardapio;

import java.io.File;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stardapio.adapter.RestaurantAdapter;
import com.example.stardapio.bean.Restaurant;
import com.example.stardapio.webservice.RestaurantREST;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class StarDapioActivity extends ListActivity {
	ImageLoaderConfiguration config;

	private class GetAsync extends AsyncTask<Void, Void, List<Restaurant>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(StarDapioActivity.this);
			dialog.show();
		}

		@Override
		protected List<Restaurant> doInBackground(Void... arg0) {
			RestaurantREST rest = new RestaurantREST();
			List<Restaurant> restaurantes = null;
			try {
				restaurantes = rest.getListaRestaurante();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return restaurantes;
		}

		@Override
		protected void onPostExecute(List<Restaurant> result) {
			ListView lv = getListView();

			RestaurantAdapter adapter = new RestaurantAdapter(
					getApplicationContext(), result, config);

			lv.setAdapter(adapter);
			dialog.dismiss();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.discCache(new UnlimitedDiscCache(cacheDir)).build();
		MyApp appConfig = ((MyApp) getApplicationContext());
		appConfig.setGlobalConfig(config);
		new GetAsync().execute();
	}

	@Override
	protected void onListItemClick(ListView l, View view, int position, long id) {
		// Intent intent = new Intent(this, CardapioActivity.class);
		Intent intent = new Intent(this, MenuSlideActivity.class);
		String selection = l.getItemAtPosition(position).toString();
		intent.putExtra("nomeDoRestaurante", selection);
		// Apenas para depuracao
		String idRestaurante = String.valueOf(id);
		Toast.makeText(this, idRestaurante, Toast.LENGTH_LONG).show();
		intent.putExtra("idRestaurante", idRestaurante);
		startActivity(intent);
		// startActivity(new Intent(StarDapioActivity.this,
		// MenuSlideActivity.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void startScan(View view) {
		Intent intent = new Intent(this, QRCodeActivity.class);
		startActivity(intent);
	}

}
 * 
 */
 