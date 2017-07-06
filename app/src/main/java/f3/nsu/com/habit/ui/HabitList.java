package f3.nsu.com.habit.ui;



/**
 * 习惯listView内容
 * Created by zhy on 2017/6/30.
 */

public class HabitList {
    private String HabitName;
    private String HabitTime;
    private String HabitDay;
    private int HabitButtonColor;
    private int CompleteButton;

    public HabitList(){}

    public HabitList(String HabitName,String HabitTime,String HabitDay,int HabitButtonColor,int CompleteButton){
        this.HabitName = HabitName;
        this.HabitTime = HabitTime;
        this.HabitDay = HabitDay;
        this.HabitButtonColor = HabitButtonColor;
        this.CompleteButton = CompleteButton;
    }
    public void setHabitName(String HabitName){
        this.HabitName = HabitName;
    }
    public void setHabitTime(String HabitTime){
        this.HabitTime = HabitTime;
    }
    public void setHabitDay(String HabitDay){
        this.HabitDay = HabitDay;
    }
    public void setHabitButtonColor(int HabitButtonColor){
        this.HabitButtonColor = HabitButtonColor;
    }
    public void setCompleteButton(int CompleteButton){
        this.CompleteButton = CompleteButton;
    }
    public String getHabitName(){
        return HabitName;
    }
    public String getHabitTime(){
        return HabitTime;
    }
    public String getHabitDay(){
        return HabitDay;
    }
    public int getHabitButtonColor(){
        return HabitButtonColor;
    }
    public int getCompleteButton(){
        return CompleteButton;
    }
}
