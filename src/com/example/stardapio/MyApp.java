package com.example.stardapio;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApp extends Application {

	// instance
	private static MyApp instance = null;
	private ImageLoaderConfiguration globalConfig = null;

	
	public ImageLoaderConfiguration getGlobalConfig() {
		return globalConfig;
	}

	public void setGlobalConfig(ImageLoaderConfiguration globalConfig) {
		this.globalConfig = globalConfig;
	}

	private static void checkInstance() {
		if (instance == null)
			throw new IllegalStateException("Application not created yet!");
	}

	public static MyApp getInstance() {
		checkInstance();
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// provide an instance for our static accessors
		instance = this;
	}
}
