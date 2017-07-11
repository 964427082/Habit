package f3.nsu.com.habit.PushClass;

/**
 * Created by 爸爸你好 on 2017/7/11.
 */

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.SystemTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;

/**
 * 第一个推送类   变的更加专注
 */
public class FistPushClass {
    private Context context;
    private String data = new GetTime().getData();
    public List<TaskList> FistPushList = new ArrayList<>();

    public FistPushClass(Context context) {
        this.context = context;
    }

    public FistPushClass() {
    }

    public void addMyFistPush() {
        List<SystemTask> systemTask = DBControl.createRealm(context).showSystemTask();
        List<TaskList> taskLists = systemTask.get(0).getSystemTaskList();
        for (TaskList t : taskLists) {
            if (t.getName().equals("按时起床") || t.getName().equals("记单词") || t.getName().equals("看书一小时") ||
                    t.getName().equals("不乱花钱") || t.getName().equals("存梦想基金")) {
                FistPushList.add(t);
                addMyFistPushToMyHabitTask(t.getName(), t.getModify(), t.getExpectDay(), t.getColorNumber(), t.getIsStart(), t.getTime());
            }
        }
    }

    private void addMyFistPushToMyHabitTask(String name, int modify, int expectDay, int colorNumber, Boolean isStart, String time) {
        DBControl.createRealm(context).addMyHabitTask(data, name, modify, expectDay, time, colorNumber);
    }

    public List<TaskList> getFistPushList() {
        return FistPushList;
    }
}
