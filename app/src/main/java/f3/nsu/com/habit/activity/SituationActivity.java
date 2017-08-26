package f3.nsu.com.habit.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyHabitTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;
import f3.nsu.com.habit.tool.StartOrStopService;
import f3.nsu.com.habit.ui.CountClockTime;
import f3.nsu.com.habit.ui.Histogram;
import f3.nsu.com.habit.ui.NewMonthDateView;
import io.realm.RealmResults;

/**
 * Created by zhy on 2017/7/12.
 */

//主界面ListView 点击后界面
public class SituationActivity extends Activity {
    private static final String TAG = "SituationActivity";
    @BindView(R.id.list_view_habit_name)
    TextView listViewHabitName; //习惯名字
    @BindView(R.id.list_view_image_edit)
    RelativeLayout listViewImageEdit;   // 编辑按钮
    @BindView(R.id.list_view_month_hold_number)
    TextView listViewMonthHoldNumber;   //本月坚持次数
    @BindView(R.id.list_view_continuous_hold_number)
    TextView listViewContinuousHoldNumber;  //最佳连续坚持次数
    @BindView(R.id.list_view_history_hold_number)
    TextView listViewHistoryHoldNumber;     //历史坚持次数
    @BindView(R.id.list_view_month_calendar)
    TextView listViewMonthCalendar;     //该月习惯日历月份
    @BindView(R.id.newMonthDateView)
    NewMonthDateView newMonthDateView;      //习惯日历内容
    @BindView(R.id.list_view_histogram)
    Histogram listViewHistogram;        //柱状图
    @BindView(R.id.list_view_color_button)
    Button listViewColorButton;

