package com.example.libAPI;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.aidl.ILogService;
import com.example.aidl.IMqttCb;
import com.example.aidl.Message;

public class TestActivity extends Activity implements OnClickListener {
	private static final String TAG = "Example";

	ILogService logService;
	LogConnection conn;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Request bind to the service
		conn = new LogConnection();
		Intent intent = new Intent("com.example.svc.Logger");
		intent.putExtra("version", "1.0");
		boolean bindSvc = bindService(intent, conn, Context.BIND_AUTO_CREATE);
		Log.d(TAG, "bindService = " + bindSvc);

		Button clr = (Button) findViewById(R.id.button1);
		clr.setOnClickListener(this);

		String packageName = this.getPackageName();
		testCert(packageName);
	}

	public void testCert(String packageName) {
		PackageManager pm = this.getPackageManager();

		int flags = PackageManager.GET_SIGNATURES;

		PackageInfo packageInfo = null;

		try {
			packageInfo = pm.getPackageInfo(packageName, flags);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Signature[] signatures = packageInfo.signatures;

		Log.d(TAG, "Number of signatures: " + signatures.length);

		for (Signature sign : signatures) {
			byte[] cert = sign.toByteArray();

			InputStream input = new ByteArrayInputStream(cert);

			CertificateFactory cf = null;
			try {
				cf = CertificateFactory.getInstance("X509");

			} catch (CertificateException e) {
				e.printStackTrace();
				return;
			}

			X509Certificate c = null;

			try {
				c = (X509Certificate) cf.generateCertificate(input);
			} catch (CertificateException e) {
				e.printStackTrace();
				return;
			}

			Log.d(TAG, "getSigAlgName: " + c.getSigAlgName());
			Log.d(TAG, "getIssuerDN: " + c.getIssuerDN());
			Log.d(TAG, "getSubjectDN: " + c.getSubjectDN());

			PublicKey key;
			// if self signed then it works
			key = c.getPublicKey();
			try {
				c.verify(key);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
				return;
			} catch (CertificateException e) {
				e.printStackTrace();
				return;
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return;
			} catch (NoSuchProviderException e) {
				e.printStackTrace();
				return;
			} catch (SignatureException e) {
				e.printStackTrace();
				return;
			}
			Log.d(TAG, "Signature verified");

			try {
				MessageDigest md = MessageDigest.getInstance("SHA1");
				byte[] publicKey = md.digest(c.getPublicKey().getEncoded());

				StringBuffer hexString = new StringBuffer();
				for (int i = 0; i < publicKey.length; i++) {
					String appendString = Integer
							.toHexString(0xFF & publicKey[i]);
					if (appendString.length() == 1)
						hexString.append("0");
					hexString.append(appendString);
				}

				Log.d(TAG, "Cert hash: " + hexString.toString());

			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
				return;
			}
		}
	}

	public void testSvc() {
		try {
			Log.d(TAG, "testSVC");
			logService.log_d("LogClient", "Hello from onClick()");
			Parcel pcl = Parcel.obtain();
			Message msg = new Message(pcl);
			msg.setTag("LogClient");
			msg.setText("Send msg");
			logService.pub("OOOOO náš pán");
			logService.log(msg);
			// register callback
			logService.regCallBack(cb);
			// logService.pub("Toto je loto ščýžýž+šáíáéřěíščřčš");
			String ret = logService.syncSendRecv("MyW/BackForth",
					"Send To Backend");

			debugToast("SynchroMsg: " + ret);
			pcl.recycle();
		} catch (RemoteException e) { //
			Log.e(TAG, "onClick failed", e);
		}
	}

	class LogConnection implements ServiceConnection {

		public void onServiceConnected(ComponentName name, IBinder service) {
			logService = ILogService.Stub.asInterface(service);
			debugToast("LogConnection.onServiceConnected");
		}

		public void onServiceDisconnected(ComponentName name) { //
			logService = null;
			debugToast("LogConnection.onServiceDisconnected");
		}
	}

	private void msgImpl(String topic, String result) {
		debugToast("topic: " + topic + "\nvalue: " + result);
	}

	private void msgAlert(String topic, String result) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(topic);
		builder.setMessage(result);
		builder.setPositiveButton("OK", null);
		builder.show();
	}

	private final IMqttCb.Stub cb = new IMqttCb.Stub() {

		@Override
		public void msg(final String topic, final String result)
				throws RemoteException {
			runOnUiThread(new Runnable() {
				public void run() {
					msgImpl(topic, result);
				}
			});
		}

	};

	public void debugToast(String what) {
		Log.d(TAG, what);
		Toast toast = Toast.makeText(this, what, Toast.LENGTH_SHORT);
		toast.show();
	}

	@Override
	public void onClick(View v) {
		Log.d(TAG, "onClick: " + v.toString());
		testSvc();
	}
}
