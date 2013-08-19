package cz.xlinux.libAPI.libFce;

import aidl.self.API.Interconnect;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class APIConnectionLocal implements ServiceConnection {

	private static final String LOG_TAG = "AIDL";
    private Interconnect apiService;
	private CBOnSvcChangeLocal cb;

	public APIConnectionLocal(CBOnSvcChangeLocal cb) {
		super();
		this.cb = cb;
	}

	public APIConnectionLocal() {
		super();
	}

	public void onServiceConnected(ComponentName name, IBinder service) {
		apiService = Interconnect.Stub.asInterface(service);
        Log.d(LOG_TAG, "APIConnectionLocal.onServiceConnected,name=" + name);
		if (cb != null)
			cb.setService(apiService);
	}

	public void onServiceDisconnected(ComponentName name) { //
		apiService = null;
        Log.d(LOG_TAG, "APIConnectionLocal.onServiceDisconnected,name=" + name);
		if (cb != null)
			cb.setService(null);
	}
}