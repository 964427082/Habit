package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 爸爸你好 on 2017/7/6.
 */

public class MyHabitTask extends RealmObject {
    @PrimaryKey
    private String data;    //主键   日期：20170706     储存为某天完成的习惯

    private int month;   //月份
    private RealmList<MyIntegralList> myIntegralList;
    private int todayIntegral; //当天积累的分数
    private int okNumber;       //当天完成次数
    private int holdNumber;     //坚持天数

    public MyHabitTask() {
    }

    public MyHabitTask(String data, RealmList<MyIntegralList> myIntegralList, int todayIntegral, int okNumber,int month,int holdNumber) {
        this.data = data;
        this.myIntegralList = myIntegralList;
        this.todayIntegral = todayIntegral;
        this.okNumber = okNumber;
        this.month = month;
        this.holdNumber = holdNumber;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public RealmList<MyIntegralList> getMyIntegralList() {
        return myIntegralList;
    }

    public void setMyIntegralList(RealmList<MyIntegralList> myIntegralList) {
        this.myIntegralList = myIntegralList;
    }

    public int getTodayIntegral() {
        return todayIntegral;
    }

    public void setTodayIntegral(int todayIntegral) {
        this.todayIntegral = todayIntegral;
    }

    public int getOkNumber() {
        return okNumber;
    }

    public void setOkNumber(int okNumber) {
        this.okNumber = okNumber;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getHoldNumber() {
        return holdNumber;
    }

    public void setHoldNumber(int holdNumber) {
        this.holdNumber = holdNumber;
    }
}
