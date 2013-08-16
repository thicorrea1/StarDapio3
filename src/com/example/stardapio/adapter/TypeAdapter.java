package com.example.stardapio.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stardapio.R;
import com.example.stardapio.bean.Type;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class TypeAdapter extends BaseAdapter {
	private List<Type> types;
	private LayoutInflater mInflater;
	private ViewHolder holder;

	static class ViewHolder {
		private TextView tvName;
	}

	public TypeAdapter(Context context, List<Type> types,
			ImageLoaderConfiguration config) {
		mInflater = LayoutInflater.from(context);
		this.types = types;
	}

	@Override
	public int getCount() {
		return types.size();
	}

	@Override
	public Object getItem(int index) {
		return types.get(index);
	}

	@Override
	public long getItemId(int index) {
		return types.get(index).getId_type();
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup arg2) {

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.type_adapter, null);
			holder = new ViewHolder();

			holder.tvName = (TextView) convertView.findViewById(R.id.type_name);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Type i = types.get(posicao);

		holder.tvName.setText(i.getType());

		return convertView;
	}
}