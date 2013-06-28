package com.example.stardapio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.stardapio.adapter.TypesAdapter;

public class TypesActivity extends Activity {

	ExpandableListView eListView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.types);

		eListView = (ExpandableListView) this.findViewById(R.id.expandableType);
		final ExpandableListAdapter adapter = new TypesAdapter(this);
		eListView.setAdapter(adapter);

		eListView
				.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

					@Override
					public boolean onGroupClick(ExpandableListView parent,
							View v, int groupPosition, long id) {
						Toast.makeText(getApplicationContext(),
								adapter.getGroup(groupPosition).toString(),
								Toast.LENGTH_SHORT).show();
						return false;
					}
				});

		eListView
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						Intent intent = new Intent(getApplicationContext(), MenuSlideActivity.class);
						String idSelect = String.valueOf(id);
						intent.putExtra("idRestaurante", getIntent().getExtras().getString("idRestaurante"));
						intent.putExtra("idType", getIntent().getExtras().getString("idType"));
						intent.putExtra("idSelect", idSelect);
						startActivity(intent);
						return false;
					}
				});
	}
}
