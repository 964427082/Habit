package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmObject;

/**
 * Created by 爸爸你好 on 2017/7/2.
 */

public class TaskList extends RealmObject {
    private String name;    //系统习惯任务名字

    private int modify;     //系统给定积分
    private boolean isStart;    //判断是否完成的标志

    public TaskList() {
    }

    public TaskList(String name, int modify, boolean isStart) {
        this.name = name;
        this.modify = modify;
        this.isStart = isStart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getModify() {
        return modify;
    }

    public void setModify(int modify) {
        this.modify = modify;
    }

    public boolean getIsStart() {
        return isStart;
    }

    public void setIsStart(boolean start) {
        isStart = start;
    }
}
