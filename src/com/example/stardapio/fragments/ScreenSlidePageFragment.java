package com.example.stardapio.fragments;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stardapio.R;
import com.example.stardapio.bean.Item;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ScreenSlidePageFragment extends Fragment {

	public static final String ARG_PAGE = "page";
	private static final String ARG_PRICE = "price";
	private static final String ARG_NAME = "name";
	public static final String ARG_DESCRICAO = "descricao";
	public static final String ARG_IMAGE = "image";

	private int mPageNumber;
	private String mPrice;
	private String mName;
	private String mDescricao;
	private String mUrlImage;

	public static ScreenSlidePageFragment create(int pageNumber, Item item) {

		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
		Bundle args = new Bundle();

		args.putInt(ARG_PAGE, pageNumber);
		args.putString(ARG_PRICE, String.valueOf(item.getPrice()));
		args.putString(ARG_NAME, item.getName());
		args.putString(ARG_DESCRICAO, item.getDescription());
		args.putString(ARG_IMAGE, item.getUrlImage());
		fragment.setArguments(args);

		return fragment;
	}

	public ScreenSlidePageFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
		mPrice = getArguments().getString(ARG_PRICE);
		mName = getArguments().getString(ARG_NAME);
		mDescricao = getArguments().getString(ARG_DESCRICAO);
		mUrlImage = getArguments().getString(ARG_IMAGE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_screen_slide_page, container, false);

		ImageView imageView = (ImageView) rootView.findViewById(R.id.image);
		ImageLoader imageLoader = ImageLoader.getInstance();

		imageLoader.displayImage(mUrlImage, imageView);
		
		((TextView) rootView.findViewById(android.R.id.text1))
				.setText(mName);
		
		((TextView) rootView.findViewById(R.id.price))
				.setText("R$ " + mPrice);

		((TextView) rootView.findViewById(R.id.description))
				.setText(mDescricao);

		return rootView;
	}

	public int getPageNumber() {
		return mPageNumber;
	}
}