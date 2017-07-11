package f3.nsu.com.habit.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;
import f3.nsu.com.habit.ui.HabitList;

/**
 * 习惯item适配器
 * Created by zhy on 2017/7/3.
 */

public class HabitAdapter extends BaseAdapter {
    private static final String TAG = "HabitAdapter";
    private LinkedList<HabitList> habitDate;
    private Context mContext;
    private boolean[] isCheck;

    public HabitAdapter(LinkedList<HabitList> habitDate, Context mContext) {
        this.habitDate = habitDate;
        this.mContext = mContext;
        isCheck = new boolean[habitDate.size()];
        for (int i = 0; i < habitDate.size() - 1; i++) {
            isCheck[i] = false;
        }
    }

    @Override
    public int getCount() {
        return habitDate.size();
    }

    @Override
    public Object getItem(int position) {
        return habitDate.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.habit_listitem, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.habitText = (TextView) convertView.findViewById(R.id.habit_text);
            viewHolder.timeText = (TextView) convertView.findViewById(R.id.time_text);
            viewHolder.dayText = (TextView) convertView.findViewById(R.id.day_time);
            viewHolder.completeButton = (Button) convertView.findViewById(R.id.complete_button);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            viewHolder.colorButton = (Button) convertView.findViewById(R.id.color_button);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (habitDate.get(position).getColorNumber()) {
            case 1:
                viewHolder.colorButton.setBackgroundResource(R.drawable.round_button_color1);
                break;
            case 2:
                viewHolder.colorButton.setBackgroundResource(R.drawable.round_button_color2);
                break;
            case 3:
                viewHolder.colorButton.setBackgroundResource(R.drawable.round_button_color3);
                break;
            case 4:
                viewHolder.colorButton.setBackgroundResource(R.drawable.round_button_color4);
                break;
            default:
                break;
        }
        String date = new GetTime().getData();
        String name = habitDate.get(position).getHabitName();
        if (habitDate.get(position).getComplete() || isCheck[position]) {
            viewHolder.completeButton.setBackgroundResource(R.drawable.icon_right_selected);
        } else {
            viewHolder.completeButton.setOnClickListener(new ListButtonListener(position, mContext, viewHolder.completeButton, date, name, viewHolder.dayText));
            viewHolder.completeButton.setBackgroundResource(R.drawable.icon_right_default);
        }
        habitDate.get(position).getColorNumber();
        viewHolder.progressBar.setProgress(habitDate.get(position).getGoalDay());
        viewHolder.progressBar.setMax(habitDate.get(position).getCompleteDay());
        viewHolder.habitText.setText(habitDate.get(position).getHabitName());
        viewHolder.timeText.setText(habitDate.get(position).getHabitTime());
        viewHolder.dayText.setText(habitDate.get(position).getGoalDay() + "天/" + habitDate.get(position).getCompleteDay() + "天");
        return convertView;
    }

    class ViewHolder {
        private TextView habitText;
        private TextView timeText;
        private TextView dayText;
        private Button completeButton;
        private ProgressBar progressBar;
        private Button colorButton;
    }

    public class ListButtonListener implements View.OnClickListener {
        TextView dayText;
        int mPosition;
        Context mContext;

        Button button1;
        String name;
        String date;


        public ListButtonListener(int position, Context context, Button button, String date, String name, TextView dayText) {
            this.mPosition = position;
            this.mContext = context;
            this.button1 = button;
            this.date = date;
            this.name = name;
            this.dayText = dayText;
        }

        @Override
        public void onClick(View v) {
            List<MyIntegralList> myIntegralList = DBControl.createRealm(mContext).amendProgress(date);
            boolean is = false;
            for (MyIntegralList m : myIntegralList) {
                if (m.getName().equals(name)) {
                    is = m.isStart();
                }
            }
            if (!is) {
                Toast.makeText(mContext, "您完成了此项任务！", Toast.LENGTH_LONG).show();
                habitDate.get(mPosition).setGoalDay(habitDate.get(mPosition).getGoalDay() + 1);
                DBControl.createRealm(mContext).amendMyHabitIsStart(date, name);
            }
            isCheck[mPosition] = true;
            notifyDataSetChanged();
        }
    }
}
