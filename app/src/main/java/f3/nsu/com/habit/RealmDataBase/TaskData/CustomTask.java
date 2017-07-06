package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 爸爸你好 on 2017/7/3.
 * 添加自定义习惯任务列表
 * 唯一的一张自定义习惯任务列表
 * 可进行增删查改的操作
 */

public class CustomTask extends RealmObject {

    @PrimaryKey
    private String name;     //名称

    private int expectDay;         //坚持的天数
    private String word;     //一段鼓励的话
    private String color;   //预设计的颜色
    private boolean isStart; //是否完成
    private String clockTime;   //设置提醒时间

    public CustomTask() {
    }

    public CustomTask(String name, int expectDay, String word, String color, boolean isStart,String clockTime) {
        this.name = name;
        this.expectDay = expectDay;
        this.word = word;
        this.color = color;
        this.isStart = isStart;
        this.clockTime = clockTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpectDay() {
        return expectDay;
    }

    public void setExpectDay(int expectDay) {
        this.expectDay = expectDay;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public String getClockTime() {
        return clockTime;
    }

    public void setClockTime(String clockTime) {
        this.clockTime = clockTime;
    }
}
