package f3.nsu.com.habit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import f3.nsu.com.habit.R;

/**
 * 宠物界面fragment
 * Created by zhy on 2017/6/29.
 */

public class IntegralFragment extends Fragment {
    private TextView myIntegralTextView;        //积分商城里面的积分
    private static final String TAG = "IntegralFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_integral,null);
        myIntegralTextView = (TextView) view.findViewById(R.id.my_integral_textView);

        return view;
//        return inflater.inflate(R.layout.fragment_integral,null);
    }
//
//    public void onEventMainThread(int i){
//        Log.i(TAG, "onEvent: 更改");
//        myIntegralTextView.setText(i + "");
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//        Log.i(TAG, "onStart: 绑定");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//        Log.i(TAG, "onStop: 解除");
//    }
}
