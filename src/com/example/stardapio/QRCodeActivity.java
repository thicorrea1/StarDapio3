package com.example.stardapio;

import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class QRCodeActivity extends Activity {
	private Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qrcode);

		// txtScanResult = (TextView) findViewById(R.id.scan_result);
		// View btnScan = findViewById(R.id.scan_button);

		// Scan button
		// btnScan.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// set the last parameter to true to open front light if available
		IntentIntegrator.initiateScan(QRCodeActivity.this, R.layout.capture,
				R.id.viewfinder_view, R.id.preview_view, true);
		// }
		// });
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case IntentIntegrator.REQUEST_CODE:
			IntentResult scanResult = IntentIntegrator.parseActivityResult(
					requestCode, resultCode, data);
			if (scanResult == null) {
				return;
			}
			final String result = scanResult.getContents();
			if (result != null) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						// txtScanResult.setText(result);
						MyApp.setMesa(result);
						Log.i("MESA NUMERO =>", MyApp.getMesa());
					}
				});
				Toast.makeText(this, result, Toast.LENGTH_LONG).show();
			}
			break;
		default:
		}
		finish();
	}
}