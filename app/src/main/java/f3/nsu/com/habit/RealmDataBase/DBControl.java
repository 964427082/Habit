package f3.nsu.com.habit.RealmDataBase;

import android.content.Context;

import java.util.List;

import f3.nsu.com.habit.MyApplication;
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
     *
     */
    public void addIDataBase(final IntegralDataBase idb) {
        Realm.init(MyApplication.getContext());
//        Realm.init(context);
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
     * 增加任务列表库
     */



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
    public void modifyIDataBase(final IntegralDataBase newIDataBase, String  data) {
        final IntegralDataBase integralDataBase = mRealm.where(IntegralDataBase.class).equalTo("data",data).findFirst();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                integralDataBase.setTodayIntegral(newIDataBase.getTodayIntegral());
            }
        });
    }

    /**
     * 查看数据
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
//        List<IntegralDataBase> integralDataBases = sleDataIntegralDataBase();
//        int size = integralDataBases.size();
//        int sumTotalModify = 0;
//        for(int i = 0;i < size;i++){
//            if(integralDataBases.equals("todayIntegral")){
//                sumTotalModify += integralDataBases.get(i).getTodayIntegral();
//            }
//    }
        int sumTotalModify  = mRealm.where(IntegralDataBase.class).sum("todayIntegral").intValue();
        return sumTotalModify;
    }

    /**
     *查看当天的积分
     * @param data 传入的data标志符
     */
    public int sumTodayModify (String data){
        List<IntegralDataBase> integralDataBasesList = sleDataIntegralDataBase();
        int size = integralDataBasesList.size();
        RealmResults<IntegralDataBase> integralDataBase = mRealm.where(IntegralDataBase.class).equalTo("data",data).findAll();
        int sumTodayModify = integralDataBase.sum("todayIntegral").intValue();
        return sumTodayModify;
    }

}
