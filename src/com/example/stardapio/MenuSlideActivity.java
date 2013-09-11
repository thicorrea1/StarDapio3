package com.example.stardapio;

//public class MenuSlideActivity {}

import java.util.List;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.stardapio.bean.Item;
import com.example.stardapio.fragments.ScreenSlidePageFragment;
import com.example.stardapio.webservice.RestaurantREST;

//import android.app.Fragment;

public class MenuSlideActivity extends FragmentActivity {

	private static int NUM_PAGES;

	private List<Item> itens;

	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen_slide);
		new GetAsync().execute();

		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

		menu.findItem(R.id.action_previous).setEnabled(
				mPager.getCurrentItem() > 0);

		MenuItem item = menu
				.add(Menu.NONE,
						R.id.action_next,
						Menu.NONE,
						(mPager.getCurrentItem() == mPagerAdapter.getCount() - 1) ? R.string.action_finish
								: R.string.action_next);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
					| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this, new Intent(this,
					StarDapioActivity.class));
			return true;

		case R.id.action_previous:
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			return true;

		case R.id.action_next:
			mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

		public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Item item = itens.get(position);
			return ScreenSlidePageFragment.create(position, item);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

	public void adicionar(View view) {
		if ((MyApp.getMesa() == null) || Integer.parseInt(MyApp.getMesa()) != itens.get(
				mPager.getCurrentItem()).getIdRestaurante()) {
			Toast.makeText(this,
					"Tire foto do QR da Mesa antes de fazer o pedido",
					Toast.LENGTH_LONG).show();
		} else {
			MyApp.getPedido().addItem(itens.get(mPager.getCurrentItem()));
			Toast.makeText(
					this,
					itens.get(mPager.getCurrentItem()).getName()
							+ " adicionado", Toast.LENGTH_SHORT).show();
			Log.i("TAG", MyApp.getPedido().getItens() + "");
		}
	}

	private class GetAsync extends AsyncTask<Void, Void, List<Item>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(MenuSlideActivity.this);
			dialog.show();
		}

		@Override
		protected List<Item> doInBackground(Void... arg0) {

			RestaurantREST rest = new RestaurantREST();
			List<Item> listaItem = null;
			String idRestaurant = getIntent().getExtras().getString(
					"idRestaurante");
			String idType = getIntent().getExtras().getString("idType");

			try {
				listaItem = rest.getListaItemType(idType, idRestaurant);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return listaItem;
		}

		@Override
		protected void onPostExecute(List<Item> result) {

			itens = result;
			NUM_PAGES = result.size();
			mPager.setAdapter(mPagerAdapter);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

					@TargetApi(Build.VERSION_CODES.HONEYCOMB)
					@Override
					public void onPageSelected(int position) {
						invalidateOptionsMenu();
					}
				});
			}
			dialog.dismiss();
		}

	}
}
