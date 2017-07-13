package f3.nsu.com.habit.actvity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import f3.nsu.com.habit.R;
import f3.nsu.com.habit.ui.Histogram;

/**
 * Created by zhy on 2017/7/12.
 */

public class SituationActivity extends Activity implements View.OnClickListener{
    private ImageButton return_imageButton;
    private Histogram histogram;

    private int[] habitData = new int[]{75,65,100,48};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.habit_item_situation);
        init();
    }

    private void init() {
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

    }

    @Override
    public void onClick(View v) {

    }
}
