package sp.API;

import android.os.RemoteException;
import android.util.Log;
import aidl.core.API.OnNewHistoryItem;
import aidl.sp.API.History;
import aidl.sp.API.HistoryItem;

public class HistoryImpl extends History.Stub {

	private static final String LOG_TAG = "HistoryImpl";

	@Override
	public int getNumberOfItems() throws RemoteException {
		Log.d(LOG_TAG, "getNumberOfItems()");
		return 0;
	}

	@Override
	public HistoryItem getItemNo(int no) throws RemoteException {
		Log.d(LOG_TAG, "getItemNo(no=" + no + ")");
		return null;
	}

	@Override
	public void subscribeToHistoryUpdates(OnNewHistoryItem cb)
			throws RemoteException {
		Log.d(LOG_TAG, "subscribeToHistoryUpdates(cb=" + cb + ")");
	}

}
