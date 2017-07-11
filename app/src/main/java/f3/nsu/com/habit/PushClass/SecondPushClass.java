package f3.nsu.com.habit.PushClass;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.SystemTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;

/**
 * Created by 爸爸你好 on 2017/7/11.
 * 健康的作息
 */

public class SecondPushClass {

    private Context context;

    private String data = new GetTime().getData();
    public List<TaskList> SecondPushList = new ArrayList<>();

    public SecondPushClass(Context context) {
        this.context = context;
    }

    public SecondPushClass() {
    }

    public void addMySecondPush() {
        List<SystemTask> systemTask = DBControl.createRealm(context).showSystemTask();
        List<TaskList> taskLists = systemTask.get(0).getSystemTaskList();
        for (TaskList t : taskLists) {
            if (t.getName().equals("早上空腹喝杯水") || t.getName().equals("晨跑") || t.getName().equals("吃早餐") ||
                    t.getName().equals("喝上5杯水") || t.getName().equals("按时睡觉") || t.getName().equals("睡午觉")) {
                SecondPushList.add(t);
                addMySecondPushToMyHabitTask(t.getName(), t.getModify(), t.getExpectDay(), t.getColorNumber(), t.getIsStart(), t.getTime());
            }
        }
    }

    private void addMySecondPushToMyHabitTask(String name, int modify, int expectDay, int colorNumber, Boolean isStart, String time) {
        DBControl.createRealm(context).addMyHabitTask(data, name, modify, expectDay, time, colorNumber);
    }

    public List<TaskList> getSecondPushList() {
        return SecondPushList;
    }
}