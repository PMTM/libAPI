package cz.xlinux.libAPI.aidl;

interface IMqttCb {
    void msg(in String topic,in String result);
}
