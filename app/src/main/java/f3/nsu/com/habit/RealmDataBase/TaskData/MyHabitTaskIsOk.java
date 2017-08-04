package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 爸爸你好 on 2017/7/30.
 */

//储存已经完成了的习惯
public class MyHabitTaskIsOk extends RealmObject {
    @PrimaryKey
    private int id;

    private String data;    //完成日期
    private int insistDay;  //累计坚持天数
    private String name;    //习惯名字


    public MyHabitTaskIsOk(){}

    public MyHabitTaskIsOk(int id,String data,int insistDay,String name){
        this.id  = id;
        this.data = data;
        this.insistDay = insistDay;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getInsistDay() {
        return insistDay;
    }

    public void setInsistDay(int insistDay) {
        this.insistDay = insistDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
