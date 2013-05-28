package aidl.sp.API;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Coupon extends ValueItem {

	public static final Parcelable.Creator<Coupon> CREATOR = new Parcelable.Creator<Coupon>() {
		public Coupon createFromParcel(Parcel in) {
			Log.v("Ticket", "Creating from parcel");
			return new Coupon(in);
		}

		public Coupon[] newArray(int size) {
			return new Coupon[size];
		}
	};

	public Coupon(Parcel in) {
		super(in);
	}

	public Coupon(String tag, String msg) {
		super(tag, msg);
	}
}