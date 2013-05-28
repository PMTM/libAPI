package aidl.sp.API;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Receipt extends ProofItem {

	public static final Parcelable.Creator<Receipt> CREATOR = new Parcelable.Creator<Receipt>() {
		public Receipt createFromParcel(Parcel in) {
			Log.v("Receipt", "Creating from parcel");
			return new Receipt(in);
		}

		public Receipt[] newArray(int size) {
			return new Receipt[size];
		}
	};

	public Receipt(Parcel in) {
		super(in);
	}

	public Receipt(String tag, String msg) {
		super(tag, msg);
	}
}