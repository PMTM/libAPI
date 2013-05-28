package cz.xlinux.libAPI.libFce;

import aidl.self.API.Interconnect;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class APIConnectionLocal implements ServiceConnection {

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
		if (cb != null)
			cb.setService(apiService);
	}

	public void onServiceDisconnected(ComponentName name) { //
		apiService = null;
		if (cb != null)
			cb.setService(null);
	}
}