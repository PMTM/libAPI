package aidl.self.API;

import android.os.Messenger;
import aidl.core.API.EntryPoint;

interface Interconnect {

	void registerMessenger(in android.os.Messenger messenger);
	void registerCallBack(in EntryPoint uiService);
}
