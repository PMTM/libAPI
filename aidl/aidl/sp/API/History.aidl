package aidl.sp.API;

import aidl.sp.API.HistoryItem;
import aidl.core.API.OnNewHistoryItem;

interface History {

	int getNumberOfItems();
	HistoryItem getItemNo(in int no);

	void subscribeToHistoryUpdates(in OnNewHistoryItem cb);
}
