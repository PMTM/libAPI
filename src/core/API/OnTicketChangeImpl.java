package core.API;

import aidl.core.API.OnTicketChange;
import aidl.sp.API.Ticket;
import android.os.RemoteException;
import android.util.Log;

public class OnTicketChangeImpl extends OnTicketChange.Stub {

	private static final String LOG_TAG = "OnCouponChangeImpl";
	private EntryPoint serviceLink;
	private OnTicketChange ticketCB;

	public OnTicketChangeImpl(EntryPoint serviceLink) {
		this.serviceLink = serviceLink;
		refreshCB();
	}

	private void refreshCB() {
		if (serviceLink != null) {
			aidl.core.API.EntryPoint entry = serviceLink.getEntryPoint();
			if (entry != null) {
				try {
					ticketCB = entry.getTicketCB();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Log.e(LOG_TAG, "has to EntryPoint instance");
			}
		} else {
			Log.e(LOG_TAG, "has no service link");
		}
	}

	@Override
	public void addTicket(Ticket item) throws RemoteException {
		Log.d(LOG_TAG, "addTicket: " + item);
		if (ticketCB != null) {
			ticketCB.addTicket(item);
		} else {
			Log.e(LOG_TAG,"ticketCB not set");
			refreshCB();
		}
		// if (ctx != null) {
		// Intent intent = new Intent("core.API.SECOND");
		// intent.putExtra("type", "ticket");
		// intent.putExtra("op", "addTicket");
		// intent.putExtra("ticket", item);
		// ctx.sendOrderedBroadcast(intent, null);
		// }
	}

	@Override
	public void removeTicket(Ticket item) throws RemoteException {
		Log.d(LOG_TAG, "removeTicket: " + item);
		if (ticketCB != null) {
			ticketCB.removeTicket(item);
		} else {
			Log.e(LOG_TAG,"ticketCB not set");
			refreshCB();
		}
		// if (ctx != null) {
		// Intent intent = new Intent("core.API.MISC");
		// intent.putExtra("type", "ticket");
		// intent.putExtra("op", "addTicket");
		// intent.putExtra("ticket", item);
		// ctx.sendOrderedBroadcast(intent, null);
		// }
	}
}
