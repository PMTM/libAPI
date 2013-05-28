package core.API;

import aidl.core.API.OnTicketChange;
import aidl.sp.API.Ticket;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.util.Log;

public class OnTicketChangeImpl extends OnTicketChange.Stub {

	private static final String LOG_TAG = "OnCouponChangeImpl";
	private Context ctx;

	public OnTicketChangeImpl(Context ctx) {
		this.ctx = ctx;
	}

	public void setContext(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public void addTicket(Ticket item) throws RemoteException {
		Log.d(LOG_TAG, "addTicket: " + item);
		if (ctx != null) {
			Intent intent = new Intent("core.API.SECOND");
			intent.putExtra("type", "ticket");
			intent.putExtra("op", "addTicket");
			intent.putExtra("ticket", item);
			ctx.sendOrderedBroadcast(intent, null);
		}
	}

	@Override
	public void removeTicket(Ticket item) throws RemoteException {
		Log.d(LOG_TAG, "removeTicket: " + item);
		if (ctx != null) {
			Intent intent = new Intent("core.API.MISC");
			intent.putExtra("type", "ticket");
			intent.putExtra("op", "addTicket");
			intent.putExtra("ticket", item);
			ctx.sendOrderedBroadcast(intent, null);
		}
	}
}
