package cz.xlinux.libAPI.testAct;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import cz.xlinux.libAPI.aidl.ILogService;
import cz.xlinux.libAPI.aidl.IMqttCb;
import cz.xlinux.libAPI.testAct.R;

import cz.xlinux.libAPI.aidl.Message;
import cz.xlinux.libAPI.libFce.LibAPI;

public class TestActivity extends Activity implements OnClickListener {
	private static final String TAG = "Example";

	ILogService logService;
	LogConnection conn;

	private TextView mTvLog;

	public static TestActivity mThis;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mThis = this;
		// Prepare items on screen
		mTvLog = (TextView) findViewById(R.id.tvLog);
		// mTvLog.setMovementMethod(ScrollingMovementMethod.getInstance());

		Button clr;
		clr = (Button) findViewById(R.id.btTestCert);
		clr.setOnClickListener(this);
		clr = (Button) findViewById(R.id.btTestSvc);
		clr.setOnClickListener(this);
		clr = (Button) findViewById(R.id.btTestScan);
		clr.setOnClickListener(this);

		// Request bind to the service
		conn = new LogConnection();
		Intent intent = new Intent("cz.xlinux.libAPI.svc.Logger");
		intent.putExtra("version", "1.0");
		boolean bindSvc = bindService(intent, conn, Context.BIND_AUTO_CREATE);
		debugToast("bindService = " + bindSvc);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// unbind the service and null it out
		if (conn != null) {
			unbindService(conn);
			conn = null;
		}
	}

	public void testSvc() {
		try {
			debugToast("testSVC");
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
		} catch (RemoteException e) {
			debugToast("onClick failed: " + e);
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
		// Send to log
		Log.d(TAG, what);
		if (mTvLog != null) {
			String tmpTxt = mTvLog.getText() + "\n---\n" + what;
			mTvLog.setText(tmpTxt);
		}
		// send to screen popup
		// Toast toast = Toast.makeText(this, what, Toast.LENGTH_SHORT);
		// toast.show();
	}

	@Override
	public void onClick(View v) {
		debugToast("onClick: " + ((Button) v).getText());
		switch (v.getId()) {
		case R.id.btTestSvc:
			testSvc();
			break;
		case R.id.btTestCert:
			String packageName = this.getPackageName();
			LibAPI.testCert(this, packageName, this);
			break;
		case R.id.btTestScan:
			testScan();
			break;
		}
	}

	private void testScan() {
		final PackageManager pm = getPackageManager();
		// get a list of installed apps.
		List<ApplicationInfo> packages = pm
				.getInstalledApplications(PackageManager.GET_META_DATA);

		String resp;
		for (ApplicationInfo packageInfo : packages) {
			resp = "";
			resp += "Installed package :";
			resp += packageInfo.packageName;
			resp += "\n";
			resp += "Launch Activity :";
			resp += pm.getLaunchIntentForPackage(packageInfo.packageName);
			resp += LibAPI.testCertPkg(pm, packageInfo.packageName);
			debugToast(resp);
		}
	}

	private ArrayList<PInfo> getPackages() {
		ArrayList<PInfo> apps = getInstalledApps(false); /*
														 * false = no system
														 * packages
														 */
		final int max = apps.size();
		for (int i = 0; i < max; i++) {
			apps.get(i).prettyPrint();
		}
		return apps;
	}

	class PInfo {
		private String appname = "";
		private String pname = "";
		private String versionName = "";
		private int versionCode = 0;
		private Drawable icon;

		private void prettyPrint() {
			Log.v(TAG,appname + "\t" + pname + "\t" + versionName + "\t"
					+ versionCode);
		}
	}

	private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
		ArrayList<PInfo> res = new ArrayList<PInfo>();
		List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
		for (int i = 0; i < packs.size(); i++) {
			PackageInfo p = packs.get(i);
			if ((!getSysPackages) && (p.versionName == null)) {
				continue;
			}
			PInfo newInfo = new PInfo();
			newInfo.appname = p.applicationInfo.loadLabel(getPackageManager())
					.toString();
			newInfo.pname = p.packageName;
			newInfo.versionName = p.versionName;
			newInfo.versionCode = p.versionCode;
			newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());
			res.add(newInfo);
		}
		return res;
	}
}
