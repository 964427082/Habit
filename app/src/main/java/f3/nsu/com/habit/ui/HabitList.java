package f3.nsu.com.habit.ui;

/**
 * 习惯listView内容
 * Created by zhy on 2017/6/30.
 */

public class HabitList {

    private String HabitName;   //习惯名称
    private String HabitTime;   //提醒时间
    private int goalDay;        //目标天数
    private int completeDay;    //完成天数
    private boolean isComplete; //当天是否完成标记
    private int modify;     //该习惯的积分
    private int colorNumber;    //颜色序号

    public HabitList() {
    }

    public HabitList(String HabitName, String HabitTime, int goalDay, int completeDay, boolean isComplete,int modify,int colorNumber) {
        this.HabitName = HabitName;
        this.HabitTime = HabitTime;
        this.modify = modify;
        this.goalDay = goalDay;
        this.completeDay = completeDay;
        this.isComplete = isComplete;
        this.colorNumber = colorNumber;
    }

    public void setHabitName(String HabitName) {
        this.HabitName = HabitName;
    }

    public void setHabitTime(String HabitTime) {
        this.HabitTime = HabitTime;
    }

    public void setGoalDay(int goalDay) {
        this.goalDay = goalDay;
    }

    public void setCompleteDay(int completeDay) {
        this.completeDay = completeDay;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getHabitName() {
        return HabitName;
    }

    public String getHabitTime() {
        return HabitTime;
    }

    public int getGoalDay() {
        return goalDay;
    }

    public int getCompleteDay() {
        return completeDay;
    }

    public boolean getComplete() {
        return isComplete;
    }

    public int getModify() {
        return modify;
    }

    public void setModify(int modify) {
        this.modify = modify;
    }

    public int getColorNumber() {
        return colorNumber;
    }

    public void setColorNumber(int colorNumber) {
        this.colorNumber = colorNumber;
    }
}
