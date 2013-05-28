package aidl.sp.API;

import android.os.Parcel;
import android.os.Parcelable;

public class ProofItem implements Parcelable {
	private String tag;
	private String text;

	public ProofItem(String tag, String text) {
		this.tag = tag;
		this.text = text;
	}

	public ProofItem(Parcel in) { //
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

	public static final Parcelable.Creator<ProofItem> CREATOR = new Parcelable.Creator<ProofItem>() { //

		public ProofItem createFromParcel(Parcel source) {
			return new ProofItem(source);
		}

		public ProofItem[] newArray(int size) {
			return new ProofItem[size];
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

	public String toString() {
		return "ProofItem:{ tag: " + tag + " text: " + text + " }";
	}

}