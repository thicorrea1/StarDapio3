package com.example.stardapio.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stardapio.MyApp;
import com.example.stardapio.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy
 * title indicating the page number, along with some dummy text.
 * 
 * <p>
 * This class is used by the {@link CardFlipActivity} and
 * {@link ScreenSlideActivity} samples.
 * </p>
 */
public class ScreenSlidePageFragment extends Fragment {
	/**
	 * The argument key for the page number this fragment represents.
	 */
	public static final String ARG_PAGE = "page";
	public static final String ARG_URL_IMAGE = "urlImage";

	/**
	 * The fragment's page number, which is set to the argument value for
	 * {@link #ARG_PAGE}.
	 */
	private int mPageNumber;
	private String mUrlImage;

	/**
	 * Factory method for this fragment class. Constructs a new fragment for the
	 * given page number.
	 */
	
	public static ScreenSlidePageFragment create(int pageNumber, String urlImage) {
		ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		args.putString(ARG_URL_IMAGE, urlImage);
		fragment.setArguments(args); 
		return fragment;
	}

	public ScreenSlidePageFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
		mUrlImage = getArguments().getString(ARG_URL_IMAGE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout containing a title and body text.
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_screen_slide_page, container, false);

		// Set the title view to show the page number.
		// ((TextView) rootView.findViewById(android.R.id.text1)).setText(
		// getString(R.string.title_template_step, mPageNumber + 1));
		
		((TextView) rootView.findViewById(android.R.id.text1)).setText("hgjkfd");
		ImageView imageView = (ImageView) rootView.findViewById(R.id.image);
		ImageLoader imageLoader = ImageLoader.getInstance();
		//imageLoader.init(ImageLoaderConfiguration.createDefault(container.getContext()));
		MyApp myApp = ((MyApp)getActivity().getApplicationContext());
		imageLoader.init(myApp.getGlobalConfig());
		imageLoader.displayImage(mUrlImage, imageView);
		return rootView;
	}

	/**
	 * Returns the page number represented by this fragment object.
	 */
	public int getPageNumber() {
		return mPageNumber;
	}
}