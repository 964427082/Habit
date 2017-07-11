package f3.nsu.com.habit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import f3.nsu.com.habit.R;

/**
 * 新习惯Fragment
 * Created by zhy on 2017/7/11.
 */

public class NewHabitFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_habit,null);
    }
}
