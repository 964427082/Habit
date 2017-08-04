package f3.nsu.com.habit.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.ui.Histogram;
import f3.nsu.com.habit.ui.NewMonthDateView;

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

    private float[] habitData = new float[]{35, 52, 78, 23};
    private int month = new GetTime().getMonth();
    private int number = 1;
    private String  name;
    private Context context = this;
    private List<Integer> list = new ArrayList<>();

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
        name = intent.getAction();
        number = intent.getFlags();
        setColor(number);
        completeTag(name);
        int datas[] = DBControl.createRealm(context).showHistory(name,1);
        int continuousHoldNumber = DBControl.createRealm(this).showContinuousHoldNumber(name);
        listViewMonthCalendar.setText(month + "月习惯日历");
        listViewHabitName.setText(name);
        listViewMonthHoldNumber.setText(datas[0] + "");                  //本月坚持次数
        listViewContinuousHoldNumber.setText(continuousHoldNumber + "");    //最佳连续坚持次数
        listViewHistoryHoldNumber.setText(datas[2] + "");      //历史坚持次数


        //设置柱状图的值
        listViewHistogram.setHabitData(habitData);
    }

    private void setColor(int number) {
        switch (number){
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
                break;
        }
    }
}
