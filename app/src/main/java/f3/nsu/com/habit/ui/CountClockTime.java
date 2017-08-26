package f3.nsu.com.habit.ui;

import android.util.Log;

import f3.nsu.com.habit.GetTime.GetTime;

/**
 * Created by 爸爸你好 on 2017/8/22.
 */

/**
 * 计算闹钟延时长度
 */
public class CountClockTime {
    int nowHour = new GetTime().getNowHour();
    int nowMinute = new GetTime().getNowMinute();
    private int hour;
    private int minuet;

    public CountClockTime(int hour, int minuet) {
        this.hour = hour;
        this.minuet = minuet;
    }

    public int getTime() {
        int hourValue = hour - nowHour;     //时差
        int minuteValue = minuet - nowMinute;   //分差
        int everTime = 0;       //该返回值为分钟

        if (hourValue == 0) {
            if (minuteValue == 0)
                everTime = 1440;
            else if (minuteValue > 0)
                everTime = minuteValue;
            else
                everTime = 1440 + minuteValue;
        } else if (hourValue > 0) {
            if (minuteValue == 0)
                everTime = hourValue * 60;
            else
                everTime = (hourValue * 60) + minuteValue;
        } else {
            if (minuteValue < 0) {
                everTime = ((23 - nowHour + hour) * 60) + (60 + minuteValue);
            } else
            everTime = ((24 - nowHour + hour) * 60) + minuteValue;
        }
        return everTime;
    }
}
