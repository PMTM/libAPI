package aidl.sp.API;

import android.os.Parcel;
import android.os.Parcelable;

public class ValueItem implements Parcelable {
	private String tag;
	private String text;

	public ValueItem(String tag, String text) {
		this.tag = tag;
		this.text = text;
	}

	public ValueItem(Parcel in) { //
		tag = in.readString();
		text = in.readString();
	}

	public void writeToParcel(Parcel out, int flags) { //
		out.writeString(tag);
		out.writeString(text);
	}

	public int describeContents() { //
		return 0;
	}

	public static final Parcelable.Creator<ValueItem> CREATOR = new Parcelable.Creator<ValueItem>() { //

		public ValueItem createFromParcel(Parcel source) {
			return new ValueItem(source);
		}

		public ValueItem[] newArray(int size) {
			return new ValueItem[size];
		}

	};

	// Setters and Getters
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}