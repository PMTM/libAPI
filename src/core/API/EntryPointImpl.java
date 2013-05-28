package core.API;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import aidl.core.API.EntryPoint;
import aidl.core.API.OnCouponChange;
import aidl.core.API.OnNewHistoryItem;
import aidl.core.API.OnNewReceipt;
import aidl.core.API.OnTicketChange;
import aidl.core.API.SecurityWatchdog;

public class EntryPointImpl extends EntryPoint.Stub {

	private static final String LOG_TAG = "EntryPointImpl";
	private static SecurityWatchdog wd;
	private static OnCouponChange couponCB;
	private static OnNewHistoryItem histItemCB;
	private static OnTicketChange ticketCB;
	private static OnNewReceipt receiptCB;
	private Context ctx ;
	
	public EntryPointImpl(Context ctx) {
		this.ctx =ctx;
	}

	@Override
	public SecurityWatchdog getSecurityWatchdog() throws RemoteException {
		Log.d(LOG_TAG, "getSecurityWatchDog called");
		if (wd == null)
			wd = new SecurityWatchdogImpl();
		return wd;
	}

	@Override
	public OnCouponChange getCouponCB() throws RemoteException {
		Log.d(LOG_TAG, "getCouponCB called");
		if (couponCB == null)
			couponCB = new OnCouponChangeImpl();
		return couponCB;
	}

	@Override
	public OnNewHistoryItem getHistoryCB() throws RemoteException {
		Log.d(LOG_TAG, "getHistoryCB called");
		if (histItemCB == null)
			histItemCB = new OnNewHistoryItemImpl();
		return histItemCB;
	}

	@Override
	public OnTicketChange getTicketCB() throws RemoteException {
		Log.d(LOG_TAG, "getTicketCB called");
		if (ticketCB == null)
			ticketCB = new OnTicketChangeImpl(ctx);
		return ticketCB;
	}

	@Override
	public OnNewReceipt getReceiptCB() throws RemoteException {
		Log.d(LOG_TAG, "getReceiptCB called");
		if (receiptCB == null)
			receiptCB = new OnNewReceiptImpl();
		return receiptCB;
	}
}
