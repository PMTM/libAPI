package cz.xlinux.libAPI.aidl;

interface SecurityWatchdog {

	void renewTimer();
	void expireTimerNow();
}
