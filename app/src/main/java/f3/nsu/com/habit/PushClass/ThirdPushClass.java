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
 * 理想的八块腹肌
 */

public class ThirdPushClass {
    private Context context;

    private String data = new GetTime().getData();
    public List<TaskList> ThirdPushList = new ArrayList<>();

    public ThirdPushClass(Context context) {
        this.context = context;
    }

    public ThirdPushClass() {
    }

    public void addMyThirdPush() {
        List<SystemTask> systemTask = DBControl.createRealm(context).showSystemTask();
        List<TaskList> taskLists = systemTask.get(0).getSystemTaskList();
        for (TaskList t : taskLists) {
            if (t.getName().equals("30个俯卧撑") || t.getName().equals("晨跑") || t.getName().equals("称体重") ||
                    t.getName().equals("戒烟") || t.getName().equals("不玩游戏") || t.getName().equals("静下来听歌")) {
                ThirdPushList.add(t);
                addMyThirdPushToMyHabitTask(t.getName(), t.getModify(), t.getExpectDay(), t.getColorNumber(), t.getIsStart(), t.getTime());
            }
        }
    }

    private void addMyThirdPushToMyHabitTask(String name, int modify, int expectDay, int colorNumber, Boolean isStart, String time) {
        DBControl.createRealm(context).addMyHabitTask(data, name, modify, expectDay, time, colorNumber);
    }

    public List<TaskList> getThirdPushList() {
        return ThirdPushList;
    }
}