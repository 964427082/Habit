package f3.nsu.com.habit.RealmDataBase;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.RealmDataBase.TaskData.ConvertIntegralList;
import f3.nsu.com.habit.RealmDataBase.TaskData.CustomTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyHabitTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;
import f3.nsu.com.habit.RealmDataBase.TaskData.RewardList;
import f3.nsu.com.habit.RealmDataBase.TaskData.SystemTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;
import f3.nsu.com.habit.activity.MainActivity;
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
    private String data = new GetTime().getData();

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
            systemTaskList.add(new TaskList("按时起床", 6, false, 30, 2, "07:20"));
            systemTaskList.add(new TaskList("记单词", 6, false, 30, 3, "08:00"));
            systemTaskList.add(new TaskList("看书一小时", 6, false, 30, 2, "10:00"));
            systemTaskList.add(new TaskList("不乱花钱", 5, false, 30, 4, "19:00"));
            systemTaskList.add(new TaskList("存梦想基金", 7, false, 30, 5, "20:10"));

            systemTaskList.add(new TaskList("早上空腹喝杯水", 4, false, 30, 2, "07:50"));
            systemTaskList.add(new TaskList("晨跑", 6, false, 30, 1, "07:00"));
            systemTaskList.add(new TaskList("吃早餐", 3, false, 30, 2, "08:10"));
            systemTaskList.add(new TaskList("喝上5杯水", 4, false, 30, 1, "19:50"));
            systemTaskList.add(new TaskList("按时睡觉", 6, false, 30, 1, "22:00"));
            systemTaskList.add(new TaskList("坚持午觉", 5, false, 30, 1, "13:00"));

            systemTaskList.add(new TaskList("30个俯卧撑", 4, false, 30, 5, "20:40"));
            systemTaskList.add(new TaskList("称体重", 2, false, 30, 5, "20:00"));
            systemTaskList.add(new TaskList("戒烟", 4, false, 30, 3, "08:15"));
            systemTaskList.add(new TaskList("不玩游戏", 6, false, 30, 2, "09:00"));
            systemTaskList.add(new TaskList("静下来听歌", 7, false, 30, 4, "17:05"));

            systemTaskList.add(new TaskList("不说脏话", 5, false, 30, 1, "08:16"));

            systemTaskList.add(new TaskList("拍一张照片", 8, false, 30, 4, "10:40"));
            systemTaskList.add(new TaskList("写下今天的总结", 7, false, 30, 5, "21:30"));
            systemTaskList.add(new TaskList("Call你想念的人", 8, false, 30, 4, "18:00"));
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
    public List<TaskList> showSystemTask() {
        final List<TaskList> s = new ArrayList<>();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SystemTask systemTaskList = mRealm.where((SystemTask.class)).findFirst();
                if (systemTaskList == null)
                    return;
                else
                    s.addAll(systemTaskList.getSystemTaskList());
            }
        });
        return s;
    }


    /**
     * 保存添加的自定义习惯列表
     */
    public void addCustomTask(final String name, final int expectDay, final int color, final String clockTime) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CustomTask c = realm.where(CustomTask.class).findFirst();
                TaskList taskList = realm.createObject(TaskList.class);
                taskList.setName(name);
                taskList.setExpectDay(expectDay);
                taskList.setColorNumber(color);
                taskList.setModify(5);
                taskList.setTime(clockTime);
                taskList.setStart(false);
                if (c == null) {
                    CustomTask customTask = realm.createObject(CustomTask.class, "customTask");
                    customTask.getCustomTaskList().add(taskList);
                } else {
                    c.getCustomTaskList().add(taskList);
                }
            }
        });
    }

    /**
     * 查看自定义习惯列表
     */
    public List<TaskList> showCustomTask() {
        final List<TaskList> c = new ArrayList<>();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CustomTask customTasks = mRealm.where((CustomTask.class)).findFirst();
                if (customTasks == null)
                    return;
                else
                    c.addAll(customTasks.getCustomTaskList());
            }
        });
        return c;
    }

    /**
     * 删除自定义列表中的某项
     */
    public void deleteCustomTask(final String name) {
        final CustomTask customTasks = mRealm.where(CustomTask.class).findFirst();
        final RealmList<TaskList> taskLists = customTasks.getCustomTaskList();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int j = -1;
                for (int i = 0; i < taskLists.size(); i++) {
                    if (name.equals(taskLists.get(i).getName())) {
                        j = i;
                    }
                }
                taskLists.deleteFromRealm(j);
            }
        });
    }

    /**
     * 删除当天习惯时    修改积分
     *
     * @param name 根据名字   删除   对应分值扣除今天分数
     */
    public void deleteMyHabitTaskList(final String name) {
        final MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", data).findFirst();
        final RealmList<MyIntegralList> myIntegralLists = myHabitTask.getMyIntegralList();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int j = -1;
                for (int i = 0; i < myIntegralLists.size(); i++) {
                    if (name.equals(myIntegralLists.get(i).getName())) {
                        j = i;
                    }
                }
                if (myIntegralLists.get(j).isStart()) {
                    int f = myHabitTask.getTodayIntegral() - myIntegralLists.get(j).getModify();
                    myHabitTask.setTodayIntegral(f);
                }
                myIntegralLists.deleteFromRealm(j);
            }
        });
    }


    /**
     * 更改我的习惯坚持天数
     *
     * @param name     键值  唯一的
     * @param amendDay 修改后的预计天数
     */
    public void amendMyHabitDay(final String name, final int amendDay) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                List<MyIntegralList> myIntegralList = myHabitTask.getMyIntegralList();
                for (int i = 0; i < myIntegralList.size(); i++) {
                    if (myIntegralList.get(i).getName().equals(name)) {
                        myIntegralList.get(i).setExpectDay(amendDay);
                    }
                }
            }
        });
    }

    /**
     * 查看当天我要完成的习惯列表
     *
     * @return
     */
    public List<MyIntegralList> showTodayMyHabitIntegralList() {
        final List<MyIntegralList> i = new ArrayList<>();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                if (myHabitTask == null)
                    return;
                i.addAll(myHabitTask.getMyIntegralList());
            }
        });
        return i;
    }

    /**
     * 查看我的某一天习惯列表
     *
     * @return
     */
    public List<MyHabitTask> showMyHabitEveyTask(final String data) {
        final List<MyHabitTask> i = new ArrayList<>();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<MyHabitTask> myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", data).findAll();
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
    public void addMyHabitTask(final String data, final String name, final int modify, final int expectDay, final String clockTime, final int colorNumber) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyHabitTask m = realm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                MyIntegralList myIntegralList = realm.createObject(MyIntegralList.class);
                myIntegralList.setName(name);
                myIntegralList.setModify(modify);
                myIntegralList.setExpectDay(expectDay);
                myIntegralList.setClockTime(clockTime);
                myIntegralList.setInsistDay(0);
                myIntegralList.setColorNumber(colorNumber);
                myIntegralList.setStart(false);
                if (m == null) {
                    MyHabitTask myHabitTask = realm.createObject(MyHabitTask.class, data);
                    myHabitTask.setTodayIntegral(0);
                    myHabitTask.getMyIntegralList().add(myIntegralList);
                } else {
                    m.getMyIntegralList().add(myIntegralList);
                }
            }
        });
    }


    /**
     * 复制我前n天的习惯数据
     *
     * @param myHabit
     */
    public void copyMyHabitTask(final MyHabitTask myHabit) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                boolean is = false;
                List<MyIntegralList> my = myHabit.getMyIntegralList();
                MyHabitTask isM = realm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                if (isM == null) {
                    is = true;
                }
                for (MyIntegralList m : my) {
                    MyIntegralList myIntegralList = realm.createObject(MyIntegralList.class);
                    myIntegralList.setName(m.getName());
                    myIntegralList.setModify(m.getModify());
                    myIntegralList.setExpectDay(m.getExpectDay());
                    myIntegralList.setClockTime(m.getClockTime());
                    myIntegralList.setInsistDay(m.getInsistDay());
                    myIntegralList.setColorNumber(m.getColorNumber());
                    myIntegralList.setStart(false);
                    if (is) {
                        MyHabitTask myHabitTask = realm.createObject(MyHabitTask.class, data);
                        myHabitTask.setTodayIntegral(0);
                        myHabitTask.getMyIntegralList().add(myIntegralList);
                        isM = realm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                        is = false;
                    } else {
                        //问题：   如果isM = null  不知道是否还能向里面添加数据
                        isM.getMyIntegralList().add(myIntegralList);
                    }
                }
            }
        });
    }

    /**
     * 修改是否完成   是否当天点击了
     * 完成了该项     在当天完成积分上加上  并修改为已经点击了
     *
     * @param data   日期
     * @param name   名字
     * @param modify 积分
     */
    public void amendMyHabitIsStart(final String data, final String name, final int modify) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                int f = myHabitTask.getTodayIntegral() + modify;
                myHabitTask.setTodayIntegral(f);
                Log.i(TAG, "execute: 总积分 = " + myHabitTask.getTodayIntegral() + "..该项积分 = " + modify + " 修改后的积分 = " + f);
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
    public int showTotalModify() {
        int showTotalModify = mRealm.where(MyHabitTask.class).sum("todayIntegral").intValue();
        return showTotalModify;
    }

    /**
     * 查看某天的积分
     */
    public int sumTodayModify(String data) {
        MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", data).findFirst();
        int sumTodayModify = myHabitTask.getTodayIntegral();
        return sumTodayModify;
    }


    /**
     * 删除当天所有习惯  并将当天获取的积分置为0
     */
    public void deleteAllMyHabitTask() {
        final MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", data).findFirst();
        if (myHabitTask != null) {
            final RealmList<MyIntegralList> myIntegralLists = myHabitTask.getMyIntegralList();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    myHabitTask.setTodayIntegral(0);
                    myIntegralLists.deleteAllFromRealm();
                }
            });
        }
    }

    /**
     * 添加奖励设置
     *
     * @param name     奖励名称
     * @param why      奖励原因
     * @param integral 奖励需要积分
     */
    public void addRewardList(final String name, final String why, final int integral) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
