package aidl.core.API;

import aidl.sp.API.HistoryItem;

interface OnNewHistoryItem {

	void addHistoryItem(in HistoryItem item);
}
