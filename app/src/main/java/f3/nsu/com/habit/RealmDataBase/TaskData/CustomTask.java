package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 爸爸你好 on 2017/7/3.
 * 添加自定义习惯任务列表
 * 唯一的一张自定义习惯任务列表
 * 可进行增删查改的操作
 */

public class CustomTask extends RealmObject {

    @PrimaryKey
    private String customTask = "customTask";      //主键   任务列表

    private RealmList<CustomList> customTaskList = new RealmList<CustomList>();

    public CustomTask() {
    }

    public CustomTask(RealmList<CustomList> customTaskList) {
        this.customTaskList = customTaskList;
    }

    public String getCustomTask() {
        return customTask;
    }

    public void setCustomTask(String customTask) {
        this.customTask = customTask;
    }

    public RealmList<CustomList> getCustomTaskList() {
        return customTaskList;
    }

    public void setCustomTaskList(RealmList<CustomList> customTaskList) {
        this.customTaskList = customTaskList;
    }
}
