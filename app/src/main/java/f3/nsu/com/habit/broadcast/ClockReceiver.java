package f3.nsu.com.habit.broadcast;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

import f3.nsu.com.habit.service.ClockTimeService;

/**
 * Created by 爸爸你好 on 2017/8/11.
 */

public class ClockReceiver extends BroadcastReceiver {
    private static final String TAG = "ClockReceiver";
    private static final String serviceName = "f3.nsu.com.habit.service.ClockTimeService";

    @Override
    public void onReceive(Context context, Intent intent) {
        //重复执行服务
//        if (intent.getAction() == "android.intent.action.USER_PRESENT") {
//            Log.i(TAG, "onStartCommand111111: 这是解锁权限   可开启服务");
//            if (isServiceRunning(context, serviceName)) {
//                Log.i(TAG, "onReceive: 表示服务正在开启");
//            } else {
//                Log.i(TAG, "onStartCommand111111: 重新去启动服务");
//                Bundle bundle = intent.getExtras();
//                String name = bundle.getString("name");
//                String clockTime = bundle.getString("clockTime");
//                Log.i(TAG, "onStartCommand111111:重新去启动服务 name = " + name + "..clockTime = " + clockTime);
//                Intent i = new Intent(context, ClockTimeService.class);
//                Bundle bu = new Bundle();
//                bu.putString("name", name);
//                bu.putString("clockTime", clockTime);
//                bu.putBoolean("isClockTime", true);
//                i.putExtras(bu);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startService(i);
//            }
//        }
        if (intent.getAction() == "com.habit.service") {
//            isServiceRunning(context, serviceName);

            Bundle bundle = intent.getExtras();
            String name = bundle.getString("name");
            String clockTime = bundle.getString("clockTime");
            int number = bundle.getInt("number");
            boolean isClockTime = bundle.getBoolean("isClockTime");
            Intent i = new Intent(context, ClockTimeService.class);
            Bundle bu = new Bundle();
            bu.putString("name", name);
            bu.putString("clockTime", clockTime);
            bu.putBoolean("isClockTime", isClockTime);
            bu.putInt("number",number);
            bu.putBoolean("broadcastReceiver",true);
            i.putExtras(bu);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startService(i);
        }
    }

    public static boolean isServiceRunning(Context mContext, String className) {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfoList = activityManager.getRunningServices(30);
        if (!(serviceInfoList.size() > 0)) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo i : serviceInfoList) {
            if (i.service.getClassName().contains(className) == true) {
                return true;
            }
        }
        return false;
    }
}
