package com.example.stardapio.fragments;

import android.app.Fragment;
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
	public static final String ARG_DESCRICAO = "descricao";
	public static final String ARG_IMAGE = "image";

	private int mPageNumber;
	private String mDescricao;
	private String mUrlImage;
	
	public static ScreenSlidePageFragment create(int pageNumber, Item item) {

		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
		Bundle args = new Bundle();

		args.putInt(ARG_PAGE, pageNumber);
		args.putString(ARG_DESCRICAO, item.getDescricao());
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
		mDescricao = getArguments().getString(ARG_DESCRICAO);
		mUrlImage = getArguments().getString(ARG_IMAGE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_screen_slide_page, container, false);

		((TextView) rootView.findViewById(android.R.id.text1))
				.setText(mDescricao);

		ImageView imageView = (ImageView) rootView.findViewById(R.id.image);
		ImageLoader imageLoader = ImageLoader.getInstance();

		imageLoader.displayImage(mUrlImage, imageView);

		return rootView;
	}

	public int getPageNumber() {
		return mPageNumber;
	}
}