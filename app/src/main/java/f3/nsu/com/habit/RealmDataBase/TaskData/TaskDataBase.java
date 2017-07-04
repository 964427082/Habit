//package f3.nsu.com.habit.RealmDataBase.TaskData;
//
//import io.realm.RealmList;
//import io.realm.RealmObject;
//import io.realm.annotations.PrimaryKey;
//
///**
// * Created by 爸爸你好 on 2017/6/27.
// */
//public class TaskDataBase extends RealmObject {
//    @PrimaryKey
//    private String data;      //主键   任务列表
//
//    private RealmList<TaskList> taskDataBaseList = new RealmList<TaskList>();
//
//    public TaskDataBase() {
//    }
//
//    public TaskDataBase(RealmList<TaskList> taskDataBaseList) {
//        this.taskDataBaseList = taskDataBaseList;
//        taskDataBaseList.add(0, new TaskList("按时起床", 6, false));
//        taskDataBaseList.add(1, new TaskList("按时睡觉", 5, false));
//        taskDataBaseList.add(2, new TaskList("早上空腹喝杯水", 4, false));
//        taskDataBaseList.add(3, new TaskList("每天运动", 5, false));
//        taskDataBaseList.add(4, new TaskList("拍一张照片", 8, false));
//        taskDataBaseList.add(5, new TaskList("写下今天的总结", 5, false));
//        taskDataBaseList.add(6, new TaskList("Call你想念的人", 8, false));
//        taskDataBaseList.add(7, new TaskList("记单词", 5, false));
//        taskDataBaseList.add(8, new TaskList("吃早餐", 5, false));
//        taskDataBaseList.add(9, new TaskList("睡午觉", 5, false));
//        taskDataBaseList.add(10, new TaskList("看书一小时", 5, false));
//        taskDataBaseList.add(11, new TaskList("戒烟", 5, false));
//        taskDataBaseList.add(12, new TaskList("多喝水", 5, false));
//        taskDataBaseList.add(13, new TaskList("不乱花钱", 5, false));
//        taskDataBaseList.add(14, new TaskList("存梦想基金", 5, false));
//        taskDataBaseList.add(15, new TaskList("称体重", 5, false));
//        taskDataBaseList.add(16, new TaskList("不说脏话", 5, false));
//        taskDataBaseList.add(17, new TaskList("不玩游戏", 5, false));
//        taskDataBaseList.add(18, new TaskList("睡午觉", 5, false));
//        taskDataBaseList.add(19, new TaskList("思考一下人生", 5, false));
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }
//
//    public RealmList<TaskList> getTaskDataBaseList() {
//        return taskDataBaseList;
//    }
//
//    public void setTaskDataBaseList(RealmList<TaskList> taskDataBaseList) {
//        this.taskDataBaseList = taskDataBaseList;
//    }
//}
