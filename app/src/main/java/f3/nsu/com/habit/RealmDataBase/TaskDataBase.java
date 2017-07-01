package f3.nsu.com.habit.RealmDataBase;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by 爸爸你好 on 2017/6/27.
*/
public class TaskDataBase extends RealmObject {
    @PrimaryKey
    private String id;      //主键   任务列表

    @Required
    private ArrayList taskDataBaseL = new ArrayList<String >();

    private TaskDataBase(){
    }

    private TaskDataBase(ArrayList taskDataBaseL){
        this.taskDataBaseL = taskDataBaseL;
        taskDataBaseL.add("upBed");
        taskDataBaseL.add("sleep");
        taskDataBaseL.add("drink");
        taskDataBaseL.add("run");
        taskDataBaseL.add("fun");
    }

    public ArrayList getTaskDataBaseL() {
        return taskDataBaseL;
    }

    public void setTaskDataBaseL(ArrayList taskDataBaseL) {
        this.taskDataBaseL = taskDataBaseL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
