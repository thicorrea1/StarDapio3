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

import com.example.stardapio.bean.ContainerTypeAndSubType;
import com.example.stardapio.bean.SubType;
import com.example.stardapio.bean.Type;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TypesAdapter extends BaseExpandableListAdapter {

	private List<Type> types;
	private List<List<SubType>> subTypes;
	private ImageLoader imageLoader;
	private Activity activity;

	public TypesAdapter(Activity activity, ContainerTypeAndSubType container) {
		this.activity = activity;
		this.types = container.getTypes();
		organizeTypes(container.getTypes(), container.getSubTypes());
		imageLoader = ImageLoader.getInstance();
	}

	private void organizeTypes(List<Type> types2, List<SubType> subTypes2) {
		for (Type t : types2) {
			ArrayList<SubType> aux = new ArrayList<SubType>();
			for (SubType st : subTypes2) {
				if (t.getId_type() == st.getId_type()) {
					aux.add(st);
				}
			}
			subTypes.add(aux);
		}
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return subTypes.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return subTypes.get(groupPosition).get(childPosition).getId_type();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView txt = new TextView(this.activity);
		txt.setText(subTypes.get(groupPosition).get(childPosition).getName());
		txt.setPadding(10, 0, 0, 0);
		txt.setTextSize(30);
		return txt;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return subTypes.get(groupPosition).size();
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
		img.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT));
		imageLoader.displayImage(types.get(groupPosition).getUrlImage(), img);

		TextView txt = new TextView(this.activity);
		txt.setText(types.get(groupPosition).getName());

		txt.setTextSize(30);
		txt.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);

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
