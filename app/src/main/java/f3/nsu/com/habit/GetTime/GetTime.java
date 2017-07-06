package f3.nsu.com.habit.GetTime;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by 爸爸你好 on 2017/6/27.
 */
public class GetTime {
    private static final String TAG = "GetTime";
    private String data;
    private int year;
    private int month;
    private int day;

    public GetTime() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    public String getWeek() {  // 星期
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "我也不知道今天星期几";
        }
    }

    public String getData() {
        data = String.valueOf(year) + String.valueOf(month) + String.valueOf(day);
        return data;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}

