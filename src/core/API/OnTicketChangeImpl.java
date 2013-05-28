package core.API;

import aidl.core.API.OnTicketChange;
import aidl.sp.API.Ticket;
import android.os.RemoteException;
import android.util.Log;

public class OnTicketChangeImpl extends OnTicketChange.Stub {

	private static final String LOG_TAG = "OnCouponChangeImpl";

	@Override
	public void addTicket(Ticket item) throws RemoteException {
		Log.d(LOG_TAG, "pushTicket: " + item);
	}

	@Override
	public void removeTicket(Ticket item) throws RemoteException {
		Log.d(LOG_TAG, "pushTicket: " + item);
	}
}
