package core.API;

import aidl.core.API.OnTicketChange;
import aidl.sp.API.Ticket;
import android.os.RemoteException;
import android.util.Log;

public class OnTicketChangeImpl extends OnTicketChange.Stub {

	private static final String LOG_TAG = "OnTicketChangeImpl";
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
	public void addTicket(String dummy, Ticket item) throws RemoteException {
		Log.d(LOG_TAG, "addTicket: " + item);
		if (serviceLink != null) {
			String str = new String(item.toString());
			Log.e(LOG_TAG, "create comment " + str);
			serviceLink.createCommentUp("[+]" + str);
		} else {
			Log.e(LOG_TAG,"cannot create db record");
		}
		if (ticketCB != null) {
			ticketCB.addTicket(dummy,item);
		} else {
			Log.e(LOG_TAG, "ticketCB not set");
			refreshCB();
		}
	}

	@Override
	public void removeTicket(Ticket item) throws RemoteException {
		Log.d(LOG_TAG, "removeTicket: " + item);
		if (serviceLink != null) {
			String str = new String(item.toString());
			Log.e(LOG_TAG, "create comment " + str);
			serviceLink.createCommentUp("[-]" + str);
		} else {
			Log.e(LOG_TAG,"cannot create db record");
		}
		if (ticketCB != null) {
			ticketCB.removeTicket(item);
		} else {
			Log.e(LOG_TAG, "ticketCB not set");
			refreshCB();
		}
	}

}
