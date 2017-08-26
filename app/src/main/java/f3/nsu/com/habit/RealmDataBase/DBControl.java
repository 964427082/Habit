package f3.nsu.com.habit.RealmDataBase;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.RealmDataBase.TaskData.ConvertIntegralList;
import f3.nsu.com.habit.RealmDataBase.TaskData.CustomTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyHabitTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyHabitTaskIsOk;
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
    private int month = new GetTime().getMonth();
    private int year = new GetTime().getYear();

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
            systemTaskList.add(new TaskList("按时起床", 6, false, 30, 2, "07:20", 1, false));
            systemTaskList.add(new TaskList("记单词", 6, false, 30, 3, "08:00", 2, false));
            systemTaskList.add(new TaskList("看书一小时", 6, false, 30, 2, "10:00", 3, false));
            systemTaskList.add(new TaskList("不乱花钱", 5, false, 30, 4, "19:00", 4, false));
            systemTaskList.add(new TaskList("存梦想基金", 7, false, 30, 5, "20:10", 5, false));

            systemTaskList.add(new TaskList("早上空腹喝杯水", 4, false, 30, 2, "07:50", 6, false));
            systemTaskList.add(new TaskList("晨跑", 6, false, 30, 1, "07:00", 7, false));
            systemTaskList.add(new TaskList("吃早餐", 3, false, 30, 2, "08:10", 8, false));
            systemTaskList.add(new TaskList("喝上5杯水", 4, false, 30, 1, "19:50", 9, false));
            systemTaskList.add(new TaskList("按时睡觉", 6, false, 30, 1, "22:00", 10, false));
            systemTaskList.add(new TaskList("坚持午觉", 5, false, 30, 1, "13:00", 11, false));

            systemTaskList.add(new TaskList("30个俯卧撑", 4, false, 30, 5, "20:40", 12, false));
            systemTaskList.add(new TaskList("称体重", 2, false, 30, 5, "20:00", 13, false));
            systemTaskList.add(new TaskList("戒烟", 4, false, 30, 3, "08:15", 14, false));
            systemTaskList.add(new TaskList("不玩游戏", 6, false, 30, 2, "09:00", 15, false));
            systemTaskList.add(new TaskList("静下来听歌", 7, false, 30, 4, "17:05", 16, false));

            systemTaskList.add(new TaskList("不说脏话", 5, false, 30, 1, "08:16", 17, false));

            systemTaskList.add(new TaskList("拍一张照片", 8, false, 30, 4, "10:40", 18, false));
            systemTaskList.add(new TaskList("写下今天的总结", 7, false, 30, 5, "21:30", 19, false));
            systemTaskList.add(new TaskList("Call你想念的人", 8, false, 30, 4, "18:00", 20, false));
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
    public void addCustomTask(final String name, final int expectDay, final int color, final String clockTime, final int serviceNumber) {
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
                taskList.setServiceNumber(serviceNumber);
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
                for (int i = 0; i < myIntegralLists.size(); i++) {
                    if (name.equals(myIntegralLists.get(i).getName())) {
                        if (myIntegralLists.get(i).isStart()) {
                            int f = myHabitTask.getTodayIntegral() - myIntegralLists.get(i).getModify();
                            myHabitTask.setTodayIntegral(f);
                            //需要去减少总次数吗？
//                    int y = myHabitTask.getOkNumber() - 1;
//                    myHabitTask.setOkNumber(y);
                        }
                        myIntegralLists.deleteFromRealm(i);
                        break;
                    }
                }
            }
        });
    }

    /**
     * 更改我的习惯是否开启通知服务（闹钟）
     *
     * @param name
     * @param isClockTime
     */
    public void amendMyHabitIsStartService(final String name, final boolean isClockTime) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyHabitTask myHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                List<MyIntegralList> myIntegralList = myHabitTask.getMyIntegralList();
                for (int i = 0; i < myIntegralList.size(); i++) {
                    if (myIntegralList.get(i).getName().equals(name)) {
                        myIntegralList.get(i).setIsClockTime(isClockTime);
                    }
                }

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
     *                  需要添加的数据：     本月坚持次数----去遍历历史查询本月最后一天  复制即可！
     *                  最佳连续坚持次数-----去遍历所有    查询该习惯最大连续坚持次数并复制
     *                  历史坚持次数！
     */
    public void addMyHabitTask(final String data, final String name, final int modify, final int expectDay,
                               final String clockTime, final int colorNumber, final int serviceNumber) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyHabitTask m = realm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                MyIntegralList myIntegralList = realm.createObject(MyIntegralList.class);
                myIntegralList.setName(name);       //该习惯名字
                myIntegralList.setModify(modify);       //该习惯的积分
                myIntegralList.setExpectDay(expectDay);     //预设计的天数
                myIntegralList.setClockTime(clockTime);     //提醒时间
                myIntegralList.setInsistDay(0);     //已经坚持的天数
                myIntegralList.setColorNumber(colorNumber);     //颜色序号
                myIntegralList.setStart(false);
                myIntegralList.setIsClockTime(false);
                myIntegralList.setServiceNumber(serviceNumber);

                int dates[] = showHistory(name, 0);

                myIntegralList.setMonthFinishDegree(dates[0]);     //本月完成次数
                myIntegralList.setOptimumDegree(dates[1]);         //最佳次数
                myIntegralList.setDegreeOfHistory(dates[2]);   //历史次数是一直累加的


                if (m == null) {
                    MyHabitTask myHabitTask = realm.createObject(MyHabitTask.class, data);
                    myHabitTask.setTodayIntegral(0);
                    myHabitTask.setMonth(month);
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
                    //如果预计天数等于完成天数   则不去加载
                    if (m.getInsistDay() == m.getExpectDay()) {
                        //可以去存储完成了的习惯   日期为 myHabit.getData
                        int id = DBControl.getId();
                        MyHabitTaskIsOk myHabitTaskIsOk = realm.createObject(MyHabitTaskIsOk.class, id);
                        myHabitTaskIsOk.setName(m.getName());
                        myHabitTaskIsOk.setData(myHabit.getData());
                        myHabitTaskIsOk.setInsistDay(m.getInsistDay());
                        Realm.getDefaultInstance().copyToRealmOrUpdate(myHabitTaskIsOk);
                    } else {
                        MyIntegralList myIntegralList = realm.createObject(MyIntegralList.class);
                        myIntegralList.setName(m.getName());
                        myIntegralList.setModify(m.getModify());
                        myIntegralList.setExpectDay(m.getExpectDay());
                        myIntegralList.setClockTime(m.getClockTime());
                        myIntegralList.setInsistDay(m.getInsistDay());
                        myIntegralList.setColorNumber(m.getColorNumber());
                        myIntegralList.setIsClockTime(m.getIsClockTime());
                        myIntegralList.setOptimumDegree(m.getOptimumDegree());                  //复制了所有的  连续坚持次数
                        myIntegralList.setDegreeOfHistory(m.getDegreeOfHistory());              //复制了所有的  历史坚持次数
                        myIntegralList.setServiceNumber(m.getServiceNumber());
                        //如果复制的数据是上一个月的数据    则将本月坚持次数归零
                        if (myHabit.getMonth() != month) {
                            myIntegralList.setMonthFinishDegree(0);
                        } else {
                            int newYear = Integer.valueOf(myHabit.getData().substring(0, 4));
                            if (newYear == year) {
                                myIntegralList.setMonthFinishDegree(m.getMonthFinishDegree());      //复制本月坚持次数
                            } else
                                myIntegralList.setMonthFinishDegree(0);
                        }
                        myIntegralList.setStart(false);
                        if (is) {
                            MyHabitTask myHabitTask = realm.createObject(MyHabitTask.class, data);
                            myHabitTask.setTodayIntegral(0);
                            myHabitTask.setOkNumber(0);
                            myHabitTask.setMonth(month);
                            myHabitTask.getMyIntegralList().add(myIntegralList);
                            isM = realm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                            is = false;
                        } else {
                            isM.getMyIntegralList().add(myIntegralList);
                        }
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

                int f = myHabitTask.getTodayIntegral() + modify;    //当天累计积分
                int y = myHabitTask.getOkNumber() + 1;              //当天完成次数
                myHabitTask.setTodayIntegral(f);
                myHabitTask.setOkNumber(y);

                RealmList<MyIntegralList> myIntegralLists = myHabitTask.getMyIntegralList();
                for (MyIntegralList m : myIntegralLists) {
                    if (m.getName().equals(name)) {
                        m.setStart(true);
                        m.setInsistDay(m.getInsistDay() + 1);

                        m.setMonthFinishDegree(m.getMonthFinishDegree() + 1);   //本月完成次数
                        int optimumDegree = yesterdayOptimumDegreeNumber(data, name);   //最佳连续次数
                        m.setOptimumDegree(optimumDegree);
                        m.setDegreeOfHistory(m.getDegreeOfHistory() + 1);       //历史完成次数

                        break;
                    }
                }
            }
        });
    }

    /**
     * 查看昨天的连续次数  是否完成了
     * @param newData
     * @return
     */

    /**
     * 最佳完成次数
     * 1.判断昨天是否完成
     * 注：连续完成是指   连续的日期？ 还是连续的登录完成？
     */
    private int yesterdayOptimumDegreeNumber(String newData, String newName) {
        String oldData = "";
        String oldDay = "";
        int optimumDegree = 1;
        int newDay = Integer.valueOf(newData.substring(6, 8));    //日期
        if (month == 2 || month == 4 || month == 6 || month == 9 || month == 11) {
            if (newDay != 1) {
                if ((newDay - 1) < 10)
                    oldDay = "0" + String.valueOf(newDay - 1);
                else
                    oldDay = String.valueOf(newDay - 1);
            } else {
                month = month - 1;
                oldDay = "31";
            }
        } else {
            if (newDay != 1) {
                if ((newDay - 1) < 10)
                    oldDay = "0" + String.valueOf(newDay - 1);
                else
                    oldDay = String.valueOf(newDay - 1);
            } else {
                oldDay = "31";
                if (month == 1) {
                    year = year - 1;
                    month = 12;
                } else if (month == 3) {
                    if (((year % 4) == 0 && (year % 100 != 0)) || (year / 400 == 0)) {
                        oldDay = "29";
                    } else
                        oldDay = "28";
                    month = month - 1;
                } else
                    month = month - 1;
            }
        }
        String mo = null;
        if (month < 10) {
            mo = "0" + String.valueOf(month);
        } else
            mo = String.valueOf(month);
        //上一次登录的日期
        oldData = String.valueOf(year) + mo + oldDay;
        final MyHabitTask oldMyHabitTask = mRealm.where(MyHabitTask.class).equalTo("data", oldData).findFirst();

        if (oldMyHabitTask != null) {
            List<MyIntegralList> myIntegralList = oldMyHabitTask.getMyIntegralList();
            for (MyIntegralList myI : myIntegralList) {
                //去判断该习惯昨天是否完成
                if (myI.getName().equals(newName)) {
                    if (myI.isStart()) {
                        optimumDegree = myI.getOptimumDegree() + 1;
                        break;
                    }
                    break;
                }
            }
        }
        return optimumDegree;
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
     * 查看总完成次数
     *
     * @return
     */
    public int showTotalOkTaskNumber() {
        int o = mRealm.where(MyHabitTask.class).sum("okNumber").intValue();
        return o;
    }

    /**
     * 查看前一天是否完成了所有的习惯
     *
     * @return 都完成 返回true
     */
    public int showYesterdayIs() {
        RealmResults<MyHabitTask> myHabitTasks = mRealm.where(MyHabitTask.class).findAll();
        if (myHabitTasks.size() == 0) {
            return 0;
        } else {
            MyHabitTask myHabitTask;
            if (myHabitTasks.size() == 1) {
                myHabitTask = myHabitTasks.get(myHabitTasks.size() - 1);
                return myHabitTask.getHoldNumber();
            } else
                myHabitTask = myHabitTasks.get(myHabitTasks.size() - 2);
            for (MyIntegralList ml : myHabitTask.getMyIntegralList()) {
                if (!ml.isStart())
                    return myHabitTask.getHoldNumber();
            }
            return (myHabitTask.getHoldNumber() + 1);
        }
    }


    //获取个人中心中坚持天数
    public int getHoldNumber() {
        MyHabitTask myHabitTasks = mRealm.where(MyHabitTask.class).equalTo("data", data).findFirst();
        if (myHabitTasks == null) {
            return 0;
        } else
            return myHabitTasks.getHoldNumber();
    }

    //修改累计天数
    public void amendHoldNumber(final int number) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                MyHabitTask myHabitTasks = mRealm.where(MyHabitTask.class).equalTo("data", data).findFirst();
                myHabitTasks.setHoldNumber(number);
            }
        });
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
                if (i == myHabitTasks.size() - 1) {
                    return myHabitTasks.get(i);
                }
            }
        }
        return null;
    }

    /**
     * 查看该月的日期  当天是否有完成一项习惯
     * 去判断当天是否有累加积分   不为0则视为有完成习惯
     *
     * @return
     */
    public List<String> showToMonthEverDayIntegral() {
        final List<String> l = new ArrayList<>();
        final RealmResults<MyHabitTask> myHabitTaskList = mRealm.where(MyHabitTask.class).equalTo("month", month).findAll();
        for (MyHabitTask m : myHabitTaskList) {
            if (m.getTodayIntegral() > 0) {
                l.add(m.getData());
            }
        }
        return l;
    }

    /**
     * 查看完成习惯   指100%完成
     *
     * @return
     */
    public List<MyHabitTaskIsOk> showOkTask() {
        List<MyHabitTaskIsOk> newMy = new ArrayList<>();
        final RealmResults<MyHabitTaskIsOk> myHabitTaskIsOk = mRealm.where(MyHabitTaskIsOk.class).findAll();
        newMy.addAll(myHabitTaskIsOk);
        return newMy;
    }

    /**
     * 返回创建用户的id
     *
     * @return
     */
    public static int getId() {
        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }
        int id = (int) mRealm.where(MyHabitTaskIsOk.class).count() + 1;
        if (id == 1) {
            return id;
        } else {
            return mRealm.where(MyHabitTaskIsOk.class).findAll().max("id").intValue() + 1;
        }
    }

    /**
     * 查看本月某习惯坚持情况
     *
     * @param name 该习惯名字
     * @return 坚持的日期的一个集合
     */
    public List<Integer> showToMonthHoldNumber(String name) {
        List<Integer> number = new ArrayList<>();
        final RealmResults<MyHabitTask> myHabitTasks = mRealm.where(MyHabitTask.class).equalTo("month", month).findAll();
        for (MyHabitTask m : myHabitTasks) {
            List<MyIntegralList> myIntegralList = m.getMyIntegralList();
            for (MyIntegralList ml : myIntegralList) {
                if (ml.getName().equals(name)) {
                    if (ml.isStart()) {
                        int n = Integer.valueOf(m.getData().substring(6, 8));
                        number.add(n);
                        break;
                    }
                    break;
                }
            }
        }
        return number;
    }

    public RealmResults<MyHabitTask> showToMonth() {
        final RealmResults<MyHabitTask> myHabitTasks = mRealm.where(MyHabitTask.class).equalTo("month", month).findAll();
        return myHabitTasks;
    }

    /**
     * 查询某习惯历史坚持次数
     * 本月坚持次数----去遍历历史查询本月最后一天  复制即可！
     * 最佳连续坚持次数-----去遍历所有    查询该习惯最大连续坚持次数并复制
     *
     * @param name
     * @return
     */
    public int[] showHistory(String name, int number) {
        final RealmResults<MyHabitTask> myHabitTasks = showToMonth();
        final RealmResults<MyIntegralList> myHistoryIntegralLists = mRealm.where(MyIntegralList.class).equalTo("name", name).findAll();

        int toMonthHoldNumber = 0;
        int historyOkNumber = 0;
        //在添加的时候将  最佳连续完成次数置0
        // 在修改时  如果日期连续则复制前一天值
        // 在查询时去找最大值
        int optimumDegreeNumber = 0;
        //遍历查找本月某习惯坚持次数
        if (myHabitTasks.size() != 0) {
            //这个是add时
            int size1 = 0;
            if (myHabitTasks.size() > 1) {
                if (number == 0) {
                    size1 = myHabitTasks.size() - 2;
                } else
                    size1 = myHabitTasks.size() - 1;
            }

//            if(number == 1){
//                for(int i = 0;i < myHabitTasks.size();i++){
//                    List<MyIntegralList> mm = myHabitTasks.get(i).getMyIntegralList();
//                    for(MyIntegralList myiho : mm){
//                        if(name.equals(myiho.getName()))
//                        Log.i(TAG, "showHistory: 遍历show时 = " + myHabitTasks.get(i).getData() + " .. name = "
//                                + myiho.getName() + "..积分 = " + myiho.getMonthFinishDegree());
//                    }
//                }
//            }
            List<MyIntegralList> myIntegralList = myHabitTasks.get(size1).getMyIntegralList();
            for (MyIntegralList my : myIntegralList) {
                if (my.getName().equals(name)) {
                    toMonthHoldNumber = my.getMonthFinishDegree();
                    break;
                }
            }


//            if (number == 0) {
//                int size = 0;
//                if (myHabitTasks.size() > 1)
//                    size = myHabitTasks.size() - 2;
//                List<MyIntegralList> myIntegralList = myHabitTasks.get(size).getMyIntegralList();
//                for (MyIntegralList my : myIntegralList) {
//                    if (my.getName().equals(name)) {
//                        toMonthHoldNumber0 = my.getMonthFinishDegree();
//                        break;
//                    }
//                }
//            } else {
//                int size = myHabitTasks.size();
//                List<MyIntegralList> showMyIntegralList = null;
//                int index = 0;
//                if (size > 1) {
//                    index = size - 1;
//                }
//                showMyIntegralList = myHabitTasks.get(index).getMyIntegralList();
//                for (MyIntegralList my : showMyIntegralList) {
//                    if (my.getName().equals(name)) {
//                        if (myHabitTasks.get(index).getData().equals(data)) {
//                            toMonthHoldNumber1 = my.getMonthFinishDegree();
//                            break;
//                        }
//                    }
//                }
//            }
//        }

            //遍历查找某习惯历史完成次数
            if (myHistoryIntegralLists.size() != 0) {
                int size2 = 0;
                if (myHistoryIntegralLists.size() > 1) {
                    if (number == 0) {
                        //这个是add时  -2
                        size2 = myHistoryIntegralLists.size() - 2;
                    } else
                        //这个是show时  -1
                        size2 = myHistoryIntegralLists.size() - 1;
                }
                if (myHistoryIntegralLists.get(size2).getName().equals(name)) {
                    historyOkNumber = myHistoryIntegralLists.get(size2).getDegreeOfHistory();
                }
            }
        }
        int datas0[] = {toMonthHoldNumber, optimumDegreeNumber, historyOkNumber};
        return datas0;
    }

    /**
     * 查看某一习惯最佳连续坚持次数
     *
     * @param name
     * @return
     */
    public int showContinuousHoldNumber(String name) {
        int number = 0;
        final RealmResults<MyIntegralList> myIntegralLists = mRealm.where(MyIntegralList.class).equalTo("name", name).findAll();
        for (MyIntegralList m : myIntegralLists) {
            if (m.getOptimumDegree() > number) {
                number = m.getOptimumDegree();
            }
        }
        return number;
    }
}
