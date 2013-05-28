package self.API;

import core.API.EntryPoint;
import aidl.self.API.Interconnect;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class InterconnectImpl extends Interconnect.Stub {

	private static final String LOG_TAG = "InterconnectImpl";
	private static EntryPoint parentService;

	public InterconnectImpl(EntryPoint sharedService) {
		InterconnectImpl.parentService = sharedService;
	}

	@Override
	public void registerMessenger(Messenger messenger) throws RemoteException {
		Log.d(LOG_TAG, "registerMessenger: messenger = " + messenger);
		if (parentService != null) {
			parentService.setMessenger(messenger);
			Log.d(LOG_TAG, "call setMessenger()");
		} else {
			Log.e(LOG_TAG, "no parentService Available");
		}
	}

}
