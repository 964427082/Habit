package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmObject;

/**
 * Created by 爸爸你好 on 2017/7/18.
 * 设置的积分奖励
 */

public class RewardList extends RealmObject {
//    @PrimaryKey
    private String name;    //奖励名字

    private String why;     //奖励原因
    private int integral;   //所需积分
    private boolean isFinish;   //是否完成

    public RewardList(){}

    public RewardList(String name,String why,int integral,boolean isFinish){
        this.name = name;
        this.why = why;
        this.integral = integral;
        this.isFinish = isFinish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public boolean getIsFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}
