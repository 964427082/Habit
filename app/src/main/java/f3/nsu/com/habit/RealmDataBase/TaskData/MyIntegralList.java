package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmObject;

/**
 * Created by 爸爸你好 on 2017/7/7.
 */

public class MyIntegralList extends RealmObject {
    private String name;    //该习惯名字

    private int modify;     //该习惯的积分
    private String completeTime;    //完成的时间     02：05
    private int expectDay;    //预设计的天数
    private int  insistDay;     //已经坚持的天数
    private String clockTime;   //提醒时间
    private int degreeOfHistory;    //历史完成次数
    private int optimumDegree;  //最佳次数
    private int monthFinishDegree;  //本月完成次数
    private boolean isStart;    //是否当天已经完成
    private int colorNumber;    //颜色序号
    public MyIntegralList (){}
    public MyIntegralList(String name,int modify,String completeTime,int expectDay,int insistDay,String clockTime,
                          int degreeOfHistory,int optimumDegree,int monthFinishDegree,boolean isStart,int colorNumber
    ){
        this.name = name;
        this.modify = modify;
        this.completeTime = completeTime;
        this.expectDay = expectDay;
        this.insistDay = insistDay;
        this.clockTime = clockTime;
        this.degreeOfHistory = degreeOfHistory;
        this.optimumDegree = optimumDegree;
        this.monthFinishDegree = monthFinishDegree;
        this.isStart = isStart;
        this.colorNumber = colorNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getModify() {
        return modify;
    }

    public void setModify(int modify) {
        this.modify = modify;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public int getExpectDay() {
        return expectDay;
    }

    public void setExpectDay(int expectDay) {
        this.expectDay = expectDay;
    }

    public int getInsistDay() {
        return insistDay;
    }

    public void setInsistDay(int insistDay) {
        this.insistDay = insistDay;
    }

    public String getClockTime() {
        return clockTime;
    }

    public void setClockTime(String clockTime) {
        this.clockTime = clockTime;
    }

    public int getDegreeOfHistory() {
        return degreeOfHistory;
    }

    public void setDegreeOfHistory(int degreeOfHistory) {
        this.degreeOfHistory = degreeOfHistory;
    }

    public int getOptimumDegree() {
        return optimumDegree;
    }

    public void setOptimumDegree(int optimumDegree) {
        this.optimumDegree = optimumDegree;
    }

    public int getMonthFinishDegree() {
        return monthFinishDegree;
    }

    public void setMonthFinishDegree(int monthFinishDegree) {
        this.monthFinishDegree = monthFinishDegree;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public int getColorNumber() {
        return colorNumber;
    }

    public void setColorNumber(int colorNumber) {
        this.colorNumber = colorNumber;
    }
}
