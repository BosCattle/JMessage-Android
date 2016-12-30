package tech.jiangtao.support.ui.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.fragment.ChatFragment;

/**
 * Class: XMPPService </br>
 * Description: 进行数据库操作和通知的服务 </br>
 * 简单的进行双进程守护
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 31/12/2016 1:54 AM</br>
 * Update: 31/12/2016 1:54 AM </br>
 **/

public class XMPPService  extends Service {

    public static final String TAG = XMPPService.class.getSimpleName();

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 显示通知
     */
    public void showNotification(String name, String info) {
        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent(this, ChatFragment.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setContentIntent(
                    PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT))
                    .setContentTitle(name)
                    .setContentText(info)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE);
            Notification notification = builder.build();
            notification.flags = Notification.FLAG_ONGOING_EVENT;
            notification.defaults = Notification.DEFAULT_SOUND;
            startForeground(110, notification);
        }
    }



    @Override public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override public IBinder onBind(Intent intent) {
        return null;
    }
}
