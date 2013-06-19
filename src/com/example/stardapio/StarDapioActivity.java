package com.example.stardapio;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.stardapio.bean.Restaurant;
import com.example.stardapio.webservice.RestaurantREST;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class StarDapioActivity extends FragmentActivity {

	private GoogleMap mMap = null;
	private GoogleMapOptions options = null;
	
	private CameraPosition cameraPosition = null;

	private HashMap<Marker, Integer> markerMap = new HashMap<Marker, Integer>();
	private ImageLoaderConfiguration config;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.discCache(new UnlimitedDiscCache(cacheDir)).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);

		setUpMapIfNeeded();

		// obter localizacao
		LatLng cameraInitial = new LatLng(-23.570664, -46.645117);
		//cameraInitial = cameraPosition.target;
		
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraInitial, 0));

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(cameraInitial).zoom(10).bearing(90).tilt(30).build();
		mMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));

		new GetAsync().execute();

		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {
				goCardapio(markerMap.get(marker).toString());
			}
		});

		mMap.setOnCameraChangeListener(new OnCameraChangeListener() {

			@Override
			public void onCameraChange(CameraPosition position) {
				Log.i("POSITION", position.toString());
				Log.i("mMap", mMap.getCameraPosition().toString());
				float zoom = position.zoom;
				if (zoom < mMap.getCameraPosition().zoom) {
					// 
				}
			}
		});
		mMap.setMyLocationEnabled(true);
	}

	private void setUpMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			if (mMap != null) {
				// Ok (y)
				mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			}
		} else {
			throw new RuntimeException("AHahahha");
		}
	}

	private void goCardapio(String id) {
		Intent intent = new Intent(this, CardapioActivity.class);
		intent.putExtra("idRestaurante", id);
		startActivity(intent);
	}

	private class GetAsync extends AsyncTask<Void, Void, List<Restaurant>> {

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

			options = new GoogleMapOptions();
			options.mapType(GoogleMap.MAP_TYPE_HYBRID).compassEnabled(false)
					.rotateGesturesEnabled(false).tiltGesturesEnabled(false);
			SupportMapFragment.newInstance(options);

			// arrumar latlng no bd
			LatLng caraDeFome = new LatLng(-23.570664, -46.645117);
			LatLng outroRestaurante = new LatLng(-23.568422, -46.647906);

			LatLng[] positions = { caraDeFome, outroRestaurante };

			for (Restaurant r : result) {
				Marker marker = mMap.addMarker(new MarkerOptions()
						.position(positions[r.getIdRestaurant() - 1]).title(r.getName())
						.snippet("Ver Cardapio"));
				markerMap.put(marker, r.getIdRestaurant());
				Log.i("LOOP", r.getName());
			}
			/*
			 * Marker marker1 = mMap.addMarker(new MarkerOptions()
			 * .position(caraDeFome).title("Cara de Fome")
			 * .snippet("Ver Cardapio"));
			 * 
			 * Marker marker2 = mMap.addMarker(new MarkerOptions() .position(new
			 * LatLng(20, 44)).title("Outro Restaurante")
			 * .snippet("Ver Cardapio"));
			 * 
			 * markerMap.put(marker1, 1); markerMap.put(marker2, 2);
			 */
		}

	}

}

/*
 * 
 * package com.example.stardapio;
 * 
 * import java.io.File; import java.util.List;
 * 
 * import android.app.ListActivity; import android.app.ProgressDialog; import
 * android.content.Intent; import android.os.AsyncTask; import
 * android.os.Bundle; import android.view.Menu; import android.view.View; import
 * android.widget.ListView; import android.widget.Toast;
 * 
 * import com.example.stardapio.adapter.RestaurantAdapter; import
 * com.example.stardapio.bean.Restaurant; import
 * com.example.stardapio.webservice.RestaurantREST; import
 * com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache; import
 * com.nostra13.universalimageloader.core.ImageLo)aderConfiguration; import
 * com.nostra13.universalimageloader.utils.StorageUtils;
 * 
 * public class StarDapioActivity extends ListActivity {
 * ImageLoaderConfiguration config;
 * 
 * private class GetAsync extends AsyncTask<Void, Void, List<Restaurant>> {
 * 
 * private ProgressDialog dialog;
 * 
 * @Override protected void onPreExecute() { dialog = new
 * ProgressDialog(StarDapioActivity.this); dialog.show(); }
 * 
 * @Override protected List<Restaurant> doInBackground(Void... arg0) {
 * RestaurantREST rest = new RestaurantREST(); List<Restaurant> restaurantes =
 * null; try { restaurantes = rest.getListaRestaurante(); } catch (Exception e)
 * { e.printStackTrace(); } return restaurantes; }
 * 
 * @Override protected void onPostExecute(List<Restaurant> result) { ListView lv
 * = getListView();
 * 
 * RestaurantAdapter adapter = new RestaurantAdapter( getApplicationContext(),
 * result, config);
 * 
 * lv.setAdapter(adapter); dialog.dismiss(); } }
 * 
 * @Override protected void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState); setContentView(R.layout.main);
 * 
 * File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
 * config = new ImageLoaderConfiguration.Builder(getApplicationContext())
 * .discCache(new UnlimitedDiscCache(cacheDir)).build(); MyApp appConfig =
 * ((MyApp) getApplicationContext()); appConfig.setGlobalConfig(config); new
 * GetAsync().execute(); }
 * 
 * @Override protected void onListRestaurantClick(ListView l, View view, int
 * position, long id) { // Intent intent = new Intent(this,
 * CardapioActivity.class); Intent intent = new Intent(this,
 * MenuSlideActivity.class); String selection =
 * l.getRestaurantAtPosition(position).toString();
 * intent.putExtra("nomeDoRestaurante", selection); // Apenas para depuracao
 * String idRestaurante = String.valueOf(id); Toast.makeText(this,
 * idRestaurante, Toast.LENGTH_LONG).show(); intent.putExtra("idRestaurante",
 * idRestaurante); startActivity(intent); // startActivity(new
 * Intent(StarDapioActivity.this, // MenuSlideActivity.class)); }
 * 
 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
 * menu; this adds Restaurants to the action bar if it is present.
 * getMenuInflater().inflate(R.menu.main, menu); return true; }
 * 
 * public void startScan(View view) { Intent intent = new Intent(this,
 * QRCodeActivity.class); startActivity(intent); }
 * 
 * }
 */
