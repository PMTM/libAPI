package cz.xlinux.libAPI.spServices;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import cz.xlinux.libAPI.aidl.ILogService;
import cz.xlinux.libAPI.aidl.IMqttCb;
import cz.xlinux.libAPI.testAct.R;

import cz.xlinux.libAPI.aidl.Message;
import cz.xlinux.libAPI.testAct.TestActivity;

public class spService extends Service {

	private static final int MY_NOTIF_ID = 0;
	private static final int PI_REQ_CODE = 1;
	protected static final String TAG = "spService";

	@Override
	public IBinder onBind(Intent intent) {
		final String version = intent.getExtras().getString("version");

		Log.d(TAG, "onBind: version requested: " + version);

		return new ILogService.Stub() {

			IMqttCb savedCb;

			public void log_d(String tag, String message)
					throws RemoteException {
				Log.d(TAG, "tag: " + tag + " / msg: " + message + " version: "
						+ version);
			}

			public void log(Message msg) throws RemoteException {
				Log.d(TAG, "tag: " + msg.getTag() + " / msg:" + msg.getText());
			}

			public void regCallBack(IMqttCb cb) {
				Log.d(TAG, "regCallBack");
				savedCb = cb;
			}

			@Override
			public void pub(String txt) throws RemoteException {
				Log.d(TAG, "pub: " + txt);
				Runnable busyLoop = new Runnable() {
					public void run() {
						int count = 1;
						while (count < 5) {
							count++;
							try {
								Thread.sleep(1000);
							} catch (Exception ex) {
								;
							}
							sendNotification("progress + " + count,
									"busyLoop - " + count);
						}
						sendNotification("done + " + count, "end - " + count);
						if (savedCb != null) {
							try {
								savedCb.msg("done work", "OK!");
							} catch (RemoteException e) {
								e.printStackTrace();
							}
						}
					}
				};
				performOnBackgroundThread(busyLoop);
			}

			@Override
			public String syncSendRecv(String topic, String txt)
					throws RemoteException {
				Log.d(TAG, "syncSendRecv");

				return "nothing";
			}
		};
	}

	public static Thread performOnBackgroundThread(final Runnable runnable) {
		final Thread t = new Thread() {
			@Override
			public void run() {
				try {
					runnable.run();
				} finally {

				}
			}
		};
		t.start();
		return t;
	}

	public void sendNotification(String ticker, String txt) {
		Context ctx = this;
		Intent notificationIntent = new Intent(ctx, TestActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(ctx,
				PI_REQ_CODE, notificationIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		NotificationManager nm = (NotificationManager) ctx
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Resources res = ctx.getResources();
		Notification.Builder builder = new Notification.Builder(ctx);

		// res.getString(R.string.myTicker)
		Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.ic_drum_large);
		builder.setContentIntent(contentIntent)
				.setSmallIcon(R.drawable.ic_machine).setLargeIcon(bmp)
				.setTicker(ticker).setWhen(System.currentTimeMillis())
				.setAutoCancel(true)
				.setContentTitle(res.getString(R.string.myNotifTitle))
				.setContentText(txt);
		Notification n = builder.build();

		nm.notify(MY_NOTIF_ID, n);
	}
}