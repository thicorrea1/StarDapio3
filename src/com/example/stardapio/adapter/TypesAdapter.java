package com.example.stardapio.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.stardapio.bean.SubType;
import com.example.stardapio.bean.Type;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TypesAdapter extends BaseExpandableListAdapter {

	private ImageLoader imageLoader;
	private Activity activity;
	private Type typeA = new Type();
	private Type typeB = new Type();
	public List<Type> types = new ArrayList<Type>();
	private SubType subTypeA = new SubType(typeA);
	private SubType subTypeB = new SubType(typeA);
	private SubType subTypeC = new SubType(typeB);
	private SubType subTypeD = new SubType(typeB);
	private SubType subTypeE = new SubType(typeB);
	public SubType[][] subTypes = new SubType[][] { { subTypeA, subTypeB },
			{ subTypeC, subTypeD, subTypeE } };

	public TypesAdapter(Activity activity) {
		this.activity = activity;
		types.add(typeA);
		types.add(typeB);
		typeA.setName("Massas");
		typeA.setUrlImage("http://www.restauranteamerica.com.br/templates/img/cardapio/banner-pasta-e-cia.jpg");
		typeB.setName("Bebidas");
		typeB.setUrlImage("http://www.restauranteamerica.com.br/templates/img/cardapio/banner-bebidas.jpg");

		subTypeA.setName("Espaguete");
		subTypeA.setId_type(1);
		subTypeB.setName("Penne");
		subTypeB.setId_type(1);
		subTypeC.setName("Refrigerante");
		subTypeC.setId_type(2);
		subTypeD.setName("Refrigerante Diet");
		subTypeD.setId_type(2);
		subTypeE.setName("Sucos");
		subTypeE.setId_type(2);

		imageLoader = ImageLoader.getInstance();
	}

	@Override
	public Object getChild(int groupPosition, int childPostition) {
		return subTypes[groupPosition][childPostition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return subTypes[groupPosition][childPosition].getId_type();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView txt = new TextView(activity);
		txt.setText(subTypes[groupPosition][childPosition].getName());
		txt.setPadding(10, 0, 0, 0);
		txt.setTextSize(30);
		return txt;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return subTypes[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return types.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return types.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return types.get(groupPosition).getId_type();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		ImageView img = new ImageView(this.activity);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		imageLoader.displayImage(types.get(groupPosition).getUrlImage(), img);

		TextView txt = new TextView(this.activity);
		txt.setText(types.get(groupPosition).getName());
		
		txt.setTextSize(30);
		txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

		RelativeLayout relativeLayout = new RelativeLayout(this.activity);

		relativeLayout.addView(txt);
		relativeLayout.addView(img);

		return relativeLayout;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

}
