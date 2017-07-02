package f3.nsu.com.habit.acitvity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;

import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.IntegralDataBase;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskDataBase;
import f3.nsu.com.habit.fragment.HomeFragment;
import f3.nsu.com.habit.fragment.PersonalFragment;
import f3.nsu.com.habit.fragment.PetFragment;

import static f3.nsu.com.habit.RealmDataBase.DBControl.createRealm;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    HomeFragment mHomeFragment;
    PersonalFragment mPersonalFragment;
    PetFragment mPetFragment;
    private ImageButton button_home,button_pet,button_personal;
    private View currentButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(DBControl.createRealm(this).showTaskDataBase().size() == 0){
            DBControl.createRealm(this).addTaskDataBase(new TaskDataBase());
        }
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
    }

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
    public void setButton(View v) {
        if (currentButton != null && currentButton.getId() != v.getId()) {
            currentButton.setEnabled(true);
        }
        v.setEnabled(false);//setEnabled控制控件false不激活，不管什么属性都无效
        currentButton = v;
    }


    /**
     * 修改当天积分信息
     * @param data  当天的主键   日期
     * @param today  修改后的当天积分
     * @param week   星期几
     */
    public void todayIntegral(String data,int today,String week){
        IntegralDataBase idb = new IntegralDataBase();
        idb.setData(data);
        idb.setTodayIntegral(today);
        idb.setWeek(week);
        createRealm(this).addIDataBase(idb);
    }

    /**
     * 查看所有总积分
     * @return   总积分
     */
    public int showTotalModify(){
        int totalModify = 0;
        totalModify = DBControl.createRealm(this).sumTotalModify();
        return totalModify;
    }

    /**
     * 查询某天积分
     * @param data  当天的日期
     * @return   当天的积分
     */
    public int showAnyDayModify(String data){
        int anyDayModify = DBControl.createRealm(this).sumTodayModify(data);
        return anyDayModify;
    }
}
