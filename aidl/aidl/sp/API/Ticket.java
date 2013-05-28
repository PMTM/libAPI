package aidl.sp.API;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Ticket extends ValueItem {
	
	public static final Parcelable.Creator<Ticket> CREATOR = new Parcelable.Creator<Ticket>() {
		public Ticket createFromParcel(Parcel in) {
			Log.v("Ticket", "Creating from parcel");
			return new Ticket(in);
		}

		public Ticket[] newArray(int size) {
			return new Ticket[size];
		}
	};

	
	public Ticket(Parcel in) {
		super(in);
	}

	public Ticket(String tag, String msg) {
		super(tag,msg);
	}
}