package com.example.stardapio;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class StarDapioActivity extends FragmentActivity {

	private GoogleMap mMap = null;
	private GoogleMapOptions options = null;

	private HashMap<Marker, Integer> markerMap = new HashMap<Marker, Integer>();
	private ImageLoaderConfiguration config;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();
		config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions).build();
		ImageLoader.getInstance().init(config);

		setUpMapIfNeeded();

		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Location location = lm
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (location == null) {
			location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}

		LatLng cameraInitial = new LatLng(location.getLatitude(),
				location.getLongitude());

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
		// Intent intent = new Intent(this, TypeActivity.class);
		Intent intent = new Intent(this, TypesActivity.class);
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

			for (Restaurant r : result) {
				Marker marker = mMap.addMarker(new MarkerOptions()
						.position(new LatLng(r.getLat(), r.getLng()))
						.title(r.getName()).snippet("Ver Cardapio"));
				markerMap.put(marker, r.getIdRestaurant());
				Log.i("LOOP", r.getName());
			}
		}

	}

}