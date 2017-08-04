package f3.nsu.com.habit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import f3.nsu.com.habit.R;

/**
 * 个人界面fragment
 * Created by zhy on 2017/6/29.
 */

public class PersonalFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_personal,null);
    }

//    private TextView tvMyAllIntegral,tvMyOkTask,tvMyHoldDay,tvMyAllOkNumber;
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        setContentView(R.layout.fragment_personal);
//        init();
//    }
//
//    private void init() {
//        tvMyAllIntegral = (TextView) findViewById(R.id.tv_my_allIntegral);
//        tvMyOkTask = (TextView) findViewById(R.id.tv_my_okTask);
//        tvMyHoldDay = (TextView) findViewById(R.id.tv_my_holdDay);
//        tvMyAllOkNumber = (TextView) findViewById(R.id.tv_my_allOkNumber);
//    }

}
