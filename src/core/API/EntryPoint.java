package core.API;

import self.API.InterconnectImpl;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class EntryPoint extends Service {

	protected static final String LOG_TAG = "EntryPoint";
	private Messenger messenger;
	private aidl.core.API.EntryPoint uiService;

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
}