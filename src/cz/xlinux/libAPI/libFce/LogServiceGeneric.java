package cz.xlinux.libAPI.libFce;

import android.os.RemoteException;
import android.util.Log;
import cz.xlinux.libAPI.aidl.ILogService;
import cz.xlinux.libAPI.aidl.IMqttCb;
import cz.xlinux.libAPI.aidl.Message;

public class LogServiceGeneric extends ILogService.Stub {

	private static final String TAG = "LogServiceGeneric";
	private int version;
	private IMqttCb savedCb;

	LogServiceGeneric(int version) {
		this.version = version;
	}

	@Override
	public void log_d(String tag, String msg) throws RemoteException {
		Log.d(TAG, "tag: " + tag + " / msg: " + msg + " version: " + version);
	}

	@Override
	public void log(Message msg) throws RemoteException {
		Log.d(TAG, "tag: " + msg.getTag() + " / msg:" + msg.getText());
	}

	@Override
	public void pub(String txt) throws RemoteException {
		Log.d(TAG, "pub: " + txt);
	}

	@Override
	public String syncSendRecv(String topic, String txt) throws RemoteException {
		Log.d(TAG, "syncSendRecv");
		return "nothing";
	}

	@Override
	public void regCallBack(IMqttCb cb) throws RemoteException {
		Log.d(TAG, "regCallBack");
		savedCb = cb;
	}

}
