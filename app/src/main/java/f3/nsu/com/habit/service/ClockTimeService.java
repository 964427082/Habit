package f3.nsu.com.habit.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import f3.nsu.com.habit.R;
import f3.nsu.com.habit.activity.MainActivity;
import f3.nsu.com.habit.broadcast.ClockReceiver;
import f3.nsu.com.habit.tool.StartOrStopService;
import f3.nsu.com.habit.ui.CountClockTime;

/**
 * Created by 爸爸你好 on 2017/8/8.
 */

public class ClockTimeService extends Service {
    private static final String TAG = "ClockTimeService";
    private final Context context = this;
    private String name = null;
    private String clockTime = null;
    private boolean isClockTime = false;
    private int serviceNumber = 0;

    int anMin = 60 * 1000;          //这是一分钟的毫秒数
    int anS = 1 * 1000;             //这是一秒钟的毫秒数

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        clockTime = bundle.getString("clockTime");
        isClockTime = bundle.getBoolean("isClockTime");
        serviceNumber = bundle.getInt("number");
        boolean isBroadcastReceiver = bundle.getBoolean("broadcastReceiver");
        //对时间的处理操作
        //isBroadcastReceiver 该标志用于判断启动该服务是从广播来的
        if (isBroadcastReceiver) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //这里去执行逻辑操作
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    Intent toIntent = new Intent(context, MainActivity.class);
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, toIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                    mBuilder.setContentTitle("Habit")
                            .setContentText("老铁，‘" + name + "’还没完成！")
//                        .setTicker("什么标题？")
                            .setSmallIcon(R.mipmap.logo)
                            .setContentIntent(pendingIntent);
                    //针对sdk小于17的操作
                    initForeService();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        //悬挂式 NOtification,5.0后显示
                        mBuilder.setFullScreenIntent(pendingIntent, true);
                        mBuilder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
                    }
                    mBuilder.setWhen(System.currentTimeMillis())    //显示通知的时间
                            .setPriority(NotificationCompat.PRIORITY_MAX)   //  通知优先级
                            .setAutoCancel(true)
                            .setDefaults(NotificationCompat.DEFAULT_SOUND)      //默认通知铃声
//                        .setDefaults(NotificationCompat.DEFAULT_LIGHTS)            //闪灯
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);       //任何情况下都显示   不受锁屏影响
                    manager.notify(0, mBuilder.build());
                    //重复闹钟服务
                    new StartOrStopService(alarmManager).isStartService(name,clockTime,serviceNumber
                            ,true,getApplicationContext(),isClockTime);
                }
            }).start();

        }

        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private void initForeService() {
        if (Build.VERSION.SDK_INT <= 17) {
            Notification notification = new Notification();
            startForeground(1220, notification);
        }
    }

    @Override
    public void onDestroy() {
        Intent in = new Intent();
        in.setClass(context, ClockTimeService.class);

        //是否移除之前的通知
//        stopForeground(true);
        super.onDestroy();
    }
}
