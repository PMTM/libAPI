package com.example.aidl;

import com.example.aidl.Message;
import com.example.aidl.IMqttCb;

interface ILogService {
  void log_d(String tag, String message); 
  void log(in Message message);
  
  void pub(in String txt);
  String syncSendRecv(in String topic, in String txt);
   
  void regCallBack(IMqttCb cb);
}
