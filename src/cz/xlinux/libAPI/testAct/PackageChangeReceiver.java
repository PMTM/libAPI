package cz.xlinux.libAPI.testAct;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import cz.xlinux.libAPI.libFce.LibAPI;

public class PackageChangeReceiver extends BroadcastReceiver {
	private static final String TAG = "BroadcastReceiver";

	@Override
	public void onReceive(Context ctx, Intent intent) {
		// Uri data = intent.getData();
		String data = intent.getData().toString();
		final PackageManager pm = ctx.getPackageManager();
		String resp = "";
		resp += "Action: " + intent.getAction();
		resp += "\n";
		resp += "data: " + data;
		resp += LibAPI.testCertPkg(pm, data.substring(8));
		TestActivity.mThis.debugToast(resp);
		// Log.d(TAG, "Action: " + intent.getAction());
		// Log.d(TAG, "The DATA: " + data);
	}
}