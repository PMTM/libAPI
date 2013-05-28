package aidl.core.API;

import aidl.core.API.SecurityWatchdog;
import aidl.core.API.OnCouponChange;
import aidl.core.API.OnNewHistoryItem;
import aidl.core.API.OnTicketChange;
import aidl.core.API.OnNewReceipt;

interface EntryPoint {

	SecurityWatchdog getSecurityWatchdog ();
	OnCouponChange getCouponCB();
	OnNewHistoryItem getHistoryCB();
	OnTicketChange getTicketCB();
	OnNewReceipt getReceiptCB();
}
