
package aidl.core.API;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class AbstractBestInterface implements AbstractInterface, Serializable, Parcelable {

    /**
     * A UID generated by Eclipse for serialization purposes.
     */
    private static final long serialVersionUID = 855981730848566269L;

    /**
     * Flags for special marshaling
     */
    public int describeContents() {
        return 0;
    }

    /**
     * Write the concrete strategy to the Parcel.
     */
    public void writeToParcel(Parcel out, int flags) {
        // Serialize "this", so that we can get it back after IPC
        out.writeSerializable(this);
    }

    /**
     * The creator that MUST be defined and named "CREATOR" so that the service generated from AIDL
     * can recreate AbstractBestInterface after IPC.
     */
    public static final Creator<AbstractBestInterface> CREATOR = new Parcelable.Creator<AbstractBestInterface>() {

        /**
         * Read the serialized concrete strategy from the parcel.
         * 
         * @param in The parcel to read from
         * @return An AbstractStrategy
         */
        public AbstractBestInterface createFromParcel(Parcel in) {
            // Read serialized concrete strategy from parcel
            return (AbstractBestInterface) in.readSerializable();
        }

        /**
         * Required by Creator
         */
        public AbstractBestInterface[] newArray(int size) {
            return new AbstractBestInterface[size];
        }
    };
}
