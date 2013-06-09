package core.API;

import cz.xlinux.db.MyContentProvider;
import cz.xlinux.db.TableItems;
import self.API.InterconnectImpl;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

public class EntryPoint extends Service {

    protected static final String LOG_TAG = "EntryPoint";
    private Messenger messenger;
    private aidl.core.API.EntryPoint uiService;

    // private CommentsDataSource datasource;

    @Override
    public void onCreate() {
        // datasource = new CommentsDataSource(this);
        // datasource.open();
    }

    @Override
    public IBinder onBind(Intent intent) {
        final String version = intent.getExtras().getString("version");

        String action = intent.getAction();
        Log.d(LOG_TAG, "onBind: ver = " + version + ", act = " + action);

        if (action.equals("core.API.BindLocal")) {
            return new InterconnectImpl(this);
        } else {
            return new EntryPointImpl(this);
        }
    }

    public void setMessenger(Messenger messenger) {
        Log.d(LOG_TAG, "setting messenger = " + messenger);
        this.messenger = messenger;
    }

    public void sendMessage(String txt) {

        if (messenger != null) {
            Message msg = Message.obtain();

            // msg.obj = new Coupon("ahoj",txt);
            // msg.obj = txt;
            msg.arg1 = 7;

            try {
                messenger.send(msg);
            } catch (android.os.RemoteException e1) {
                Log.w(getClass().getName(), "Exception sending message", e1);
            }
        } else {
            Log.d(LOG_TAG, "no messenger set");
        }
    }

    public aidl.core.API.EntryPoint getEntryPoint() {
        return uiService;
    }

    public void setUIService(aidl.core.API.EntryPoint uiService) {
        Log.d(LOG_TAG, "setUIService uiService = " + uiService);
        this.uiService = uiService;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // datasource.close();
        // All clients have unbound with unbindService()
        Toast.makeText(this, "Service Unbinding", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onUnbind()");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        // datasource.open();
        // A client is binding to the service with bindService(),
        // after onUnbind() has already been called
        Log.d(LOG_TAG, "onRebind(intent=" + intent + ")");
    }

    @Override
    public void onDestroy() {
        // datasource.close();
        Toast.makeText(this, "Service Done", Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, "onDestroy()");
    }

    public void createCommentUp(String str) {
        ContentValues values = new ContentValues();
        values.put(TableItems.COLUMN_NAME, str);

        @SuppressWarnings("unused")
        Uri todoUri = getContentResolver().insert(
                MyContentProvider.CONTENT_URI, values);
    }
}