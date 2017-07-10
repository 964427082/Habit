package f3.nsu.com.habit.RealmDataBase;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import f3.nsu.com.habit.RealmDataBase.TaskData.CustomTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyHabitTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;
import f3.nsu.com.habit.RealmDataBase.TaskData.SystemTask;
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
    public void addCustomTask(final String name, final int expectDay, final String word, final String color, final String clockTime) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CustomTask customTask = realm.createObject(CustomTask.class, name);
                customTask.setExpectDay(expectDay);
                customTask.setWord(word);
                customTask.setColor(color);
                customTask.setModify(5);
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
     *
     * @param name     键值  唯一的
     * @param amendDay 修改后的预计天数
     */
    public void amendData(final String name, final int amendDay) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CustomTask customTasks = mRealm.where(CustomTask.class).equalTo("name", name).findFirst();
                customTasks.setExpectDay(amendDay);
            }
        });
    }


    public List<MyHabitTask> showMyHabitEveyTask() {
        final List<MyHabitTask> i = new ArrayList<>();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<MyHabitTask> myHabitTask = mRealm.where(MyHabitTask.class).findAll();
                i.addAll(myHabitTask);
            }
        });
        return i;
    }

    /**
     * 添加我的习惯列表
     *
     * @param data      日期
     * @param name      习惯名字
     * @param modify    积分分数
     * @param expectDay 坚持的天数
     * @param clockTime 提醒的时间
     */
    public void addMyHabitTask(final String data, final String name, final int modify, final int expectDay, final String clockTime,final int colorNumber) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyHabitTask m = realm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                MyIntegralList myIntegralList = realm.createObject(MyIntegralList.class, name);
                myIntegralList.setModify(modify);
                myIntegralList.setExpectDay(expectDay);
                myIntegralList.setClockTime(clockTime);
                myIntegralList.setInsistDay(0);
                myIntegralList.setColorNumber(colorNumber);
                myIntegralList.setStart(false);
                if (m == null) {
                    MyHabitTask myHabitTask = realm.createObject(MyHabitTask.class, data);
                    myHabitTask.getMyIntegralList().add(myIntegralList);
                } else {
                    m.getMyIntegralList().add(myIntegralList);
                }
            }
        });
    }

    /**
     * 修改是否完成
     *
     * @param data 日期
     * @param name 名字
     */
    public void amendMyHabitIsStart(final String data, final String name) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                RealmList<MyIntegralList> myIntegralLists = myHabitTask.getMyIntegralList();
                for (int i = 0; i < myIntegralLists.size(); i++) {
                    if (myIntegralLists.get(i).getName().equals(name)) {
                        myIntegralLists.get(i).setStart(true);
                        int j = myIntegralLists.get(i).getInsistDay() + 1;
                        myIntegralLists.get(i).setInsistDay(j);
                        Log.i(TAG, "execute: 天数  = " + j);
                    }
                }
            }
        });
    }

    public List<MyIntegralList> amendProgress(String date) {
        MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", date).findFirst();
        List<MyIntegralList> myIntegralLists = new ArrayList<>();
        myIntegralLists.addAll(myHabitTask.getMyIntegralList());
        return myIntegralLists;
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
