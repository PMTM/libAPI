package aidl.core.API;

interface SecurityWatchdog {

	void renewTimer();
	void expireTimerNow();
}
