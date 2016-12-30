package tech.jiangtao.support.ui.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import tech.jiangtao.support.kit.callback.VCardCallback;
import tech.jiangtao.support.kit.eventbus.RecieveMessage;
import tech.jiangtao.support.kit.init.SupportIM;
import tech.jiangtao.support.kit.userdata.SimpleVCard;
import tech.jiangtao.support.ui.R;
import tech.jiangtao.support.ui.fragment.ChatFragment;

/**
 * Created by jiang on 2016/12/29.
 */

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
