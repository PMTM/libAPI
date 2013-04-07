package cz.xlinux.libAPI.aidl;

import cz.xlinux.libAPI.aidl.Message;
import cz.xlinux.libAPI.aidl.IMqttCb;

interface ILogService {
  void log_d(String tag, String message); 
  void log(in Message message);
  
  void pub(in String txt);
  String syncSendRecv(in String topic, in String txt);
   
  void regCallBack(IMqttCb cb);
}
