package aidl.core.API;

import aidl.sp.API.Receipt;

interface OnNewReceipt {

	void addReceipt(in Receipt item);
}