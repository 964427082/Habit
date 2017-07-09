package f3.nsu.com.habit.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyHabitTask;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;
import f3.nsu.com.habit.acitvity.MainActivity;
import f3.nsu.com.habit.ui.DrawCircle;
import f3.nsu.com.habit.ui.HabitList;

import static android.content.ContentValues.TAG;

/**
 * 习惯item适配器
 * Created by zhy on 2017/7/3.
 */

public class HabitAdapter extends BaseAdapter {
    private LinkedList<HabitList> habitDate;
    private Context mContext;
    private Map<Integer, View> map = new HashMap<Integer, View>();

    public HabitAdapter(LinkedList<HabitList> HabitDate, Context mContext) {
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
        if (map.size() < habitDate.size()) {
                if (!map.containsKey(position)) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.habit_listitem, parent, false);
                viewHolder viewHolder = new viewHolder();
                viewHolder.habitText = (TextView) convertView.findViewById(R.id.habit_text);
                viewHolder.timeText = (TextView) convertView.findViewById(R.id.time_text);
                viewHolder.dayText = (TextView) convertView.findViewById(R.id.day_time);
                viewHolder.completeButton = (Button) convertView.findViewById(R.id.complete_button);

                String date = new GetTime().getData();
                String name = habitDate.get(position).getHabitName();
                viewHolder.completeButton.setOnClickListener(new ListButtonListener(position, mContext, viewHolder.completeButton, date, name));
                if (habitDate.get(position).getComplete()){
                    viewHolder.completeButton.setBackgroundResource(R.drawable.icon_right_selected);
                }

                viewHolder.habitText.setText(habitDate.get(position).getHabitName());
                viewHolder.timeText.setText(habitDate.get(position).getHabitTime());
                viewHolder.dayText.setText(habitDate.get(position).getCompleteDay() + "天/" + habitDate.get(position).getGoalDay() + "天");
                map.put(position, convertView);
            } else {
                convertView = map.get(position);
            }
        } else {
            convertView = map.get(position);
        }
        return convertView;
    }

    public class viewHolder {
        private TextView habitText;
        private TextView timeText;
        private TextView dayText;
        private Button completeButton;
    }

    public class ListButtonListener implements View.OnClickListener {

        int mPosition;
        Context mContext;
        Button button1;
        String name;
        String date;

        public ListButtonListener(int position, Context context, Button button, String date, String name) {
            this.mPosition = position;
            this.mContext = context;
            this.button1 = button;
            this.date = date;
            this.name = name;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "您完成了此项任务！", Toast.LENGTH_LONG).show();
            button1.setBackgroundResource(R.drawable.icon_right_selected);
            DBControl.createRealm(mContext).amendMyHabitIsStart(date, name);
//            button1.setBackgroundResource(R.drawable.icon_right_selected);
        }
    }
}
