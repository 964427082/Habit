package f3.nsu.com.habit.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import f3.nsu.com.habit.R;
import f3.nsu.com.habit.fragment.AddHabitFragment;
import f3.nsu.com.habit.fragment.NewHabitFragment;

/**
 * Created by zhy on 2017/7/11.
 */

public class AddHabitActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "AddHabitActivity";
    private Button createHabit_button;//创建习惯按钮
    private ImageButton exit_imageButton;//添加习惯界面退出按钮
    private ImageButton return_imageButton;//新习惯界面返回到添加习惯界面按钮
    NewHabitFragment newHabitFragment;//新习惯界面
    AddHabitFragment addHabitFragment;//添加习惯界面
    private EditText nameEditText,dayEditText,wordsEditText;//新习惯界面名称、天数、语录的编辑框
    private Button color_button1,color_button2,color_button3,color_button4,color_button5;//五个颜色选择按钮
    private RadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5;//五个颜色单选按钮

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
        createHabit_button = (Button) findViewById(R.id.createHabit_button);
        exit_imageButton = (ImageButton) findViewById(R.id.exit_imageButton);
        return_imageButton = (ImageButton) findViewById(R.id.return_imageButton);

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


        nameEditText  = (EditText) findViewById(R.id.name_editText);
        dayEditText = (EditText) findViewById(R.id.day_editText);
        wordsEditText = (EditText) findViewById(R.id.words_editText);
        newHabitFragment = (NewHabitFragment) getSupportFragmentManager().findFragmentById(R.id.new_habit_fragment);
        addHabitFragment = (AddHabitFragment) getSupportFragmentManager().findFragmentById(R.id.add_habit_fragment);
        createHabit_button.setOnClickListener(this);

        return_imageButton.setOnClickListener(this);

        exit_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddHabitActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //设置默认点击事件，一进入AddHabitActivity就显示添加习惯界面。
        return_imageButton.performClick();
        //设置编辑框限定字符数的监听。
        nameEditText.addTextChangedListener(nameTextWatcher);
        dayEditText.addTextChangedListener(dayTextWatcher);
        wordsEditText.addTextChangedListener(wordsTextWatcher);
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createHabit_button:
                getSupportFragmentManager().beginTransaction().hide(addHabitFragment).show(newHabitFragment).commit();
                break;
            case R.id.return_imageButton:
                getSupportFragmentManager().beginTransaction().hide(newHabitFragment).show(addHabitFragment).commit();
            case R.id.color_button1:
                radioButton1.setChecked(true);
                break;
            case R.id.color_button2:
                radioButton2.setChecked(true);
                break;
            case R.id.color_button3:
                radioButton3.setChecked(true);
                break;
            case R.id.color_button4:
                radioButton4.setChecked(true);
                break;
            case R.id.color_button5:
                radioButton5.setChecked(true);
                break;
            default:
                break;
        }
    }
    /**
     * 为nameEditText添加监听，超过限定字符数，弹出提示。
     */
    private TextWatcher nameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int len = s.toString().length();
            if (len>=10){
                Toast.makeText(getApplicationContext(),"习惯名称最多10个字！",Toast.LENGTH_LONG).show();
            }
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
            if (len>=3){
                Toast.makeText(getApplicationContext(),"目标天数最多3位数！",Toast.LENGTH_LONG).show();
            }
        }
    };
    /**
     * 为wordsEditText添加监听，超过限定字符数，弹出提示。
     */
    private TextWatcher wordsTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int len = s.toString().length();
            if (len>=25){
                Toast.makeText(getApplicationContext(),"励志语录不能超过25个字！",Toast.LENGTH_LONG).show();
            }
        }
    };
}
