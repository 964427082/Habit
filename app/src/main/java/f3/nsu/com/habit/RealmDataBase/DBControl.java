package f3.nsu.com.habit.RealmDataBase;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import f3.nsu.com.habit.RealmDataBase.TaskData.CustomTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.SystemTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;
import f3.nsu.com.habit.acitvity.MainActivity;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by 爸爸你好 on 2017/6/27.
 * 封装对Realm数据库的操作
 */
public class DBControl {
    private static final String TAG = "DBControl";
    private static Realm mRealm;
    private static DBControl db;
    private static android.content.Context context;

    private DBControl() {
    }

    /**
     * 单例模式，多次引用
     *
     * @param context
     */
    public static DBControl createRealm(Context context) {
        if (db == null) {
            synchronized (DBControl.class) {
                if (db == null) {
                    Realm.init(context);
                    mRealm = Realm.getDefaultInstance();
                    db = new DBControl();
                }
            }
        }
        return db;
    }

    /**
     * 储存系统习惯任务列表
     *
     * @param st 传入系统习惯任务列表
     *           该方法只执行一次
     */
    public void addSystemTask(final SystemTask st) {
//        Realm.init(MyApplication.getContext());
        Realm.init(context);
        RealmList<TaskList> systemTaskList = st.getSystemTaskList();
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
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm.getDefaultInstance().copyToRealmOrUpdate(st);
            }
        });
    }

    /**
     * 查看系统习惯任务列表
     */
    public List<SystemTask> showSystemTask() {
        final List<SystemTask> s = new ArrayList<>();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<SystemTask> systemTask = mRealm.where((SystemTask.class)).findAll();
                s.addAll(systemTask);
            }
        });
        return s;
    }

    /**
     * 保存添加的自定义习惯列表
     */

    public void addCustomTask(final String name, final int expectDay, final String word, final String color,final String clockTime) {

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CustomTask customTask = realm.createObject(CustomTask.class, name);
                customTask.setExpectDay(expectDay);
                customTask.setWord(word);
                customTask.setColor(color);
                customTask.setClockTime(clockTime);
                customTask.setStart(false);
            }
        });
    }

    /**
     * 查看自定义习惯列表
     */
    public List<CustomTask> showCustomTask() {
        RealmResults<CustomTask> customTasks = mRealm.where((CustomTask.class)).findAll();
        Log.i(TAG, "showCustomTask: TaskSize = " + customTasks.size());
        return mRealm.copyFromRealm(customTasks);
    }

    /**
     * 删除自定义列表中的某项
     */
    public void deleteCustomTask(final String name) {
        final RealmResults<CustomTask> customTasks = mRealm.where(CustomTask.class).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int j = -1;
                for (int i = 0; i < customTasks.size(); i++) {
                    if (name.equals(customTasks.get(i).getName())) {
                        j = i;
                    }
                }
                customTasks.deleteFromRealm(j);
            }
        });
    }


    /**
     * 更改自定义习惯预计坚持天数
     * @param name    键值  唯一的
     * @param amendDay     修改后的预计天数
     */
    public void amendData(final String name,final int amendDay){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CustomTask customTasks = mRealm.where(CustomTask.class).equalTo("name",name).findFirst();
                customTasks.setExpectDay(amendDay);
            }
        });
    }


//
//    /**
//     * 增加当天积分记录
//     * @param idb   累加积分后传入的数据库对象
//     *
//     */
//    public void addIDataBase(final  IntegralDataBase idb) {
////        Realm.init(MyApplication.getContext());
//        Realm.init(context);
////        mRealm = Realm.getDefaultInstance();
//        mRealm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
////                Realm.getDefaultInstance().copyToRealm(idb);        //若该Model没有主键，否则将抛出异常
//                Realm.getDefaultInstance().copyToRealmOrUpdate(idb);//如果对象存在，就更新该对象；反之，它会创建一个新的对象
////                realm.copyToRealm(idb);     //会出现错误    原因是Realm 不能在创建的线程里执行
//            }
//        });
//    }
//
//    /**
//     * 存储任务列表库
//     */
//    public static void addTaskDataBase(final TaskDataBase taskDataBase){
//        Realm.init(context);
//        if(mRealm == null){
//            mRealm = Realm.getDefaultInstance();
//        }
//        mRealm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                Realm.getDefaultInstance().copyToRealmOrUpdate(taskDataBase);
//            }
//        });
//    }
//
//
//    public List<TaskList> showTaskDataBase(){
//        mRealm = Realm.getDefaultInstance();
//        RealmResults<TaskDataBase> taskDataBases = mRealm.where((TaskDataBase.class)).findAll();
//        List<TaskDataBase> taskDataBases1 = taskDataBases;
//        List<TaskList> taskLists = null;
//        for(int i = 0;i < taskDataBases1.size();i++){
//            if(taskDataBases1.get(i).equals(new GetTime().getData())){
//                for(int j = 0;j < taskDataBases1.get(i).getTaskDataBaseList().size();j++){
//                    TaskList taskList = taskDataBases1.get(i).getTaskDataBaseList().get(j);
//                    String name = taskList.getName();
//                    int modify = taskList.getModify();
//                    boolean isStart = taskList.getIsStart();
//                    taskLists.add(taskList);
//                }
//            }
//        }
//        return taskLists;
//    }
//
//
//    /**
//     * 返回创建任务列表数据库的id
//     */
//    public static int getTaskDataBaseId() {
//        if (mRealm == null) {
//            mRealm = Realm.getDefaultInstance();
//        }
//        int id = (int) (mRealm.where(TaskDataBase.class).count() + 1);
//        if (id == 1) {
//            return id;
//        } else
//            return mRealm.where(TaskDataBase.class).findAll().max("id").intValue() + 1;
//    }
//
//    /**
//     * 刷新积分信息
//     * @param newIDataBase  需要更新的积分信息
//     * @param data  根据主键 data 日期来判断需要更新某一天的积分信息
//     */
////    public void modifyIDataBase(final IntegralDataBase newIDataBase, String  data) {
////        final IntegralDataBase integralDataBase = mRealm.where(IntegralDataBase.class).equalTo("data",data).findFirst();
////        mRealm.executeTransaction(new Realm.Transaction() {
////            @Override
////            public void execute(Realm realm) {
////                integralDataBase.setTodayIntegral(newIDataBase.getTodayIntegral());
////            }
////        });
////    }
//
//    /**
//     * 查看积分数据
//     */
//    public List<IntegralDataBase> sleDataIntegralDataBase(){
//        mRealm = Realm.getDefaultInstance();
//        RealmResults<IntegralDataBase> realmResults = mRealm.where((IntegralDataBase.class)).findAll();
//        return mRealm.copyToRealm(realmResults);
//    }
//
//    /**
//     * 累计总积分
//     */
//    public int sumTotalModify(){
//        int sumTotalModify  = mRealm.where(IntegralDataBase.class).sum("todayIntegral").intValue();
//        return sumTotalModify;
//    }
//
//    /**
//     *查看某天的积分
//     */
//    public int sumTodayModify (String data){
//        int sumTodayModify = 0;
//        List<IntegralDataBase> idb = (List<IntegralDataBase>) mRealm.where(IntegralDataBase.class).equalTo("data",data).findFirst();
//        for(int i = 0;i < idb.size();i++){
//            if(idb.get(i).equals("todayIntegral")){
//                sumTodayModify = Integer.valueOf(idb.get(i).toString());
//            }
//        }
//        return sumTodayModify;
//    }

}
