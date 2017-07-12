package f3.nsu.com.habit.PushClass;

/**
 * Created by 爸爸你好 on 2017/7/11.
 */

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;

/**
 * 第一个推送类   变的更加专注
 */
public class FistPushClass {

    private Context context;
    private String data = new GetTime().getData();
    public List<TaskList> fistPushList = new ArrayList<>();

    public FistPushClass(Context context) {
        this.context = context;
    }
    public FistPushClass(){}


    public List<TaskList> showMyFistPush() {
        fistPushList.add(new TaskList("联系亲朋好友",4,false,100,1,"20:00"));
        fistPushList.add(new TaskList("每天坚持读书",5,false,100,2,"12:00"));
        fistPushList.add(new TaskList("清洁整理房间",4,false,100,4,"08:30"));
        fistPushList.add(new TaskList("每天写一篇日记",5,false,100,5,"19:00"));
        fistPushList.add(new TaskList("睡前回顾当天",4,false,100,3,"22:00"));
        return fistPushList;
    }

    private void addMyFistPushToMyHabitTask(List<TaskList> fpl) {
        for(TaskList t : fpl){
            DBControl.createRealm(context).addMyHabitTask(data,t.getName(),t.getModify(),t.getExpectDay(),t.getTime(),t.getColorNumber());
        }
    }
}
