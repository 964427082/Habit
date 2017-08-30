package f3.nsu.com.habit.tool;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import f3.nsu.com.habit.broadcast.ClockReceiver;
import f3.nsu.com.habit.ui.CountClockTime;

/**
 * Created by 爸爸你好 on 2017/8/26.
 */

public class StartOrStopService {
    int anMin = 60 * 1000;          //这是一分钟的毫秒数
    int anS = 1 * 1000;             //这是一秒钟的毫秒数
    AlarmManager alarmManager;
    public StartOrStopService(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    /**
     * @param name      习惯名称
     * @param clockTime 提醒时间
     * @param serviceNumber     服务序号
     * @param is        是否去开启
     * @param context
     * @param isClockTime    是否已经开启了
     */
    public void isStartService(String name, String clockTime, int serviceNumber, boolean is,
                               Context context,Boolean isClockTime){
        Intent i = new Intent(context, ClockReceiver.class);
        long triggerAtTime = 0;
        int hour = Integer.parseInt(clockTime.substring(0, 2));
        int minuet = Integer.parseInt(clockTime.substring(3, 5));
        if (hour == 0)
            hour = 24;
        int getTime = new CountClockTime(hour, minuet).getTime();
        int callTime = getTime * anMin;     //这是要提醒的时间
        int egTime = 10 * anS;              //这是测试用的
        triggerAtTime = SystemClock.elapsedRealtime() + callTime;
        Bundle bu = new Bundle();
        i.setAction("com.habit.service");
        bu.putString("name", name);
        bu.putString("clockTime", clockTime);
        bu.putBoolean("isClockTime", isClockTime);
        bu.putInt("number", serviceNumber);
        i.putExtras(bu);
        PendingIntent pi = PendingIntent.getBroadcast(context, serviceNumber, i, PendingIntent.FLAG_UPDATE_CURRENT);
        if (is) {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        } else {
            alarmManager.cancel(pi);
        }
    }
}
