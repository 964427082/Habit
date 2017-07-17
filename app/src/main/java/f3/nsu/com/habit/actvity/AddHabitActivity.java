package f3.nsu.com.habit.actvity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;
import f3.nsu.com.habit.fragment.NewHabitFragment;

/**
 * Created by zhy on 2017/7/11.
 */

public class AddHabitActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "AddHabitActivity";
    NewHabitFragment newHabitFragment;//新习惯界面
    private EditText nameEditText, dayEditText;//新习惯界面名称、天数、语录的编辑框
    private Button color_button1, color_button2, color_button3, color_button4, color_button5;//五个颜色选择按钮
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5;//五个颜色单选按钮
    private ImageButton returnImgBtn, completeImgBtn;
    private Context context = this;
    private List<TaskList> systemList = DBControl.createRealm(this).showSystemTask();
    private List<TaskList> customList = DBControl.createRealm(this).showCustomTask();
    private int colorNumber = 1;
    private boolean is = false;
    private String data = new GetTime().getData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_habit_activity);
        init();
    }

    /**
     * 初始化界面
     */
    private void init() {

        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
        radioButton5 = (RadioButton) findViewById(R.id.radioButton5);
        color_button1 = (Button) findViewById(R.id.color_button1);
        color_button2 = (Button) findViewById(R.id.color_button2);
        color_button3 = (Button) findViewById(R.id.color_button3);
        color_button4 = (Button) findViewById(R.id.color_button4);
        color_button5 = (Button) findViewById(R.id.color_button5);
        color_button1.setOnClickListener(this);
        color_button2.setOnClickListener(this);
        color_button3.setOnClickListener(this);
        color_button4.setOnClickListener(this);
        color_button5.setOnClickListener(this);


        nameEditText = (EditText) findViewById(R.id.name_editText);
        dayEditText = (EditText) findViewById(R.id.day_editText);
        newHabitFragment = (NewHabitFragment) getSupportFragmentManager().findFragmentById(R.id.new_habit_fragment);
        returnImgBtn = (ImageButton) newHabitFragment.getActivity().findViewById(R.id.return_imageButton);
        completeImgBtn = (ImageButton) newHabitFragment.getActivity().findViewById(R.id.complete_img_btn);
        returnImgBtn.setOnClickListener(this);
        completeImgBtn.setOnClickListener(this);
        radioButton1.setChecked(true);


        //设置编辑框限定字符数的监听。
        new Thread(new Runnable() {
            @Override
            public void run() {
                nameEditText.addTextChangedListener(nameTextWatcher);
                dayEditText.addTextChangedListener(dayTextWatcher);
            }
        }).start();
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.color_button1:
                radioButton1.setChecked(true);
                colorNumber = 1;
                break;
            case R.id.color_button2:
                radioButton2.setChecked(true);
                colorNumber = 2;
                break;
            case R.id.color_button3:
                radioButton3.setChecked(true);
                colorNumber = 3;
                break;
            case R.id.color_button4:
                radioButton4.setChecked(true);
                colorNumber = 4;
                break;
            case R.id.color_button5:
                radioButton5.setChecked(true);
                colorNumber = 5;
                break;
            //返回
            case R.id.return_imageButton:
                finish();
                break;
            //完成
            case R.id.complete_img_btn:
                is = isComplete();
                if (is == true) {
                    DBControl.createRealm(context).addCustomTask(nameEditText.getText().toString(),
                            Integer.valueOf(dayEditText.getText().toString()), colorNumber, "12:00");
                    startActivity(new Intent(AddHabitActivity.this, MyAddHabitActivity.class));
                    finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 为nameEditText添加监听，超过限定字符数，弹出提示。
     */
    private TextWatcher nameTextWatcher = new TextWatcher() {
        boolean isSystem = false;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int len = s.toString().length();
            if (len >= 10) {
                Toast.makeText(context, "习惯名称最多10个字！", Toast.LENGTH_SHORT).show();
            }
            Log.i(TAG, "afterTextChanged: 1111");
            for (TaskList system : systemList) {
                if (s.toString().equals(system.getName())) {
                    Toast.makeText(context, "该习惯已经存在，请重新输入！", Toast.LENGTH_SHORT).show();
                    is = false;
                    isSystem = true;
                    break;
                } else
                    is = true;
            }

            if (!isSystem) {
                Log.i(TAG, "afterTextChanged: 2222");
                for (TaskList custom : customList) {
                    if (s.toString().equals(custom.getName())) {
                        Toast.makeText(context, "该习惯已经存在，请重新输入！", Toast.LENGTH_SHORT).show();
                        is = false;
                        break;
                    } else
                        is = true;
                }
            }
            isSystem = false;
        }
    };
    /**
     * 为dayEditText添加监听，超过限定字符数，弹出提示。
     */
    private TextWatcher dayTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            int len = s.toString().length();
            if (len >= 3) {
                Toast.makeText(context, "目标天数最多3位数！", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 禁用back键
     */
//    @Override
//    public boolean dispatchKeyEvent(android.view.KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
//            return false;
//        return true;
//    }
    public boolean isComplete() {
        if (nameEditText.getText().toString().equals("")) {
            Toast.makeText(context, "习惯名字不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dayEditText.getText().toString().equals("")) {
            Toast.makeText(context, "坚持天数不能为空！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
