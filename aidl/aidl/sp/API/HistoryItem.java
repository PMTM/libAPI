package aidl.sp.API;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class HistoryItem extends ProofItem {
	
	public static final Parcelable.Creator<HistoryItem> CREATOR = new Parcelable.Creator<HistoryItem>() {
		public HistoryItem createFromParcel(Parcel in) {
			Log.v("HistoryItem", "Creating from parcel");
			return new HistoryItem(in);
		}

		public HistoryItem[] newArray(int size) {
			return new HistoryItem[size];
		}
	};

	public HistoryItem(Parcel in) {
		super(in);
	}

	public HistoryItem(String tag, String msg) {
		super(tag, msg);
	}
}