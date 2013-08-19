
package cz.xlinux.libAPI.libFce;

import aidl.core.API.EntryPoint;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class APIConnection implements ServiceConnection {

    private static final String LOG_TAG = "AIDL";

    private EntryPoint apiService;

    private CBOnSvcChange cb;

    public APIConnection(CBOnSvcChange cb) {
        super();
        this.cb = cb;
    }

    public APIConnection() {
        super();
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        apiService = EntryPoint.Stub.asInterface(service);
        Log.d(LOG_TAG, "APIConnection.onServiceConnected,name=" + name);
        if (cb != null)
            cb.setService(apiService);
    }

    public void onServiceDisconnected(ComponentName name) {
        apiService = null;
        Log.d(LOG_TAG, "APIConnection.onServiceDisconnected,name=" + name);
        if (cb != null)
            cb.setService(null);
    }
}
