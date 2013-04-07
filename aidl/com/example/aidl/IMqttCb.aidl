package com.example.aidl;

interface IMqttCb {
    void msg(in String topic,in String result);
}
