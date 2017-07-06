package f3.nsu.com.habit.RealmDataBase.TaskData;

import android.util.Log;

import f3.nsu.com.habit.acitvity.MainActivity;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 爸爸你好 on 2017/7/3.
 * 系统推荐习惯任务列表
 * 唯一的一张表   不可进行增删查改的操作！
 */

public class SystemTask extends RealmObject {
    @PrimaryKey
    private String systemTask = "systemTask";      //主键   系统提供的习惯任务

    private RealmList<TaskList> systemTaskList = new RealmList<TaskList>();

    public SystemTask(RealmList<TaskList> systemTaskList) {
        this.systemTaskList = systemTaskList;
    }

    public SystemTask() {
        if (MainActivity.firstIn) {
            Log.i("add", "SystemTask: ");
            systemTaskList.add(0, new TaskList("按时起床", 6, false));
            systemTaskList.add(1, new TaskList("按时睡觉", 5, false));
            systemTaskList.add(2, new TaskList("早上空腹喝杯水", 4, false));
            systemTaskList.add(3, new TaskList("每天运动", 5, false));
            systemTaskList.add(4, new TaskList("拍一张照片", 8, false));
            systemTaskList.add(5, new TaskList("写下今天的总结", 5, false));
            systemTaskList.add(6, new TaskList("Call你想念的人", 8, false));
            systemTaskList.add(7, new TaskList("记单词", 5, false));
            systemTaskList.add(8, new TaskList("吃早餐", 5, false));
            systemTaskList.add(9, new TaskList("睡午觉", 5, false));
            systemTaskList.add(10, new TaskList("看书一小时", 5, false));
            systemTaskList.add(11, new TaskList("戒烟", 5, false));
            systemTaskList.add(12, new TaskList("多喝水", 5, false));
            systemTaskList.add(13, new TaskList("不乱花钱", 5, false));
            systemTaskList.add(14, new TaskList("存梦想基金", 5, false));
            systemTaskList.add(15, new TaskList("称体重", 5, false));
            systemTaskList.add(16, new TaskList("不说脏话", 5, false));
            systemTaskList.add(17, new TaskList("不玩游戏", 5, false));
        }
    }

    public String getSystemTask() {
        return systemTask;
    }

    public void setSystemTask(String systemTask) {
        this.systemTask = systemTask;
    }

    public RealmList<TaskList> getSystemTaskList() {
        return systemTaskList;
    }

    public void setSystemTaskList(RealmList<TaskList> systemTaskList) {
        this.systemTaskList = systemTaskList;
    }
}

