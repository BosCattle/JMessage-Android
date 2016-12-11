package tech.jiangtao.support.webrtc.init;

import android.content.Context;
import android.util.Log;
import org.webrtc.PeerConnectionFactory;

/**
 * Class: SupportWebRTC </br>
 * Description: 必须调用以初始化操作 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 07/12/2016 1:49 AM</br>
 * Update: 07/12/2016 1:49 AM </br>
 **/

public class SupportWebRTC {

  private static final String TAG = SupportWebRTC.class.getSimpleName();
  public static void initialize(Context context){
    if (PeerConnectionFactory.initializeAndroidGlobals(context,true,true,true,null)){
      Log.d(TAG, "initialize: success");
    }else {
      try {
        throw new Exception("初始化WebRTC错误");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
