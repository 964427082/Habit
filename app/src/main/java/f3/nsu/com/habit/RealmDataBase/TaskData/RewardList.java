package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmObject;

/**
 * Created by 爸爸你好 on 2017/7/18.
 */

public class RewardList extends RealmObject {
//    @PrimaryKey
    private String name;

    private String why;
    private int integral;

    public RewardList(){}

    public RewardList(String name,String why,int integral){
        this.name = name;
        this.why = why;
        this.integral = integral;
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
}
