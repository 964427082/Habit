package f3.nsu.com.habit.RealmDataBase;

import android.content.Context;

import java.util.List;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskDataBase;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 爸爸你好 on 2017/6/27.
 * 封装对Realm数据库的操作
 */
public class DBControl {
    private static Realm mRealm;
    private static DBControl db;
    private static Context context;

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
                    mRealm = Realm.getDefaultInstance();
                    db = new DBControl();
                }
            }
        }
        DBControl.context = context;
        return db;
    }


    /**
     * 增加当天积分记录
     * @param idb   累加积分后传入的数据库对象
     *
     */
    public void addIDataBase(final  IntegralDataBase idb) {
//        Realm.init(MyApplication.getContext());
        Realm.init(context);
//        mRealm = Realm.getDefaultInstance();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
//                Realm.getDefaultInstance().copyToRealm(idb);        //若该Model没有主键，否则将抛出异常
                Realm.getDefaultInstance().copyToRealmOrUpdate(idb);//如果对象存在，就更新该对象；反之，它会创建一个新的对象
//                realm.copyToRealm(idb);     //会出现错误    原因是Realm 不能在创建的线程里执行
            }
        });
    }

    /**
     * 存储任务列表库
     */
    public static void addTaskDataBase(final TaskDataBase taskDataBase){
        Realm.init(context);
        if(mRealm == null){
            mRealm = Realm.getDefaultInstance();
        }
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Realm.getDefaultInstance().copyToRealmOrUpdate(taskDataBase);
            }
        });
    }


    public List<TaskList> showTaskDataBase(){
        mRealm = Realm.getDefaultInstance();
        RealmResults<TaskDataBase> taskDataBases = mRealm.where((TaskDataBase.class)).findAll();
        List<TaskDataBase> taskDataBases1 = taskDataBases;
        List<TaskList> taskLists = null;
        for(int i = 0;i < taskDataBases1.size();i++){
            if(taskDataBases1.get(i).equals(new GetTime().getData())){
                for(int j = 0;j < taskDataBases1.get(i).getTaskDataBaseList().size();j++){
                    TaskList taskList = taskDataBases1.get(i).getTaskDataBaseList().get(j);
                    String name = taskList.getName();
                    int modify = taskList.getModify();
                    boolean isStart = taskList.getIsStart();
                    taskLists.add(taskList);
                }
            }
        }
        return taskLists;
    }


    /**
     * 返回创建任务列表数据库的id
     */
    public static int getTaskDataBaseId() {
        if (mRealm == null) {
            mRealm = Realm.getDefaultInstance();
        }
        int id = (int) (mRealm.where(TaskDataBase.class).count() + 1);
        if (id == 1) {
            return id;
        } else
            return mRealm.where(TaskDataBase.class).findAll().max("id").intValue() + 1;
    }

    /**
     * 刷新积分信息
     * @param newIDataBase  需要更新的积分信息
     * @param data  根据主键 data 日期来判断需要更新某一天的积分信息
     */
//    public void modifyIDataBase(final IntegralDataBase newIDataBase, String  data) {
//        final IntegralDataBase integralDataBase = mRealm.where(IntegralDataBase.class).equalTo("data",data).findFirst();
//        mRealm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                integralDataBase.setTodayIntegral(newIDataBase.getTodayIntegral());
//            }
//        });
//    }

    /**
     * 查看积分数据
     */
    public List<IntegralDataBase> sleDataIntegralDataBase(){
        mRealm = Realm.getDefaultInstance();
        RealmResults<IntegralDataBase> realmResults = mRealm.where((IntegralDataBase.class)).findAll();
        return mRealm.copyToRealm(realmResults);
    }

    /**
     * 累计总积分
     */
    public int sumTotalModify(){
        int sumTotalModify  = mRealm.where(IntegralDataBase.class).sum("todayIntegral").intValue();
        return sumTotalModify;
    }

    /**
     *查看某天的积分
     */
    public int sumTodayModify (String data){
        int sumTodayModify = 0;
        List<IntegralDataBase> idb = (List<IntegralDataBase>) mRealm.where(IntegralDataBase.class).equalTo("data",data).findFirst();
        for(int i = 0;i < idb.size();i++){
            if(idb.get(i).equals("todayIntegral")){
                sumTodayModify = Integer.valueOf(idb.get(i).toString());
            }
        }
        return sumTodayModify;
    }
}
