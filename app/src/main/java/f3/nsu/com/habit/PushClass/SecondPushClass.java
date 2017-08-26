package f3.nsu.com.habit.PushClass;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;

/**
 * Created by 爸爸你好 on 2017/7/11.
 * 健康的作息
 */

public class SecondPushClass {

    private Context context;

    private String data = new GetTime().getData();
    public List<TaskList> secondPushList = new ArrayList<>();

    public SecondPushClass(Context context) {
        this.context = context;
    }

    public void showMySecondPush() {
        secondPushList.add(new TaskList("早起",4,false,100,1,"08:00",26,false));
        secondPushList.add(new TaskList("早上空腹喝杯水",5,false,100,2,"08:10",6,false));
        secondPushList.add(new TaskList("坚持午觉",6,false,100,4,"13:00",11,false));
        secondPushList.add(new TaskList("每天运动半小时",5,false,100,5,"18:00",27,false));
        secondPushList.add(new TaskList("睡前不玩手机",4,false,100,3,"22:00",28,false));
        for(TaskList t : secondPushList){
            DBControl.createRealm(context).addMyHabitTask(data,t.getName(),t.getModify(),t.getExpectDay(),t.getTime(),t.getColorNumber(),t.getServiceNumber());
        }
    }
}