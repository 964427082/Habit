package f3.nsu.com.habit.ui;

/**
 * 习惯listView内容
 * Created by zhy on 2017/6/30.
 */

public class HabitList {
    private String HabitName;
    private String HabitTime;
    private int goalDay;
    private int completeDay;
    private boolean isComplete;
    public HabitList(){}

    public HabitList(String HabitName,String HabitTime,int goalDay,int completeDay,boolean isComplete ){
        this.HabitName = HabitName;
        this.HabitTime = HabitTime;
        this.goalDay = goalDay;
        this.completeDay = completeDay;
        this.isComplete = isComplete;
    }
    public void setHabitName(String HabitName){
        this.HabitName = HabitName;
    }
    public void setHabitTime(String HabitTime){
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

    //    public void setHabitDay(String HabitDay){
//        this.HabitDay = HabitDay;
//    }
    public String getHabitName(){
        return HabitName;
    }
    public String getHabitTime(){
        return HabitTime;
    }

    public int getGoalDay() {
        return goalDay;
    }

    public int getCompleteDay() {
        return completeDay;
    }
    public boolean getComplete(){
        return isComplete;
    }
    //    public String getHabitDay(){
//        return HabitDay;
//    }
}
