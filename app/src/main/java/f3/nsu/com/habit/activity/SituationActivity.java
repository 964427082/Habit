package f3.nsu.com.habit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import f3.nsu.com.habit.R;
import f3.nsu.com.habit.ui.Histogram;
import f3.nsu.com.habit.ui.NewMonthDateView;

/**
 * Created by zhy on 2017/7/12.
 */

public class SituationActivity extends Activity implements View.OnClickListener{
    private ImageButton return_imageButton;
    private Histogram histogram;
    NewMonthDateView newMonthDateView;

    private float[] habitData = new float[]{35,52,78,23};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.habit_item_situation);
        init();
    }

    private void init() {
        newMonthDateView = (NewMonthDateView) findViewById(R.id.newMonthDateView);
        histogram = (Histogram) findViewById(R.id.histogram);
        histogram.setHabitData(habitData);

        return_imageButton = (ImageButton) findViewById(R.id.return_imageButton);
        return_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SituationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        completeTag();
    }
    private void completeTag() {
        //添加指定日期做标记  10 12 15 16
        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(12);
        list.add(15);
        list.add(16);
        newMonthDateView.setDaysHasThingList(list);
    }
    @Override
    public void onClick(View v) {

    }
}
