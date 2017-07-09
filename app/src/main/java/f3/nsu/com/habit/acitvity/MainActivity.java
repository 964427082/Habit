package f3.nsu.com.habit.acitvity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import f3.nsu.com.habit.Adapter.HabitAdapter;
import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.CustomTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyHabitTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;
import f3.nsu.com.habit.RealmDataBase.TaskData.SystemTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;
import f3.nsu.com.habit.fragment.HomeFragment;
import f3.nsu.com.habit.fragment.PersonalFragment;
import f3.nsu.com.habit.fragment.PetFragment;
import f3.nsu.com.habit.ui.DrawCircle;
import f3.nsu.com.habit.ui.ExpandLayout;
import f3.nsu.com.habit.ui.HabitList;
import f3.nsu.com.habit.ui.MonthDateView;
import io.realm.Realm;

import static android.content.ContentValues.TAG;
import static f3.nsu.com.habit.RealmDataBase.DBControl.createRealm;

/**
 * 主界面
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener{
    private static final String TAG = "MainActivity";

    HomeFragment mHomeFragment;
    PersonalFragment mPersonalFragment;
    PetFragment mPetFragment;
    private ImageButton button_home,button_pet,button_personal;
    private ImageButton calendar_ImageButton;
    private TextView dayTextView,monthTextView;
    private View currentButton;
    private List<HabitList> habitDate = null;
    private Context mContext;
    private HabitAdapter habitAdapter = null;
    private ListView habitListView;
    private ExpandLayout mExpandLayout;
    public Realm realm;
    public static boolean firstIn = true;
    private MonthDateView monthDateView;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        //添加自定义习惯方式
//        showCustomTask();
//        addCustomTask("ok1", 5, "加油1", "#00000","12:06");
////        showCustomTask();
//        addMyHabitTask(new GetTime().getData(),"oo1",3,20,"12:10");
//        addMyHabitTask(new GetTime().getData(),"oo2",3,20,"12:10");
//        addMyHabitTask(new GetTime().getData(),"oo3",3,20,"12:10");
//
//        addMyHabitTask(new GetTime().getData(),"oo4",3,20,"12:10");
//        addMyHabitTask(new GetTime().getData(),"oo5",3,20,"12:10");
//        addMyHabitTask(new GetTime().getData(),"oo6",3,20,"12:10");
//
//        addMyHabitTask(new GetTime().getData(),"oo7",3,20,"12:10");
//        addMyHabitTask(new GetTime().getData(),"oo8",3,20,"12:10");

        initView();
    }

    /**
     * 添加习惯
     */
    private void addHabit() {
        mContext = MainActivity.this;
        habitListView = (ListView) findViewById(R.id.habit_ListView);
        habitDate = new LinkedList<HabitList>();
        List<MyHabitTask> myHabitTasks = showMyHabitTask();
        for(MyHabitTask s : myHabitTasks){
            int i = 0;
            List<MyIntegralList> myIntegralLists = s.getMyIntegralList();
            for(MyIntegralList m : myIntegralLists){
                if(m.getInsistDay() == 0){
                    habitDate.add(new HabitList(m.getName(),m.getClockTime(),0 ,m.getExpectDay(),m.isStart()));
                }else
                    habitDate.add(new HabitList(m.getName(),m.getClockTime(),m.getInsistDay(),m.getExpectDay(),m.isStart()));

            }
        }

//        habitDate.add(new HabitList("早起","13:00","21天/","45天",false));
//        habitDate.add(new HabitList("记单词","14:00","15天/","30天",false));
//        habitDate.add(new HabitList("喝水","15:00","17天/","20天",false));
//        habitDate.add(new HabitList("早睡","10:00","12天/","30天",false));
//        habitDate.add(new HabitList("装逼","15:30","50天/","50天",false));
//        habitDate.add(new HabitList("撩妹","0:00","50天/","50天",false));
//        habitDate.add(new HabitList("去召唤师峡谷","0:00","50天/","50天",false));
//        habitDate.add(new HabitList("喝水","15:00","17天/","20天",false));
//        habitDate.add(new HabitList("装逼","15:30","50天/","50天",false));
//        habitDate.add(new HabitList("早起","13:00","21天/","45天",false));
        habitAdapter = new HabitAdapter((LinkedList<HabitList>) habitDate,mContext);
        habitListView.setAdapter(habitAdapter);
        habitListView.setOnItemClickListener(this);
    }

    /**
     * 响应ListView中item的点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        Toast.makeText(this, "listview的item被点击了！，点击的位置是-->" + position,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initView() {
        //绑定控件
        mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_home);
        mPetFragment = (PetFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_pet);
        mPersonalFragment = (PersonalFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_personal);


        monthDateView = (MonthDateView) findViewById(R.id.monthDateView);
        completeTag();
        monthDateView.setDateClick(new MonthDateView.DateClick() {

            @Override
            public void onClickOnDate() {
                Toast.makeText(getApplication(), "点击了：" + monthDateView.getmSelDay(), Toast.LENGTH_SHORT).show();
            }
        });

        addHabit();
        dayTextView = (TextView) findViewById(R.id.day_textView);
        monthTextView = (TextView) findViewById(R.id.month_textView);

        setDay();
        calendar_ImageButton = (ImageButton) findViewById(R.id.calendar_ImageButton);
        button_home = (ImageButton) findViewById(R.id.button_home);
        button_personal = (ImageButton) findViewById(R.id.button_personal);
        button_pet = (ImageButton) findViewById(R.id.button_pet);
        button_home.setOnClickListener(this);
        button_pet.setOnClickListener(this);
        button_personal.setOnClickListener(this);

        button_home.performClick();//主动调用button_home点击事件,进入时，显示home界面。

        initExpandView();

//        initSystemTask();       //初始化系统习惯任务列表
    }

    /**
     * 为完成的日期在日历中添加标记
     */
    private void completeTag() {
        //添加指定日期做标记  10 12 15 16
        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(12);
        list.add(15);
        list.add(16);
        monthDateView.setDaysHasThingList(list);
    }

    /**
     * 设置当天日期
     */
    private void setDay() {
        int day = new GetTime().getDay();
        int month = new GetTime().getMonth();
        dayTextView.setText(day+"日");
        monthTextView.setText(month+"月");
    }

    /**
     * 初始化系统习惯任务列表
     */
    private void initSystemTask() {
        List<SystemTask> st = DBControl.createRealm(this).showSystemTask();
        int size = st.size();
        Log.i(TAG, "initView: size = " + size);
        if (size == 0) {
            firstIn = true;
            DBControl.createRealm(this).addSystemTask(new SystemTask());
        } else {
            firstIn = false;
            for (TaskList s : st.get(0).getSystemTaskList()) {
                Log.i(TAG, "initView:  = " + s.getName());
            }
        }
    }


    /**
     * 初始化日历按钮折叠布局 状态为  折叠
     */
    public void initExpandView() {
        mExpandLayout = (ExpandLayout) findViewById(R.id.expandLayout);
        mExpandLayout.initExpand(false);//设置初始化状态，false折叠，true展开
        Log.i("TAG","设置初始化状态为true");
        calendar_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandLayout.toggleExpand();
            }
        });
    }
    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
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

    /**
     * 判断导航栏按钮
     * @param v
     */
    public void setButton(View v) {
        if (currentButton != null && currentButton.getId() != v.getId()) {
            currentButton.setEnabled(true);
        }
        v.setEnabled(false);//setEnabled控制控件false不激活，不管什么属性都无效
        currentButton = v;
    }



    /**
     * 自定义添加习惯列表并保存
     *
     * @param name  自定义习惯的名称
     * @param expectDay   需要坚持的天数
     * @param word  一段鼓励自己的话
     * @param color 列表的颜色
     * @param clockTime 设置提醒时间
     */
    public void addCustomTask(String name, int expectDay, String word, String color,String clockTime) {
        createRealm(this).addCustomTask(name,expectDay,word,color,clockTime);
    }

    /**
     * 查看自定义习惯列表
     */
    public void showCustomTask() {
        List<CustomTask> customTask = createRealm(this).showCustomTask();
        for (CustomTask s : customTask) {
            Log.i(TAG, "initView:  = " + s.getColor());
        }
    }

//    /**
//     * 根据名字删除自定义习惯列表项
//     * @param name   键值
//     */
//    public void deleteCustomTask(String name){
//        DBControl.createRealm(this).deleteCustomTask(name);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    private List<MyHabitTask> showMyHabitTask() {
        List<MyHabitTask> myHabitTask = DBControl.createRealm(this).showMyHabitEveyTask();

//        Log.i(TAG, "showMyHabitEveyTask: 我创建的天数 = " + myHabitTask.size());
////        for(int i = 0;i < myHabitTask.size();i++){
////            myIntegralList.add(myHabitTask.get(i).getMyIntegralList());
////        }
//        int i = 0;
//        for (MyHabitTask s : myHabitTask) {
//            Log.i(TAG, "initView:  创建的日期= " + s.getData());
//            //遍历某一天的习惯名称
//            for(MyIntegralList l : myHabitTask.get(i).getMyIntegralList()){
//                Log.i(TAG, "showMyHabitTask: name = " + l.getName());
//            }
//            i++;
//        }
        return myHabitTask;
    }

    private void addMyHabitTask(String data, String name, int modify, int expectDay, String clockTime) {
        DBControl.createRealm(this).addMyHabitTask(data,name,modify,expectDay,clockTime);
    }
}
