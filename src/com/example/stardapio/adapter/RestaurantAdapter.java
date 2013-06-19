package com.example.stardapio.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stardapio.R;
import com.example.stardapio.bean.Restaurant;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class RestaurantAdapter extends BaseAdapter {
	// Lista de Restaurantes
	private List<Restaurant> restaurants;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private ImageLoader imageLoader;

	static class ViewHolder {
		private TextView tvNome;
		private TextView tvEnd;
		private ImageView img;
	}

	public RestaurantAdapter(Context context, List<Restaurant> restaurants,
			ImageLoaderConfiguration config) {
		mInflater = LayoutInflater.from(context);
		this.restaurants = restaurants;
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

	@Override
	public int getCount() {
		return restaurants.size();
	}

	@Override
	public Object getItem(int index) {
		return restaurants.get(index);
	}

	@Override
	public long getItemId(int index) {
		return restaurants.get(index).getIdRestaurant();
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup arg2) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.restaurant_adapter, null);
			holder = new ViewHolder();

			holder.tvNome = (TextView) convertView
					.findViewById(R.id.restaurante_nome);
			holder.tvEnd = (TextView) convertView
					.findViewById(R.id.restaurante_endereco);
			holder.img = (ImageView) convertView
					.findViewById(R.id.restaurante_img);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Restaurant r = restaurants.get(posicao);

		holder.tvNome.setText(r.getName());
		holder.tvEnd.setText(r.getAddress());

		// String imageUrl =
		// "http://4.bp.blogspot.com/-w6NRgw49GqY/UFGyAFlIFnI/AAAAAAAAAcI/Zo2Jr7a4oMI/s1600/image2.jpeg";
		// String imageUrl = r.getUrlImage();

		// imageLoader.displayImage(imageUrl, holder.img);

		return convertView;
	}

}