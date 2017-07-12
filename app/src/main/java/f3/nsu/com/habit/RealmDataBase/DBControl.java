package f3.nsu.com.habit.RealmDataBase;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.RealmDataBase.TaskData.CustomTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyHabitTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;
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
    private  String data = new GetTime().getData();
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
        RealmList<TaskList> systemTaskList = st.getSystemTaskList();
        if (MainActivity.firstIn) {
            Log.i("add", "SystemTask: ");
            systemTaskList.add(new TaskList("按时起床", 6, false, 30, 2,"07:20"));
            systemTaskList.add(new TaskList("记单词", 6, false, 30, 3,"08:00"));
            systemTaskList.add(new TaskList("看书一小时", 6, false, 30, 2,"10:00"));
            systemTaskList.add(new TaskList("不乱花钱", 5, false, 30, 4,"19:00"));
            systemTaskList.add(new TaskList("存梦想基金", 7, false, 30, 5,"20:10"));

            systemTaskList.add(new TaskList("早上空腹喝杯水", 4, false, 30, 2,"07:50"));
            systemTaskList.add(new TaskList("晨跑",6,false,30,1,"07:00"));
            systemTaskList.add(new TaskList("吃早餐", 3, false, 30, 2,"08:10"));
            systemTaskList.add(new TaskList("喝上5杯水", 4, false, 30, 1,"19:50"));
            systemTaskList.add(new TaskList("按时睡觉", 6, false, 30, 1,"22:00"));
            systemTaskList.add(new TaskList("坚持午觉", 5, false, 30, 1,"13:00"));

            systemTaskList.add(new TaskList("30个俯卧撑",4,false,30,5,"20:40"));
            systemTaskList.add(new TaskList("称体重", 2, false, 30, 5,"20:00"));
            systemTaskList.add(new TaskList("戒烟", 4, false, 30, 3,"08:15"));
            systemTaskList.add(new TaskList("不玩游戏", 6, false, 30, 2,"09:00"));
            systemTaskList.add(new TaskList("静下来听歌",7,false,30,4,"17:05"));

            systemTaskList.add(new TaskList("不说脏话", 5, false, 30, 1,"08:16"));

            systemTaskList.add(new TaskList("拍一张照片", 8, false, 30, 4,"10:40"));
            systemTaskList.add(new TaskList("写下今天的总结", 7, false, 30, 5,"21:30"));
            systemTaskList.add(new TaskList("Call你想念的人", 8, false, 30, 4,"18:00"));
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
        final List<CustomTask> c = new ArrayList<>();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<CustomTask> customTasks = mRealm.where((CustomTask.class)).findAll();
                c.addAll(customTasks);
            }
        });
        Log.i(TAG, "showCustomTask: TaskSize = " + c.size());
        return c;
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
     * 更改我的习惯坚持天数
     * @param name     键值  唯一的
     * @param amendDay 修改后的预计天数
     */
    public void amendMyHabitDay(final String name, final int amendDay) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data",data).findFirst();
                List<MyIntegralList> myIntegralList = myHabitTask.getMyIntegralList();
                for(int i = 0;i < myIntegralList.size();i++){
                    if(myIntegralList.get(i).getName().equals(name)){
                        myIntegralList.get(i).setExpectDay(amendDay);
                    }
                }
            }
        });
    }


    /**
     * 查看我的所有习惯列表
     *
     * @return
     */
    public List<MyHabitTask> showMyHabitEveyTask() {
        final List<MyHabitTask> i = new ArrayList<>();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<MyHabitTask> myHabitTask = mRealm.where(MyHabitTask.class).findAll();
                i.addAll(myHabitTask);
            }
        });
        Log.i(TAG, "showMyHabitEveyTask: 我的所有习惯列表个数 = " + i.size());
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
    public void addMyHabitTask(final String data, final String name, final int modify, final int expectDay, final String clockTime, final int colorNumber) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyHabitTask m = realm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                m.setTodayIntegral(0);

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
     * 修改是否完成   是否当天点击了
     * 完成了该项     在当天完成积分上加上  并修改为已经点击了
     * @param data 日期
     * @param name 名字
     * @param modify 积分
     */
    public void amendMyHabitIsStart(final String data, final String name,final int modify) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                myHabitTask.setTodayIntegral(myHabitTask.getTodayIntegral() + modify);
                RealmList<MyIntegralList> myIntegralLists = myHabitTask.getMyIntegralList();
                for (int i = 0; i < myIntegralLists.size(); i++) {
                    if (myIntegralLists.get(i).getName().equals(name)) {
                        myIntegralLists.get(i).setStart(true);
                        myIntegralLists.get(i).setInsistDay(myIntegralLists.get(i).getInsistDay() + 1);
                    }
                }
            }
        });
    }

    /**
     * 修改进度条
     *
     * @param date 日期
     * @return
     */
    public List<MyIntegralList> amendProgress(String date) {
        MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", date).findFirst();
        List<MyIntegralList> myIntegralLists = new ArrayList<>();
        myIntegralLists.addAll(myHabitTask.getMyIntegralList());
        return myIntegralLists;
    }

    /**
     * 查看总积分
     */
    public int showTotalModify(){
        int showTotalModify  = mRealm.where(MyHabitTask.class).sum("todayIntegral").intValue();
        return showTotalModify;
    }

    /**
     *查看某天的积分
     */
    public int sumTodayModify (String data){
        MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data",data).findFirst();
        int sumTodayModify = myHabitTask.getTodayIntegral();
        return sumTodayModify;
    }

//    /**
//     * 查看积分数据
//     */
//    public List<IntegralDataBase> sleDataIntegralDataBase(){
//        mRealm = Realm.getDefaultInstance();
//        RealmResults<IntegralDataBase> realmResults = mRealm.where((IntegralDataBase.class)).findAll();
//        return mRealm.copyToRealm(realmResults);
//    }

}
