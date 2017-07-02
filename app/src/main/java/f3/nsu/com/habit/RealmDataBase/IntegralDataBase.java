package f3.nsu.com.habit.RealmDataBase;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by 爸爸你好 on 2017/6/27.
 */
public class IntegralDataBase extends RealmObject {
    @PrimaryKey
    private String data;     //把日期作为主键  形式为20170101

    @Required
    private int todayIntegral;   //当天获得的积分
    private String week;    //周日期

    public IntegralDataBase(String data, int todayIntegral, String week) {
        this.data = data;
        this.todayIntegral = todayIntegral;
        this.week = week;
    }

    public void setData(String data) {
        this.data = data;
    }


    public void setTodayIntegral(int todayIntegral) {
        this.todayIntegral = todayIntegral;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getData() {
        return data;
    }

    public int getTodayIntegral() {
        return todayIntegral;
    }

    public String getWeek() {
        return week;
    }
}
