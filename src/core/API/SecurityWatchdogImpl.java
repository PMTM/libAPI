package core.API;

import android.os.RemoteException;
import android.util.Log;
import aidl.core.API.SecurityWatchdog;

public class SecurityWatchdogImpl extends SecurityWatchdog.Stub {

	private static final String LOG_TAG = "SecurityWatchdogImpl";
	private EntryPoint serviceLink;

	public SecurityWatchdogImpl(EntryPoint serviceLink) {
		this.serviceLink = serviceLink;
	}

	@Override
	public void expireTimerNow() throws RemoteException {
		Log.d(LOG_TAG, "expireTimerNow()");
		if (serviceLink != null) {
		} else {
			Log.e(LOG_TAG, "no serviceLink");
		}
		// throw new RemoteException("Something");
	}

	@Override
	public void renewTimer() throws RemoteException {
		Log.d(LOG_TAG, "renewTimer()");
	}

}
