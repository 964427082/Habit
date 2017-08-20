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
import android.util.Log;

import f3.nsu.com.habit.R;
import f3.nsu.com.habit.activity.MainActivity;
import f3.nsu.com.habit.broadcast.ClockReceiver;

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onStartCommand111111  onCreate: ");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        name = bundle.getString("name");
        clockTime = bundle.getString("clockTime");
        isClockTime = bundle.getBoolean("isClockTime");
        serviceNumber = bundle.getInt("number");


        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 60 * 60 * 1000;    //这是一小时的毫秒数
        int anMin = 60 * 1000;          //这是一分钟的毫秒数
        int anSec = 1 * 1000;           //这是一秒的毫秒数
        int time = 10 * anSec;                   //这是要提醒的时间
        long triggerAtTime = SystemClock.elapsedRealtime() + time;      //测试用     10秒后发送通知
        Intent i = new Intent(this, ClockReceiver.class);
        Bundle bu = new Bundle();
        i.setAction("com.habit.service");
        bu.putString("name",name);
        bu.putString("clockTime",clockTime);
        bu.putBoolean("isClockTime",isClockTime);
        bu.putInt("number",serviceNumber);
        i.putExtras(bu);
        PendingIntent pi = PendingIntent.getBroadcast(this, serviceNumber, i,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        //isClockTime 该标志用于判断启动该服务是从广播来的
        if(isClockTime){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //这里去执行逻辑操作
                    Intent toIntent = new Intent(context, MainActivity.class);
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, toIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
                    mBuilder.setContentTitle("Habit")
                            .setContentText("老铁，‘" + name +"’还没完成！")
//                        .setTicker("什么标题？")
                            .setSmallIcon(R.drawable.circle_selected)
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
                }
            }).start();
        }

        flags = START_STICKY;
        return super.onStartCommand(intent,flags,startId);
    }
    private void initForeService(){
        if(Build.VERSION.SDK_INT <= 17){
            Notification notification = new Notification();
            startForeground(1220,notification);
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onStartCommand111111  onDestroy: 活动被销毁");
        Intent in = new Intent();
        in.setClass(context,ClockTimeService.class);

        //是否移除之前的通知
//        stopForeground(true);
        super.onDestroy();
    }
}
