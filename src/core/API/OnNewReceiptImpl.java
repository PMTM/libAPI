package core.API;

import aidl.core.API.OnNewReceipt;
import aidl.sp.API.Receipt;
import android.os.RemoteException;
import android.util.Log;

public class OnNewReceiptImpl extends OnNewReceipt.Stub {

	private static final String LOG_TAG = "OnNewReceiptImpl";

	@Override
	public void addReceipt(Receipt item) throws RemoteException {
		Log.d(LOG_TAG,"addHistoryItem: "+item);
	}

}
