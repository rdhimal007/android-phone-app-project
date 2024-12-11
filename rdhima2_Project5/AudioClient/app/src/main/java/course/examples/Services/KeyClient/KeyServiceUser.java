package course.examples.Services.KeyClient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import course.examples.Services.KeyCommon.KeyGenerator;

public class KeyServiceUser extends Activity {

	protected static final String TAG = "KeyServiceUser";
	protected static final int PERMISSION_REQUEST = 0;
	private KeyGenerator mKeyGeneratorService;
	private boolean mIsBound = false;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.main);

		final TextView output = findViewById(R.id.output);

		final Button goButton = findViewById(R.id.go_button);
		goButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				try {

					// Call KeyGenerator and get a new ID
					if (mIsBound) {
						// Calling the service through proxy mKeyGeneratorService!
						String textFromService = mKeyGeneratorService.getKey()[0] ;
						output.setText(textFromService);	// display string returned from service
					} else {
						Log.i(TAG, "Ugo says that the service was not bound!");
					}

				} catch (RemoteException e) {

					Log.e(TAG, e.toString());

				}
			}
		});
	}

	// Bind to KeyGenerator Service
	@Override
	protected void onStart() {
		super.onStart();

		if (checkSelfPermission("course.examples.Services.KeyService.GEN_ID")
			!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this,
					new String[]{"course.examples.Services.KeyService.GEN_ID"},
					PERMISSION_REQUEST);
		}
		else {
			checkBindingAndBind();
		}
	}

	protected void checkBindingAndBind() {
		if (!mIsBound) {

			boolean b ;
			Intent i = new Intent(KeyGenerator.class.getName());
			Log.e("Key client", i.toString()) ;

			// UB:  Stoooopid Android API-21 no longer supports implicit intents
			// to bind to a service #@%^!@..&**!@
			// Must make intent explicit or lower target API level to 20.
			Context c = null ;
			try {
				c = createPackageContext("course.examples.Services.KeyService", 0) ;
				Log.e("Ugo", "Ugo says: " + c.toString()) ;
			} catch (PackageManager.NameNotFoundException e) {
				throw new RuntimeException(e);
			}
			finally {
				Log.e("Ugo", "Ugo says: Package context is " + c.toString()) ;
			}
			ResolveInfo info = getPackageManager().resolveService(i, PackageManager.MATCH_ALL);
			i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

			b = bindService(i, this.mConnection, Context.BIND_AUTO_CREATE);
			if (b) {
				Log.i(TAG, "Ugo says bindService() succeeded!");
			} else {
				Log.i(TAG, "Ugo says bindService() failed!");
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case PERMISSION_REQUEST: {

				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

					// Permission granted, go ahead and bind to service
					checkBindingAndBind();
				}
				else {
					Toast.makeText(this, "BUMMER: No Permission :-(", Toast.LENGTH_LONG).show() ;
				}
			}
			default: {
				// do nothing
			}
		}
	}
	// Unbind from KeyGenerator Service
	@Override
	protected void onStop() {

		super.onStop();

		if (mIsBound) {
			unbindService(this.mConnection);
		}
	}

	private final ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder iservice) {

			mKeyGeneratorService = KeyGenerator.Stub.asInterface(iservice);
			mIsBound = true;
		}

		public void onServiceDisconnected(ComponentName className) {

			mKeyGeneratorService = null;
			mIsBound = false;

		}
	};

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}
}
