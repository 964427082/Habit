package f3.nsu.com.habit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;

import f3.nsu.com.habit.R;
import f3.nsu.com.habit.ui.HabitList;

/**
 * 习惯item适配器
 * Created by zhy on 2017/7/3.
 */

public class HabitAdapter extends BaseAdapter {
    private LinkedList<HabitList> habitDate;
    private Context mContext;
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
        Button completeButton = (Button) convertView.findViewById(R.id.complete_button);
        habitText.setText(habitDate.get(position).getHabitName());
        timeText.setText(habitDate.get(position).getHabitTime());
        dayText.setText(habitDate.get(position).getHabitDay());
        colorButton.setBackgroundResource(habitDate.get(position).getHabitButtonColor());
        completeButton.setBackgroundResource(habitDate.get(position).getCompleteButton());

        return convertView;
    }
}
