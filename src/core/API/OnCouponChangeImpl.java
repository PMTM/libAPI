package core.API;

import aidl.core.API.OnCouponChange;
import aidl.sp.API.Coupon;
import android.os.RemoteException;
import android.util.Log;

public class OnCouponChangeImpl extends OnCouponChange.Stub {

	private static final String LOG_TAG = "OnCouponChangeImpl";

	@Override
	public void addCoupon(Coupon item) throws RemoteException {
		Log.d(LOG_TAG, "addCoupon: " + item);
	}

	@Override
	public void removeCoupon(Coupon item) throws RemoteException {
		Log.d(LOG_TAG, "removeCoupon: " + item);
	}
}
