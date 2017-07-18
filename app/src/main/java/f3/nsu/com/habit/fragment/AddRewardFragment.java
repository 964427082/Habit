package f3.nsu.com.habit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import butterknife.Unbinder;
import f3.nsu.com.habit.R;

/**
 * Created by zhy on 2017/7/14.
 */

public class AddRewardFragment extends Fragment implements NumberPicker.OnValueChangeListener {

    private static final String TAG = "AddRewardFragment";
    Unbinder unbinder;
    private String[] integral = {"500", "550", "600", "650", "700", "750", "800", "850", "900", "950", "1000"};

    private NumberPicker numberPicker;//积分选择器
    private EditText rewardName_editText;//奖励名称
    private EditText rewardReason_editText;//奖励原因

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_reward, null);
        numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        rewardName_editText = (EditText) view.findViewById(R.id.rewardName_editText);
        rewardReason_editText = (EditText) view.findViewById(R.id.rewardReason_editText);
        rewardName_editText.addTextChangedListener(nameTextWatcher);
        rewardReason_editText.addTextChangedListener(reasonTextWatcher);
        init();
        return view;
    }

    private void init() {
        numberPicker.setDisplayedValues(integral);
        numberPicker.setOnValueChangedListener(this);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(integral.length - 1);
        numberPicker.setValue(5);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setWrapSelectorWheel(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//        Toast.makeText(getActivity(),"您选择的积分是" + integral[newVal],Toast.LENGTH_LONG).show();
    }

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
            if (len >= 10) {
                Toast.makeText(getActivity(), "奖励名称最多10个字！", Toast.LENGTH_LONG).show();
            }
        }
    };
    private TextWatcher reasonTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int len = s.toString().length();
            if (len >= 12) {
                Toast.makeText(getActivity(), "奖励原因最多12个字！", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
