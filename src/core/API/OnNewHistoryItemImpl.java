package core.API;

import aidl.core.API.OnNewHistoryItem;
import aidl.sp.API.HistoryItem;
import android.os.RemoteException;
import android.util.Log;

public class OnNewHistoryItemImpl extends OnNewHistoryItem.Stub {

	private static final String LOG_TAG = "OnNewHistoryItemImpl";

	@Override
	public void addHistoryItem(HistoryItem item) throws RemoteException {
		Log.d(LOG_TAG,"addHistoryItem: "+item);
	}

}
