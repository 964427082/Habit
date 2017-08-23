package f3.nsu.com.habit.activity;import android.content.Context;import android.content.Intent;import android.content.SharedPreferences;import android.os.Build;import android.os.Bundle;import android.support.annotation.RequiresApi;import android.support.v4.app.FragmentActivity;import android.view.KeyEvent;import android.view.View;import android.widget.EditText;import android.widget.ImageButton;import android.widget.ListView;import android.widget.NumberPicker;import android.widget.TextView;import android.widget.Toast;import java.util.ArrayList;import java.util.LinkedList;import java.util.List;import f3.nsu.com.habit.Adapter.HabitAdapter;import f3.nsu.com.habit.Adapter.IntegralAdapter;import f3.nsu.com.habit.GetTime.GetTime;import f3.nsu.com.habit.R;import f3.nsu.com.habit.RealmDataBase.DBControl;import f3.nsu.com.habit.RealmDataBase.TaskData.MyHabitTask;import f3.nsu.com.habit.RealmDataBase.TaskData.MyHabitTaskIsOk;import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;import f3.nsu.com.habit.RealmDataBase.TaskData.RewardList;import f3.nsu.com.habit.RealmDataBase.TaskData.SystemTask;import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;import f3.nsu.com.habit.fragment.AddRewardFragment;import f3.nsu.com.habit.fragment.HomeFragment;import f3.nsu.com.habit.fragment.IntegralFragment;import f3.nsu.com.habit.fragment.PersonalFragment;import f3.nsu.com.habit.ui.ExpandLayout;import f3.nsu.com.habit.ui.HabitList;import f3.nsu.com.habit.ui.MonthDateView;import io.realm.Realm;import static f3.nsu.com.habit.RealmDataBase.DBControl.createRealm;/** * 主界面 */public class MainActivity extends FragmentActivity implements View.OnClickListener{    private static final String TAG = "MainActivity";    HomeFragment mHomeFragment;//主界面    PersonalFragment mPersonalFragment;//个人界面    IntegralFragment mIntegralFragment;//积分商店    AddRewardFragment mAddRewardFragment;//添加奖励    private ImageButton button_home, button_pet, button_personal;//底部导航栏按钮    private ImageButton calendar_ImageButton;//左上角日历按钮    private TextView dayTextView, monthTextView;//日历展开界面日期    private ImageButton insert_imageButton;//主界面  右上角添加习惯按钮    //个人中心      积分      完成习惯            坚持天数           总共完成次数    private TextView tvMyAllIntegral, tvMyOkTask, tvMyHoldDay, tvMyAllOkNumber;    private View currentButton;    private List<HabitList> habitDate = null;    private LinkedList<RewardList> integralData = null;    private Context mContext;    private HabitAdapter habitAdapter = null;    private IntegralAdapter integralAdapter = null;    private ListView habitListView;    private ExpandLayout mExpandLayout;    public Realm realm;    public static boolean firstIn = true;   //是否为第一次进入     去加载系统任务    private MonthDateView monthDateView;    private ListView integralListView;    private ImageButton add_reward, return_Integral, imageBtnSaveIntegral;    private EditText rewardNameEditText, rewardReasonEditText;    private NumberPicker numberPicker;    private TextView myIntegralTextView;    private String data = new GetTime().getData();    private int year = new GetTime().getYear();    private int month = new GetTime().getMonth();    private boolean isOnKeyDown = false;    @RequiresApi(api = Build.VERSION_CODES.N)    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        Realm.init(this);        realm = Realm.getDefaultInstance();        initSystemTask();       //初始化系统习惯任务列表        initConvertTask();      //初始化积分奖励兑换条目        initView();        firstRun();    }    /**     * 判断用户是否第一次使用     */    private void firstRun() {        SharedPreferences sharedPreferences = getSharedPreferences("FirstRun",0);        Boolean first_run = sharedPreferences.getBoolean("First",true);        if (first_run){            sharedPreferences.edit().putBoolean("First",false).commit();            startActivity(new Intent(MainActivity.this,FirstRunActivity.class));        }        else {            Toast.makeText(this,"不是第一次",Toast.LENGTH_LONG).show();        }    }    /**     * 添加习惯到列表     * 1.查找今天我的习惯     */    private void addHabit() {        MyHabitTask myHabitTask = DBControl.createRealm(this).showMyEndTaskList();        habitDate.clear();        if (myHabitTask != null) {            //查看数据库最后一天数据的日期是不是今天      如果不是  则复制前一次数据            if (!myHabitTask.getData().equals(data)) {                DBControl.createRealm(this).copyMyHabitTask(myHabitTask);                addHabit();            } else {                //如果当天有数据   就直接去加载当天的数据                List<MyIntegralList> myIntegralList = myHabitTask.getMyIntegralList();                for (MyIntegralList m : myIntegralList) {                    habitDate.add(new HabitList(m.getName(), m.getClockTime(), m.getInsistDay(), m.getExpectDay(), m.isStart(), m.getModify(), m.getColorNumber(), m.getIsClockTime(),m.getServiceNumber()));                }            }        }        habitAdapter = new HabitAdapter((LinkedList<HabitList>) habitDate, mContext);        habitListView.setAdapter(habitAdapter);//        habitListView.setOnItemClickListener(this);    }    /**     * 响应ListView中item的点击事件     *///    @Override//    public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {//        Intent intent = new Intent(MainActivity.this, SituationActivity.class);//        startActivity(intent);//    }    /**     * 初始化     */    @RequiresApi(api = Build.VERSION_CODES.N)    private void initView() {        mContext = MainActivity.this;        habitDate = new LinkedList<HabitList>();        //绑定控件        //主界面        mHomeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_home);        //积分商店        mIntegralFragment = (IntegralFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_pet);        //添加奖励        mAddRewardFragment = (AddRewardFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_add_reward);        //个人界面        mPersonalFragment = (PersonalFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_personal);        habitListView = (ListView) findViewById(R.id.habit_ListView);        monthDateView = (MonthDateView) findViewById(R.id.monthDateView);        dayTextView = (TextView) findViewById(R.id.day_textView);        monthTextView = (TextView) findViewById(R.id.month_textView);        //设置当天日期        setDay();        calendar_ImageButton = (ImageButton) findViewById(R.id.calendar_ImageButton);        insert_imageButton = (ImageButton) findViewById(R.id.insert_imageButton);        button_home = (ImageButton) findViewById(R.id.button_home);        button_personal = (ImageButton) findViewById(R.id.button_personal);        button_pet = (ImageButton) findViewById(R.id.button_pet);        add_reward = (ImageButton) findViewById(R.id.add_reward);        return_Integral = (ImageButton) findViewById(R.id.return_integral);        myIntegralTextView = (TextView) findViewById(R.id.my_integral_textView);        //积分奖励自定义界面        imageBtnSaveIntegral = (ImageButton) findViewById(R.id.image_btn_save_integral);        rewardNameEditText = (EditText) findViewById(R.id.rewardName_editText);        rewardReasonEditText = (EditText) findViewById(R.id.rewardReason_editText);        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);        //个人中心        tvMyAllIntegral = (TextView) findViewById(R.id.tv_my_allIntegral);        tvMyOkTask = (TextView) findViewById(R.id.tv_my_okTask);        tvMyHoldDay = (TextView) findViewById(R.id.tv_my_holdDay);        tvMyAllOkNumber = (TextView) findViewById(R.id.tv_my_allOkNumber);        imageBtnSaveIntegral.setOnClickListener(this);        button_home.setOnClickListener(this);        button_pet.setOnClickListener(this);        button_personal.setOnClickListener(this);        add_reward.setOnClickListener(this);        return_Integral.setOnClickListener(this);        insert_imageButton.setOnClickListener(this);        button_home.performClick();//主动调用button_home点击事件,进入时，显示home界面。        //初始化日历按钮折叠布局 状态为  折叠        initExpandView();    }    /**     * 总积分查询     * 需要减去积分兑换掉的     */    private int showIntegralAll() {        int i = DBControl.createRealm(this).showTotalModify() -                DBControl.createRealm(this).showConvertIntegralAll();        return i;    }    /**     * 积分的兑换选项     */    private void addIntegral() {        mContext = MainActivity.this;        integralListView = (ListView) findViewById(R.id.integral_ListView);        List<RewardList> rewardList = DBControl.createRealm(mContext).showRewardList();        integralData = new LinkedList<RewardList>();        integralData.addAll(rewardList);        integralAdapter = new IntegralAdapter(integralData, mContext);        integralAdapter.notifyDataSetChanged();        integralListView.setAdapter(integralAdapter);    }    /**     * 为完成的日期在日历中添加标记     * 条件：如果当天有累计积分   则视为当天完成了     */    public void completeTag() {        //为完成的日期添加完成标记        List<Integer> list = new ArrayList<Integer>();        List<String> l = DBControl.createRealm(this).showToMonthEverDayIntegral();        for (String i : l) {            int newDay = Integer.valueOf(i.substring(6, 8));            list.add(newDay);        }        monthDateView.setDaysHasThingList(list);    }    /**     * 设置当天日期     */    private void setDay() {        int day = new GetTime().getDay();        int month = new GetTime().getMonth();        dayTextView.setText(day + "日");        monthTextView.setText(month + "月");    }    /**     * 初始化系统习惯任务列表     */    private void initSystemTask() {        List<TaskList> st = DBControl.createRealm(this).showSystemTask();        int size = st.size();        if (size == 0) {            firstIn = true;            DBControl.createRealm(this).addSystemTask(new SystemTask());        } else {            firstIn = false;        }    }    /**     * 初始化积分兑换奖励设置  默认两条     */    private void initConvertTask() {        int i = DBControl.createRealm(this).showRewardList().size();        if (i == 0) {            DBControl.createRealm(this).addRewardList("放我去大吃一顿！", "吃是为了生存!", 249);        }    }    /**     * 初始化日历按钮折叠布局 状态为  折叠     */    public void initExpandView() {        mExpandLayout = (ExpandLayout) findViewById(R.id.expandLayout);        mExpandLayout.initExpand(false);//设置初始化状态，false折叠，true展开        calendar_ImageButton.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                mExpandLayout.toggleExpand();            }        });    }    /**     * 点击事件     *     * @param v     */    @Override    public void onClick(View v) {        switch (v.getId()) {            //主界面按钮            case R.id.button_home:                getSupportFragmentManager().beginTransaction().                        hide(mPersonalFragment).                        hide(mIntegralFragment).                        hide(mAddRewardFragment).                        show(mHomeFragment).commit();                setButton(v);                break;            //积分商店按钮            case R.id.button_pet:                addIntegral();                int integralAll = showIntegralAll();                myIntegralTextView.setText(integralAll + "");                getSupportFragmentManager().beginTransaction().                        hide(mHomeFragment).                        hide(mAddRewardFragment).                        hide(mPersonalFragment).                        show(mIntegralFragment).commit();                setButton(v);                break;            //个人中心按钮            case R.id.button_personal:                showMyInformation();                getSupportFragmentManager().beginTransaction().                        hide(mHomeFragment).                        hide(mIntegralFragment).                        hide(mAddRewardFragment).                        show(mPersonalFragment).commit();                setButton(v);                break;            //打开积分奖励设置            case R.id.add_reward:                rewardNameEditText.setText("");                rewardReasonEditText.setText("");                getSupportFragmentManager().beginTransaction().                        hide(mHomeFragment).                        hide(mIntegralFragment).                        hide(mPersonalFragment).                        show(mAddRewardFragment).commit();                break;            //添加积分奖励界面   返回按钮            case R.id.return_integral:                getSupportFragmentManager().beginTransaction().                        hide(mHomeFragment).                        hide(mAddRewardFragment).                        hide(mPersonalFragment).                        show(mIntegralFragment).commit();                break;            //添加积分奖励界面   保存按钮            case R.id.image_btn_save_integral:                String name = rewardNameEditText.getText().toString();                String why = rewardReasonEditText.getText().toString();                int[] integralNumbers = {500, 550, 600, 650, 700, 750, 800, 850, 900, 950, 1000};                int number = numberPicker.getValue();                int integral = integralNumbers[number];                if (isName(name)) {                    if (why.equals(""))                        why = "没有原因！";                    DBControl.createRealm(this).addRewardList(name, why, integral);                    getSupportFragmentManager().beginTransaction().                            hide(mHomeFragment).                            hide(mAddRewardFragment).                            hide(mPersonalFragment).                            show(mIntegralFragment).commit();                    addIntegral();                }                break;            //添加习惯按钮(+号)            case R.id.insert_imageButton:                Intent intent = new Intent(MainActivity.this, MyAddHabitActivity.class);                startActivity(intent);                break;            default:                break;        }    }    //个人中心数据    private void showMyInformation() {        List<MyHabitTaskIsOk> myHabitTaskIsOk = DBControl.createRealm(this).showOkTask();        int okTaskNumber = DBControl.createRealm(this).showTotalOkTaskNumber();        int myOkTask = myHabitTaskIsOk.size();        tvMyAllIntegral.setText(showIntegralAll() + "");        tvMyAllOkNumber.setText(okTaskNumber + "");//        tvMyHoldDay.setText();        tvMyOkTask.setText(myOkTask + "");    }    private boolean isName(String name) {        if (name.equals("")) {            Toast.makeText(this, "名称不能为空！", Toast.LENGTH_SHORT).show();            return false;        }        List<RewardList> rewardList = DBControl.createRealm(this).showRewardList();        for (RewardList r : rewardList) {            if (r.getName().equals(name)) {//                Toast toast = Toast.makeText(this,"自定义位置",Toast.LENGTH_SHORT);//                toast.setGravity(Gravity.CENTER,0,0);//                toast.show();                Toast.makeText(this, "名字不能重复，请重新输入！", Toast.LENGTH_SHORT).show();                return false;            }        }        return true;    }    /**     * 判断导航栏按钮     *     * @param v     */    public void setButton(View v) {        if (currentButton != null && currentButton.getId() != v.getId()) {            currentButton.setEnabled(true);        }        v.setEnabled(false);//setEnabled控制控件false不激活，不管什么属性都无效        currentButton = v;    }    /**     * 自定义添加习惯列表并保存     *     * @param name        自定义习惯的名称     * @param expectDay   需要坚持的天数     * @param colorNumber 列表的颜色     * @param clockTime   设置提醒时间     */    public void addCustomTask(String name, int expectDay, int colorNumber, String clockTime,int serviceNumber) {        createRealm(this).addCustomTask(name, expectDay, colorNumber, clockTime,serviceNumber);    }    /**     * 查看自定义习惯列表     */    public void showCustomTask() {        List<TaskList> customList = createRealm(this).showCustomTask();    }    /**     * 根据名字删除自定义习惯列表项     *     * @param name 键值     */    public void deleteCustomTask(String name) {        DBControl.createRealm(this).deleteCustomTask(name);    }    @Override    protected void onDestroy() {        super.onDestroy();        realm.close();    }    @Override    protected void onResume() {        addHabit();        //为完成的日期在日历中添加标记        completeTag();        super.onResume();    }    /**     * back   返回键执行home     */    @Override    public boolean onKeyDown(int keyCode, KeyEvent event) {//        if(!isOnKeyDown){//            isOnKeyDown = true;//            //需要设置Toast//            Toast myToast = new Toast(this);//            myToast.makeText(this,"再次点击即可退出程序！",Toast.LENGTH_SHORT).show();//            new Thread(){//                @Override//                public void run(){//                    try {//                        Log.i(TAG, "run: 延时两秒");//                        sleep(2000);//                        isOnKeyDown = false;//                    } catch (InterruptedException e) {//                        e.printStackTrace();//                    }//                }//            }.start();//            return false;//        }else {//            if (keyCode == KeyEvent.KEYCODE_BACK) {//                Intent intent = new Intent(Intent.ACTION_MAIN);//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//                intent.addCategory(Intent.CATEGORY_HOME);//                startActivity(intent);//            }//        }        if (keyCode == KeyEvent.KEYCODE_BACK) {            Intent intent = new Intent(Intent.ACTION_MAIN);            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);            intent.addCategory(Intent.CATEGORY_HOME);            startActivity(intent);        }        return super.onKeyDown(keyCode, event);    }}