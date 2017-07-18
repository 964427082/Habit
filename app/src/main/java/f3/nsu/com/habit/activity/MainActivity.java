package f3.nsu.com.habit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import f3.nsu.com.habit.Adapter.HabitAdapter;
import f3.nsu.com.habit.Adapter.IntegralAdapter;
import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyHabitTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;
import f3.nsu.com.habit.RealmDataBase.TaskData.RewardList;
import f3.nsu.com.habit.RealmDataBase.TaskData.SystemTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;
import f3.nsu.com.habit.fragment.AddRewardFragment;
import f3.nsu.com.habit.fragment.HomeFragment;
import f3.nsu.com.habit.fragment.IntegralFragment;
import f3.nsu.com.habit.fragment.PersonalFragment;
import f3.nsu.com.habit.ui.ExpandLayout;
import f3.nsu.com.habit.ui.HabitList;
import f3.nsu.com.habit.ui.IntegralList;
import f3.nsu.com.habit.ui.MonthDateView;
import io.realm.Realm;

import static f3.nsu.com.habit.RealmDataBase.DBControl.createRealm;

/**
 * 主界面
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";

    HomeFragment mHomeFragment;//主界面
    PersonalFragment mPersonalFragment;//个人界面
    IntegralFragment mIntegralFragment;//宠物界面
    AddRewardFragment mAddRewardFragment;//添加奖励

    private ImageButton button_home, button_pet, button_personal;//底部导航栏按钮
    private ImageButton calendar_ImageButton;//左上角日历按钮
    private TextView dayTextView, monthTextView;//日历展开界面日期
    private ImageButton insert_imageButton;//右上角添加习惯按钮
    private View currentButton;
    private List<HabitList> habitDate = null;
    private LinkedList<IntegralList> integralData = null;
    private Context mContext;
    private HabitAdapter habitAdapter = null;
    private IntegralAdapter integralAdapter = null;
    private ListView habitListView;
    private ExpandLayout mExpandLayout;
    public Realm realm;
    public static boolean firstIn = true;
    private MonthDateView monthDateView;
    private ListView integralListView;
    private ImageButton add_reward, return_Integral, imageBtnSaveIntegral;
    private EditText rewardNameEditText, rewardReasonEditText;
    private NumberPicker numberPicker;
    private TextView myIntegralTextView;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        initSystemTask();       //初始化系统习惯任务列表
        initView();
    }

    /**
     * 添加习惯
     */
    private void addHabit() {
        List<MyHabitTask> myHabitTasks = showMyHabitTask();
        habitDate.clear();
        for (MyHabitTask s : myHabitTasks) {
            if (s.getData().equals(new GetTime().getData())) {
                List<MyIntegralList> myIntegralLists = s.getMyIntegralList();
                for (MyIntegralList m : myIntegralLists) {
                    habitDate.add(new HabitList(m.getName(), m.getClockTime(), m.getInsistDay(), m.getExpectDay(), m.isStart(), m.getModify(), m.getColorNumber()));
                }
            }
        }
        habitAdapter = new HabitAdapter((LinkedList<HabitList>) habitDate, mContext);
        habitAdapter.notifyDataSetChanged();
        habitListView.setAdapter(habitAdapter);
        habitListView.setOnItemClickListener(this);
    }

    /**
     * 响应ListView中item的点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        Intent intent = new Intent(MainActivity.this, SituationActivity.class);
        startActivity(intent);

    }

    /**
     * 初始化
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initView() {
        mContext = MainActivity.this;
        habitDate = new LinkedList<HabitList>();
        //绑定控件
        mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_home);
        mIntegralFragment = (IntegralFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_pet);
        mAddRewardFragment = (AddRewardFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_add_reward);
        mPersonalFragment = (PersonalFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_personal);

        habitListView = (ListView) findViewById(R.id.habit_ListView);
        monthDateView = (MonthDateView) findViewById(R.id.monthDateView);
        completeTag();

        dayTextView = (TextView) findViewById(R.id.day_textView);
        monthTextView = (TextView) findViewById(R.id.month_textView);

        setDay();
        calendar_ImageButton = (ImageButton) findViewById(R.id.calendar_ImageButton);
        insert_imageButton = (ImageButton) findViewById(R.id.insert_imageButton);
        insert_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyAddHabitActivity.class);
                startActivity(intent);
            }
        });

        button_home = (ImageButton) findViewById(R.id.button_home);
        button_personal = (ImageButton) findViewById(R.id.button_personal);
        button_pet = (ImageButton) findViewById(R.id.button_pet);
        add_reward = (ImageButton) findViewById(R.id.add_reward);
        return_Integral = (ImageButton) findViewById(R.id.return_integral);
        myIntegralTextView = (TextView) findViewById(R.id.my_integral_textView);

        //积分奖励自定义界面
        imageBtnSaveIntegral = (ImageButton) findViewById(R.id.image_btn_save_integral);
        rewardNameEditText = (EditText) findViewById(R.id.rewardName_editText);
        rewardReasonEditText = (EditText) findViewById(R.id.rewardReason_editText);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        imageBtnSaveIntegral.setOnClickListener(this);

        button_home.setOnClickListener(this);
        button_pet.setOnClickListener(this);
        button_personal.setOnClickListener(this);
        add_reward.setOnClickListener(this);
        return_Integral.setOnClickListener(this);

        button_home.performClick();//主动调用button_home点击事件,进入时，显示home界面。

        initExpandView();
        addIntegral();

    }

    private void addIntegral() {
        mContext = MainActivity.this;
        integralListView = (ListView) findViewById(R.id.integral_ListView);
        integralData = new LinkedList<IntegralList>();
        integralData.add(new IntegralList("蝙蝠侠战车一辆", "原因", "200"));
        integralData.add(new IntegralList("蝙蝠侠战车一辆", "原因", "200"));
        integralData.add(new IntegralList("蝙蝠侠战车一辆", "原因", "200"));
        integralAdapter = new IntegralAdapter(integralData, mContext);
        integralListView.setAdapter(integralAdapter);
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
        dayTextView.setText(day + "日");
        monthTextView.setText(month + "月");
    }

    /**
     * 初始化系统习惯任务列表
     */
    private void initSystemTask() {
        List<TaskList> st = DBControl.createRealm(this).showSystemTask();
        int size = st.size();
        if (size == 0) {
            firstIn = true;
            DBControl.createRealm(this).addSystemTask(new SystemTask());
        } else {
            firstIn = false;
        }
    }


    /**
     * 初始化日历按钮折叠布局 状态为  折叠
     */
    public void initExpandView() {
        mExpandLayout = (ExpandLayout) findViewById(R.id.expandLayout);
        mExpandLayout.initExpand(false);//设置初始化状态，false折叠，true展开
        calendar_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandLayout.toggleExpand();
            }
        });
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_home:
                getSupportFragmentManager().beginTransaction().
                        hide(mPersonalFragment).
                        hide(mIntegralFragment).
                        hide(mAddRewardFragment).
                        show(mHomeFragment).commit();
                setButton(v);
                break;
            case R.id.button_pet:
                int integralAll = DBControl.createRealm(this).showTotalModify();
                Log.i(TAG, "onClick: integralAll = " + integralAll);
                myIntegralTextView.setText(integralAll + "");
                getSupportFragmentManager().beginTransaction().
                        hide(mHomeFragment).
                        hide(mAddRewardFragment).
                        hide(mPersonalFragment).
                        show(mIntegralFragment).commit();
                setButton(v);
                break;
            case R.id.button_personal:
                getSupportFragmentManager().beginTransaction().
                        hide(mHomeFragment).
                        hide(mIntegralFragment).
                        hide(mAddRewardFragment).
                        show(mPersonalFragment).commit();
                setButton(v);
                break;
            case R.id.add_reward:
                rewardNameEditText.setText("");
                getSupportFragmentManager().beginTransaction().
                        hide(mHomeFragment).
                        hide(mIntegralFragment).
                        hide(mPersonalFragment).
                        show(mAddRewardFragment).commit();
                break;
            case R.id.return_integral:
                getSupportFragmentManager().beginTransaction().
                        hide(mHomeFragment).
                        hide(mAddRewardFragment).
                        hide(mPersonalFragment).
                        show(mIntegralFragment).commit();
                break;
            case R.id.image_btn_save_integral:
                String name = rewardNameEditText.getText().toString();
                String why = rewardReasonEditText.getText().toString();
                int integral = numberPicker.getValue();
                if (isName(name)) {
                    if (why.equals(""))
                        why = "没有原因！";
                    DBControl.createRealm(this).addRewardList(name, why, integral);
                    getSupportFragmentManager().beginTransaction().
                            hide(mHomeFragment).
                            hide(mAddRewardFragment).
                            hide(mPersonalFragment).
                            show(mIntegralFragment).commit();
                }
                break;
            default:
                break;
        }
    }

    private boolean isName(String name) {
        if (name.equals("")) {
            Toast.makeText(this, "名称不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        List<RewardList> rewardList = DBControl.createRealm(this).showRewardList();
        for (RewardList r : rewardList) {
            if (r.getName().equals(name)) {
                Toast.makeText(this, "名字不能重复，请重新输入！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    /**
     * 判断导航栏按钮
     *
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
     * @param name        自定义习惯的名称
     * @param expectDay   需要坚持的天数
     * @param colorNumber 列表的颜色
     * @param clockTime   设置提醒时间
     */
    public void addCustomTask(String name, int expectDay, int colorNumber, String clockTime) {
        createRealm(this).addCustomTask(name, expectDay, colorNumber, clockTime);
    }

    /**
     * 查看自定义习惯列表
     */
    public void showCustomTask() {
        List<TaskList> customList = createRealm(this).showCustomTask();
        for (TaskList s : customList) {
            Log.i(TAG, "initView:  = " + s.getName());
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
        return myHabitTask;
    }

    private void addMyHabitTask(String data, String name, int modify, int expectDay, String clockTime, int colorNumber) {
        DBControl.createRealm(this).addMyHabitTask(data, name, modify, expectDay, clockTime, colorNumber);
    }

    @Override
    protected void onResume() {
        addHabit();
        super.onResume();
    }
}
