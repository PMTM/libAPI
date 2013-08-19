package aidl.core.API;

import aidl.core.API.SecurityWatchdog;
import aidl.core.API.OnCouponChange;
import aidl.core.API.OnNewHistoryItem;
import aidl.core.API.OnTicketChange;
import aidl.core.API.OnNewReceipt;
//-----------------------
import aidl.core.API.AbstractFunInterface;
import aidl.core.API.AbstractJoyInterface;
import aidl.core.API.AbstractBestInterface;

interface EntryPoint {

	SecurityWatchdog getSecurityWatchdog ();
	OnCouponChange getCouponCB();
	OnNewHistoryItem getHistoryCB();
	OnTicketChange getTicketCB();
	OnNewReceipt getReceiptCB();
	// --------------------
    AbstractFunInterface getFunAPI(in String verId);
    AbstractJoyInterface getJoyAPI(in String verId);
    AbstractBestInterface getBestAPI(in String verId);
}
