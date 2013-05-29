package com.example.stardapio.webservice;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpRestaurantSingleton {
	private static final int JSON_CONNECTION_TIMEOUT = 3000;
	private static final int JSON_SOCKET_TIMEOUT = 5000;
	private static HttpRestaurantSingleton instance;
	private HttpParams httpParameters;
	private DefaultHttpClient httpclient;

	private void setTimeOut(HttpParams params) {
		HttpConnectionParams.setConnectionTimeout(params,
				JSON_CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, JSON_SOCKET_TIMEOUT);
	}

	private HttpRestaurantSingleton() {
		httpParameters = new BasicHttpParams();
		setTimeOut(httpParameters);
		httpclient = new DefaultHttpClient(httpParameters);
	}

	public static DefaultHttpClient getHttpClientInstace() {
		if (instance == null)
			instance = new HttpRestaurantSingleton();
		return instance.httpclient;
	}

}