    private int month = new GetTime().getMonth();
    private int year = new GetTime().getYear();
    private int number = 1;    //颜色序号
    private String name;    //习惯名字
    private String clockTime;    //闹钟时间
    private int serviceNumber;      //服务序号
    private boolean isClockTime = false;    //是否开启闹钟
    public Context context = this;
    private List<Integer> list = new ArrayList<>();
    private PopupWindow mPopupWindow;
    private int oneNumber = 0, towNumber = 0, threeNumber = 0, fourNumber = 0;
    private AlertDialog.Builder builder = null;
    private AlertDialog alert = null;
    private View builderView;
    private Button cancelButton, completeButton;
    private TextView newHabitName;      //修改习惯名字
    private Switch clockSwitch;
    private boolean isStartService = false; //是否发出通知  仅在当前有效
    private boolean isOnClick = false;      //是否点击了闹钟开关

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.habit_item_situation);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        Bundle bu = intent.getExtras();
        name = bu.getString("name");
        number = bu.getInt("colorNumber");
        isClockTime = bu.getBoolean("isClockTime");
        clockTime = bu.getString("clockTime");
        serviceNumber = bu.getInt("serviceNumber");
        setColor(number);
        completeTag(name);
        int datas[] = DBControl.createRealm(context).showHistory(name, 1);
        int continuousHoldNumber = DBControl.createRealm(this).showContinuousHoldNumber(name);
        listViewMonthCalendar.setText(month + "月习惯日历");
        listViewHabitName.setText(name);
        listViewMonthHoldNumber.setText(datas[0] + "");                  //本月坚持次数
        listViewContinuousHoldNumber.setText(continuousHoldNumber + "");    //最佳连续坚持次数
        listViewHistoryHoldNumber.setText(datas[2] + "");      //历史坚持次数

        //设置柱状图的值
        listViewHistogram.setHabitData(showToMonthNumbers(name));
        listViewHistogram.setOnChartClickListener(new Histogram.OnChartClickListener() {
            @Override
            public void onClick(int num, float x, float y, float value) {
                //显示提示窗
                View inflate = View.inflate(SituationActivity.this, R.layout.popupwindow, null);
                TextView text = (TextView) inflate.findViewById(R.id.popup_window_text);

                text.setText(value + "%\n" + showNumber(num) + "次");
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
                mPopupWindow = new PopupWindow(inflate, 150, 100, true);
                mPopupWindow.setTouchable(true);

                mPopupWindow.showAsDropDown(listViewHistogram, (int) (x - 240),
                        (int) ((-listViewHistogram.getHeight()) + y - 105));
//                mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.card_2_bg));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPopupWindow.dismiss();
                    }
                }, 3000);
            }
        });
    }

    //返回该周完成次数
    private int showNumber(int num) {
        switch (num) {
            case 1:
                return oneNumber;
            case 2:
                return towNumber;
            case 3:
                return threeNumber;
            case 4:
                return fourNumber;
            default:
                return 0;
        }
    }

    //查询柱状图的值
    private float[] showToMonthNumbers(String name) {
        RealmResults<MyHabitTask> myHabitTasks = DBControl.createRealm(context).showToMonth();
        for (MyHabitTask mt : myHabitTasks) {
            List<MyIntegralList> myIntegralLists = mt.getMyIntegralList();
            for (MyIntegralList ml : myIntegralLists) {
                if (ml.getName().equals(name)) {
                    if (ml.isStart()) {
                        int day = Integer.valueOf(mt.getData().substring(6, 8));
                        if (day <= 7)
                            oneNumber++;
                        else if (day <= 14)
                            towNumber++;
                        else if (day <= 21)
                            threeNumber++;
                        else
                            fourNumber++;
                    }
                }
            }
        }
        double cardinal = 0;
        if (month == 2) {
            if (((year % 4) == 0 && (year % 100 != 0)) || (year / 400 == 0))
                cardinal = 8;
            else
                cardinal = 7;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            cardinal = 9;
        } else
            cardinal = 10;

        int intFour = (int) (fourNumber / cardinal * 1000);
        int number1 = intFour / 10;
        int small = intFour % 10;
        if (small > 3) {
            number1 = number1 + 1;
        }
        float[] habitData = {returnNumber(oneNumber), returnNumber(towNumber),
                returnNumber(threeNumber), number1};
        return habitData;
    }

    private float returnNumber(int oneNumber) {
        switch (oneNumber) {
            case 1: {
                return (float) 14.29;

            }
            case 2: {
                return (float) 28.57;
            }
            case 3: {
                return (float) 42.86;
            }
            case 4: {
                return (float) 57.14;
            }
            case 5: {
                return (float) 71.43;
            }
            case 6: {
                return (float) 85.71;
            }
            case 7: {
                return 100;
            }
            default: {
                return 0;
            }
        }
    }

    private void setColor(int number) {
        switch (number) {
            case 1:
                listViewColorButton.setBackgroundResource(R.drawable.round_button_color1);
                break;
            case 2:
                listViewColorButton.setBackgroundResource(R.drawable.round_button_color2);
                break;
            case 3:
                listViewColorButton.setBackgroundResource(R.drawable.round_button_color3);
                break;
            case 4:
                listViewColorButton.setBackgroundResource(R.drawable.round_button_color4);
                break;
            case 5:
                listViewColorButton.setBackgroundResource(R.drawable.round_button_color5);
                break;
            default:
                break;
        }
    }

    //标记的是该习惯在本月的完成日期
    private void completeTag(String name) {
        list = DBControl.createRealm(context).showToMonthHoldNumber(name);
        newMonthDateView.setDaysHasThingList(list);
    }

    @OnClick({R.id.list_view_return_imageButton, R.id.list_view_image_edit})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            //返回按钮
            case R.id.list_view_return_imageButton:
                finish();
                break;
            //编辑按钮
            case R.id.list_view_image_edit:
                showEditDialog();
                alert.show();
                break;
            default:
                break;
        }
    }


    //对某个习惯的编辑操作
    private void showEditDialog() {
        builder = new AlertDialog.Builder(context);
        final LayoutInflater inflater = SituationActivity.this.getLayoutInflater();
        builderView = inflater.inflate(R.layout.alertdialog_edit, null);
        builder.setView(builderView);
        builder.setCancelable(false);
        alert = builder.create();
        newHabitName = (TextView) builderView.findViewById(R.id.new_habit_name_text_view);
        cancelButton = (Button) builderView.findViewById(R.id.cancel_button);
        completeButton = (Button) builderView.findViewById(R.id.complete_button);
        clockSwitch = (Switch) builderView.findViewById(R.id.clock_switch);
        newHabitName.setText(name);
        if (isClockTime)
            clockSwitch.setChecked(true);
        clockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG, "onCheckedChanged: 点击闹钟开关");
                isOnClick = true;
                if (isChecked) {
                    isStartService = true;
                    isClockTime = true;
                } else {
                    isStartService = false;
                    isClockTime = false;
                }
            }
        });
        //取消逻辑
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        //保存逻辑
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnClick) {  //点击了闹钟开关   才有无通知的说法
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    if (isStartService) {
                        //计算预计提醒时间
                        //判断名字是否重复
                        int hour = Integer.parseInt(clockTime.substring(0, 2));
                        int minuet = Integer.parseInt(clockTime.substring(3, 5));
                        if (hour == 0)
                            hour = 24;
                        int everTime = new CountClockTime(hour, minuet).getTime();
                        int newHour = everTime / 60;
                        int newMinuet = everTime % 60;
                        if (newHour == 0) {
                            if (newMinuet == 1) {
                                Toast.makeText(context, "闹钟不到一分钟后提醒", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(context, "闹钟" + newMinuet + "分钟后提醒", Toast.LENGTH_LONG).show();
                        } else {
                            if (newMinuet == 0)
                                Toast.makeText(context, "闹钟" + newHour + "小时后提醒", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(context, "闹钟" + newHour + "小时" + newMinuet + "分钟后提醒", Toast.LENGTH_LONG).show();
                        }
                        new StartOrStopService(alarmManager).isStartService(name,clockTime,serviceNumber
                        ,true,getApplicationContext(),isClockTime);
                    } else {
                        new StartOrStopService(alarmManager).isStartService(name,clockTime,serviceNumber
                                ,false,getApplicationContext(),isClockTime);
                    }
                    DBControl.createRealm(context).amendMyHabitIsStartService(name, isClockTime);
                }
                alert.dismiss();
            }
        });
    }
}
