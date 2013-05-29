package com.example.stardapio;

import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.stardapio.bean.Item;
import com.example.stardapio.fragments.ScreenSlidePageFragment;
import com.example.stardapio.webservice.RestaurantREST;

public class MenuSlideActivity extends FragmentActivity {

	/**
	 * Demonstrates a "screen-slide" animation using a {@link ViewPager}.
	 * Because {@link ViewPager} automatically plays such an animation when
	 * calling {@link ViewPager#setCurrentItem(int)}, there isn't any
	 * animation-specific code in this sample.
	 * 
	 * <p>
	 * This sample shows a "next" button that advances the user to the next step
	 * in a wizard, animating the current screen out (to the left) and the next
	 * screen in (from the right). The reverse animation is played when the user
	 * presses the "previous" button.
	 * </p>
	 * 
	 * @see ScreenSlidePageFragment
	 */

	/**
	 * The number of pages (wizard steps) to show in this demo.
	 */
	private static final int NUM_PAGES = 5;

	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;

	private String urlImage;
	private List<Item> itens;

	private class GetAsync extends AsyncTask<Void, Void, List<Item>> {

		@Override
		protected List<Item> doInBackground(Void... arg0) {
			RestaurantREST rest = new RestaurantREST();
			List<Item> listaItem = null;
			String id = getIntent().getExtras().getString("idRestaurante");
			try {
				listaItem = rest.getListaItem(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.i("MenuSlide", "itens: " + listaItem.get(0).getUrlImage());
			return listaItem;
		}

		@Override
		protected void onPostExecute(List<Item> result) {
			Log.i("onPostExecute", "itens: " + itens);
			itens = result;
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen_slide);
		new GetAsync().execute();

		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When changing pages, reset the action bar actions since they
				// are dependent
				// on which page is currently active. An alternative approach is
				// to have each
				// fragment expose actions itself (rather than the activity
				// exposing actions),
				// but for simplicity, the activity provides the actions in this
				// sample.
				invalidateOptionsMenu();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

		menu.findItem(R.id.action_previous).setEnabled(
				mPager.getCurrentItem() > 0);

		// Add either a "next" or "finish" button to the action bar, depending
		// on which page
		// is currently selected.
		MenuItem item = menu
				.add(Menu.NONE,
						R.id.action_next,
						Menu.NONE,
						(mPager.getCurrentItem() == mPagerAdapter.getCount() - 1) ? R.string.action_finish
								: R.string.action_next);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// Navigate "up" the demo structure to the launchpad activity.
			// See http://developer.android.com/design/patterns/navigation.html
			// for more.
			NavUtils.navigateUpTo(this, new Intent(this,
					StarDapioActivity.class));
			return true;

		case R.id.action_previous:
			// Go to the previous step in the wizard. If there is no previous
			// step,
			// setCurrentItem will do nothing.
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			return true;

		case R.id.action_next:
			// Advance to the next step in the wizard. If there is no next step,
			// setCurrentItem
			// will do nothing.
			mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment}
	 * objects, in sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			urlImage = "http://s.glbimg.com/es/ge/f/original/2010/11/19/santos_45x45.png";
			//Item item = itens.get(position);
			//urlImage = item.getUrlImage();
			return ScreenSlidePageFragment.create(position, urlImage);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}
}