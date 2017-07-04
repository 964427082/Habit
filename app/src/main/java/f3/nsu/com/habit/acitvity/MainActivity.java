package f3.nsu.com.habit.acitvity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.CustomList;
import f3.nsu.com.habit.RealmDataBase.TaskData.CustomTask;
import f3.nsu.com.habit.fragment.HomeFragment;
import f3.nsu.com.habit.fragment.PersonalFragment;
import f3.nsu.com.habit.fragment.PetFragment;
import io.realm.Realm;

import static f3.nsu.com.habit.RealmDataBase.DBControl.createRealm;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    HomeFragment mHomeFragment;
    PersonalFragment mPersonalFragment;
    PetFragment mPetFragment;
    @BindView(R.id.button_eg)
    Button buttonEg;
    private ImageButton button_home, button_pet, button_personal;
    private View currentButton;
    public Realm realm;
    public static boolean firstIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        initView();
    }

    private void initView() {
        mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_home);
        mPetFragment = (PetFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_pet);
        mPersonalFragment = (PersonalFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_personal);
        button_home = (ImageButton) findViewById(R.id.button_home);
        button_personal = (ImageButton) findViewById(R.id.button_personal);
        button_pet = (ImageButton) findViewById(R.id.button_pet);
        button_home.setOnClickListener(this);
        button_pet.setOnClickListener(this);
        button_personal.setOnClickListener(this);
        button_home.performClick();//主动调用button_home点击事件,进入时，显示home界面。

        /**
         * 初始化系统习惯任务列表
         */
//        List<SystemTask> st = DBControl.createRealm(this).showSystemTask();
//        int size = st.size();
//        Log.i(TAG, "initView: size = " + size);
//        if (size == 0) {
//            firstIn = true;
//            DBControl.createRealm(this).addSystemTask(new SystemTask());
//        } else {
//            firstIn = false;
//            for (TaskList s : st.get(0).getSystemTaskList()) {
//                Log.i(TAG, "initView:  = " + s.getName());
//            }
//        }


        DBControl.createRealm(this).addCustomTask("ok1", 5, "加油", "#00000");
//        DBControl.createRealm(this).addCustomTask("ok2", 5, "加油22222", "#02222");
//        DBControl.createRealm(this).addCustomTask("ok3", 5, "加3333", "#33333");
//        DBControl.createRealm(this).addCustomTask("ok4", 5, "加44444", "#44444");
//        showCustomTask();
    }

    @OnClick(R.id.button_eg)
    public void onViewClicked() {
        List<CustomTask> customTask = createRealm(this).showCustomTask();
        for (CustomList s : customTask.get(0).getCustomTaskList()) {
            Log.i(TAG, "initView:  = " + s.getName());
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_home:
                getSupportFragmentManager().beginTransaction().hide(mPersonalFragment).hide(mPetFragment).show(mHomeFragment).commit();
                setButton(v);
                break;
            case R.id.button_pet:
                getSupportFragmentManager().beginTransaction().hide(mHomeFragment).hide(mPersonalFragment).show(mPetFragment).commit();
                setButton(v);
                break;
            case R.id.button_personal:
                getSupportFragmentManager().beginTransaction().hide(mHomeFragment).hide(mPetFragment).show(mPersonalFragment).commit();
                setButton(v);
                break;
            default:
                break;
        }
    }

    public void setButton(View v) {
        if (currentButton != null && currentButton.getId() != v.getId()) {
            currentButton.setEnabled(true);
        }
        v.setEnabled(false);//setEnabled控制控件false不激活，不管什么属性都无效
        currentButton = v;
    }


//    /**
//     * 自定义添加习惯列表并保存
//     *
//     * @param name  自定义习惯的名称
//     * @param day   需要坚持的天数
//     * @param word  一段鼓励自己的话
//     * @param color 列表的颜色
//     */
//    public void addCustomTask(String name, int day, String word, String color) {
//        List<CustomTask> customTask = createRealm(this).showCustomTask();
//
//        if (customTask.size() == 0) {
//            CustomTask ct = new CustomTask();
//            RealmList<CustomList> customLists = ct.getCustomTaskList();
//            customLists.add(new CustomList(name, day, word, color));
//            createRealm(this).addCustomTask(ct);
//        }
//        else {
//            RealmList<CustomList> cl = customTask.get(0).getCustomTaskList();
//        }
////        else {
////            CustomTask ct = customTask.get(0);
////            RealmList<CustomList> customLists = ct.getCustomTaskList();
////            int i = 0;
////            Log.i(TAG, "addCustomTask: MainActivity size =  " + customLists.size());
////            while (i < customLists.size()) {
////                i++;
////            }
////            CustomList cl = new CustomList();
////            cl.setName(name);
////            cl.setDay(day);
////            cl.setWord(word);
////            cl.setColor(color);
////            customLists.add(i + 1, cl);
////            createRealm(this).addCustomTask(ct);
////        }
//    }

    public void showCustomTask() {
        List<CustomTask> customTask = createRealm(this).showCustomTask();
        Log.i(TAG, "showCustomTask: TaskSize = " + customTask.size());
        if (customTask.size() != 0) {
            Log.i(TAG, "showCustomTask: name = " + customTask.get(0).getCustomTaskList().get(0).getName());
        }
    }


//    /**
//     * 修改当天积分信息
//     * @param data  当天的主键   日期
//     * @param today  修改后的当天积分
//     * @param week   星期几
//     */
//    public void todayIntegral(String data,int today,String week){
//        IntegralDataBase idb = new IntegralDataBase();
//        idb.setData(data);
//        idb.setTodayIntegral(today);
//        idb.setWeek(week);
//        createRealm(this).addIDataBase(idb);
//    }
//
//    /**
//     * 查看所有总积分
//     * @return   总积分
//     */
//    public int showTotalModify(){
//        int totalModify = 0;
//        totalModify = DBControl.createRealm(this).sumTotalModify();
//        return totalModify;
//    }
//
//    /**
//     * 查询某天积分
//     * @param data  当天的日期
//     * @return   当天的积分
//     */
//    public int showAnyDayModify(String data){
//        int anyDayModify = DBControl.createRealm(this).sumTodayModify(data);
//        return anyDayModify;
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
