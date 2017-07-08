package f3.nsu.com.habit.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyHabitTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;
import f3.nsu.com.habit.ui.HabitList;

import static android.content.ContentValues.TAG;

/**
 * 习惯item适配器
 * Created by zhy on 2017/7/3.
 */

public class HabitAdapter extends BaseAdapter {
    private LinkedList<HabitList> habitDate;
    private Context mContext;
    private Button completeButton;
    public HabitAdapter(LinkedList<HabitList> HabitDate,Context mContext){
        this.habitDate = HabitDate;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return habitDate.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.habit_listitem,parent,false);
        TextView habitText = (TextView) convertView.findViewById(R.id.habit_text);
        TextView timeText = (TextView) convertView.findViewById(R.id.time_text);
        TextView dayText = (TextView) convertView.findViewById(R.id.day_time);
        Button colorButton = (Button) convertView.findViewById(R.id.color_button);
        completeButton = (Button) convertView.findViewById(R.id.complete_button);

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeButton.setBackgroundResource(R.drawable.icon_right_selected);
            }
        });

        habitText.setText(habitDate.get(position).getHabitName());
        timeText.setText(habitDate.get(position).getHabitTime());
        dayText.setText(habitDate.get(position).getHabitDay());
        colorButton.setBackgroundResource(habitDate.get(position).getHabitButtonColor());
        completeButton.setBackgroundResource(habitDate.get(position).getCompleteButton());

        return convertView;
    }

}
