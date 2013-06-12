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
import com.example.stardapio.bean.Item;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MenuAdapter extends BaseAdapter {
	private List<Item> itens;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private ImageLoader imageLoader;

	static class ViewHolder {
		private TextView tvNome;
		private TextView tvDescricao;
		private TextView tvPreco;
		private ImageView img;
	}

	public MenuAdapter(Context context, List<Item> itens,
			ImageLoaderConfiguration config) {
		mInflater = LayoutInflater.from(context);
		this.itens = itens;
		imageLoader = ImageLoader.getInstance();
		
	}

	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public Object getItem(int index) {
		return itens.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup arg2) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_adapter, null);
			holder = new ViewHolder();

			holder.tvNome = (TextView) convertView.findViewById(R.id.item_nome);
			holder.tvPreco = (TextView) convertView
					.findViewById(R.id.item_preco);
			holder.tvDescricao = (TextView) convertView
					.findViewById(R.id.item_descricao);
			holder.img = (ImageView) convertView.findViewById(R.id.item_img);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Item i = itens.get(posicao);

		holder.tvNome.setText(i.getNome());
		holder.tvPreco.setText(i.getPreco());
		holder.tvDescricao.setText(i.getDescricao());

		String imageUrl = i.getUrlImage();

		imageLoader.displayImage(imageUrl, holder.img);

		return convertView;
	}

}