package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmObject;

/**
 * Created by 爸爸你好 on 2017/7/21.
 */

/**
 * 记录积分兑换奖励的表单
 */
public class ConvertIntegralList extends RealmObject {
    String name;    //习惯名字
    int integral;   //所消耗积分
    String data;    //日期
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
