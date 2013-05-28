package cz.xlinux.libAPI.libFce;

import cz.xlinux.libAPI.aidl.EntryPoint;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class APIConnection implements ServiceConnection {

	private EntryPoint apiService;
	private CBOnSvcChange cb;

	public APIConnection(CBOnSvcChange cb) {
		super();
		this.cb = cb;
	}

	public APIConnection() {
		super();
	}

	public void onServiceConnected(ComponentName name, IBinder service) {
		apiService = EntryPoint.Stub.asInterface(service);
		if (cb != null)
			cb.setService(apiService);
	}

	public void onServiceDisconnected(ComponentName name) { //
		apiService = null;
		if (cb != null)
			cb.setService(null);
	}
}