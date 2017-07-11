package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmObject;

/**
 * Created by 爸爸你好 on 2017/7/2.
 */

public class TaskList extends RealmObject {
    private String name;    //系统习惯任务名字

    private int modify;     //系统给定积分
    private boolean isStart;    //判断是否完成的标志
    private int expectDay;      //系统给定的预定天数
    private int colorNumber;    //颜色序号
    private String time;        //提醒时间

    public TaskList() {
    }

    public TaskList(String name, int modify, boolean isStart,int expectDay,int colorNumber,String time) {
        this.name = name;
        this.modify = modify;
        this.isStart = isStart;
        this.expectDay = expectDay;
        this.colorNumber = colorNumber;
        this.time = time;
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

    public boolean getIsStart() {
        return isStart;
    }

    public void setIsStart(boolean start) {
        isStart = start;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public int getExpectDay() {
        return expectDay;
    }

    public void setExpectDay(int expectDay) {
        this.expectDay = expectDay;
    }

    public int getColorNumber() {
        return colorNumber;
    }

    public void setColorNumber(int colorNumber) {
        this.colorNumber = colorNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
