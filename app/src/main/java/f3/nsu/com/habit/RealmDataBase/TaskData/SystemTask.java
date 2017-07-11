package f3.nsu.com.habit.RealmDataBase.TaskData;

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

