package f3.nsu.com.habit.acitvity;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import f3.nsu.com.habit.Adapter.HabitAdapter;
import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.fragment.HomeFragment;
import f3.nsu.com.habit.fragment.PersonalFragment;
import f3.nsu.com.habit.fragment.PetFragment;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.ui.ExpandLayout;
import f3.nsu.com.habit.ui.HabitList;

/**
 * 主界面
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    HomeFragment mHomeFragment;
    PersonalFragment mPersonalFragment;
    PetFragment mPetFragment;
    private ImageButton button_home,button_pet,button_personal;
    private ImageButton calendar_ImageButton;
    private View currentButton;
    private List<HabitList> habitDate = null;
    private Context mContext;
    private HabitAdapter habitAdapter = null;
    private ListView habitListView;
    private ExpandLayout mExpandLayout;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 添加习惯
     */
    private void addHabit() {
        mContext = MainActivity.this;
        habitListView = (ListView) findViewById(R.id.habit_ListView);
        habitDate = new LinkedList<HabitList>();
        habitDate.add(new HabitList("早起","13:00","21/45天",R.drawable.round_button_color1,R.drawable.icon_right_default));
        habitDate.add(new HabitList("记单词","14:00","15/30天",R.drawable.round_button_color2,R.drawable.icon_right_selected));
        habitDate.add(new HabitList("喝水","15:00","17/20天",R.drawable.round_button_color3,R.drawable.icon_right_default));
        habitDate.add(new HabitList("早睡","10:00","12/30天",R.drawable.round_button_color2,R.drawable.icon_right_default));
        habitDate.add(new HabitList("装逼","15:30","50/50天",R.drawable.round_button_color1,R.drawable.icon_right_selected));
        habitDate.add(new HabitList("撩妹","0:00","50/50天",R.drawable.round_button_color2,R.drawable.icon_right_selected));
        habitDate.add(new HabitList("去召唤师峡谷","0:00","50/50天",R.drawable.round_button_color2,R.drawable.icon_right_default));
        habitDate.add(new HabitList("喝水","15:00","17/20天",R.drawable.round_button_color3,R.drawable.icon_right_selected));
        habitDate.add(new HabitList("装逼","15:30","50/50天",R.drawable.round_button_color1,R.drawable.icon_right_default));
        habitDate.add(new HabitList("早起","13:00","21/45天",R.drawable.round_button_color1,R.drawable.icon_right_default));
        habitAdapter = new HabitAdapter((LinkedList<HabitList>)habitDate,mContext);

        habitListView.setAdapter(habitAdapter);
        habitListView.setOnItemClickListener(this);

    }

    /**
     * list点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void onItemClick(AdapterView<?> parent, View view ,int position,long id){
        Toast.makeText(mContext,"你点击了第"+position + "项",Toast.LENGTH_LONG).show();
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

        addHabit();
        calendar_ImageButton = (ImageButton) findViewById(R.id.calendar_ImageButton);
        button_home = (ImageButton) findViewById(R.id.button_home);
        button_personal = (ImageButton) findViewById(R.id.button_personal);
        button_pet = (ImageButton) findViewById(R.id.button_pet);
        button_home.setOnClickListener(this);
        button_pet.setOnClickListener(this);
        button_personal.setOnClickListener(this);
        button_home.performClick();//主动调用button_home点击事件,进入时，显示home界面。

        initExpandView();
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
}