//                RewardList rewardList = realm.createObject(RewardList.class,name);
                RewardList rewardList = realm.createObject(RewardList.class);
                rewardList.setName(name);
                rewardList.setWhy(why);
                rewardList.setIntegral(integral);
            }
        });
    }

    /**
     * 积分兑换后  需要删除他
     *
     * @param name
     */
    public void deleteRewardOneList(final String name) {
        final RewardList rewardList = mRealm.where(RewardList.class).equalTo("name", name).findFirst();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                rewardList.deleteFromRealm();
            }
        });
    }

    /**
     * 查看已有奖励选项
     *
     * @return
     */
    public List<RewardList> showRewardList() {
        final RealmResults<RewardList> rewardList = mRealm.where(RewardList.class).findAll();
        List<RewardList> r = new ArrayList<>();
        r.addAll(rewardList);
        return r;
    }

    /**
     * 记录积分兑换信息
     *
     * @param name     奖励名字
     * @param integral 奖励分值
     */
    public void convertIntegral(final String name, final int integral) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ConvertIntegralList convertIntegralList = realm.createObject(ConvertIntegralList.class);
                convertIntegralList.setName(name);
                convertIntegralList.setIntegral(integral);
                convertIntegralList.setData(data);
            }
        });
    }

    /**
     * 查询共花费了多少积分
     *
     * @return 积分数
     */
    public int showConvertIntegralAll() {
        int i = mRealm.where(ConvertIntegralList.class).sum("integral").intValue();
        return i;
    }


    /**
     * 查看我的习惯表中最后一条数据
     *
     * @return MyHabitTask
     */
    public MyHabitTask showMyEndTaskList() {
        final RealmResults<MyHabitTask> myHabitTasks = mRealm.where(MyHabitTask.class).findAll();
        if (myHabitTasks.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < myHabitTasks.size(); i++) {
                Log.i(TAG, "onClick:   i = " + i + "size = " + myHabitTasks.size());
                if (i == myHabitTasks.size() - 1) {
                    return myHabitTasks.get(i);
                }
            }
        }
        return null;
    }
}
