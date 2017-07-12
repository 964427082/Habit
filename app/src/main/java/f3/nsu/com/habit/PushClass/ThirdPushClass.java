package f3.nsu.com.habit.PushClass;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;

/**
 * Created by 爸爸你好 on 2017/7/11.
 * 理想的八块腹肌
 */

public class ThirdPushClass {
    private Context context;

    private String data = new GetTime().getData();
    public List<TaskList> thirdPushList = new ArrayList<>();

    public ThirdPushClass(Context context) {
        this.context = context;
    }

    public ThirdPushClass() {
    }

    public List<TaskList> showMyThirdPush() {
        thirdPushList.add(new TaskList("不喝碳酸饮料",4,false,100,1,"09:00"));
        thirdPushList.add(new TaskList("多吃蔬菜和水果",5,false,100,2,"11:30"));
        thirdPushList.add(new TaskList("进行高效率运动",6,false,100,4,"15:30"));
        thirdPushList.add(new TaskList("完成腹部的燃烧脂肪",5,false,100,5,"19:30"));
        thirdPushList.add(new TaskList("坚持有氧运动",4,false,100,3,"21:30"));
        return thirdPushList;
    }

    private void addMySecondPushToMyHabitTask(List<TaskList> tpl) {
        for(TaskList t : tpl){
            DBControl.createRealm(context).addMyHabitTask(data,t.getName(),t.getModify(),t.getExpectDay(),t.getTime(),t.getColorNumber());
        }
    }
}