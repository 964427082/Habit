package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmObject;

/**
 * Created by 爸爸你好 on 2017/7/21.
 */

/**
 * 记录积分兑换奖励的表单
 */
public class ConvertIntegralList extends RealmObject {
    String name;
    int integral;
    String data;
    public ConvertIntegralList(){}
    public ConvertIntegralList(String name,int integral,String data){
        this.name = name;
        this.integral = integral;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
