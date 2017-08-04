package f3.nsu.com.habit.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;
import f3.nsu.com.habit.activity.SituationActivity;
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
    private static boolean isCompleteTag = false;

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
            viewHolder.listViewRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.item_layout);
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
            case 5:
                viewHolder.colorButton.setBackgroundResource(R.drawable.round_button_color5);
            default:
                break;
        }
        String date = new GetTime().getData();
        String name = habitDate.get(position).getHabitName();
        if (habitDate.get(position).getComplete() || isCheck[position]) {
            viewHolder.completeButton.setBackgroundResource(R.drawable.icon_right_selected);
        } else {
            int goalDay = habitDate.get(position).getGoalDay();
            int completeDay = habitDate.get(position).getCompleteDay();
            viewHolder.completeButton.setOnClickListener(new ListButtonListener(position, mContext, viewHolder.completeButton,
                    date, name, habitDate.get(position).getModify(), goalDay, completeDay));
            viewHolder.completeButton.setBackgroundResource(R.drawable.icon_right_default);
        }
        habitDate.get(position).getColorNumber();
        viewHolder.progressBar.setProgress(habitDate.get(position).getGoalDay());
        viewHolder.progressBar.setMax(habitDate.get(position).getCompleteDay());
        viewHolder.habitText.setText(habitDate.get(position).getHabitName());
        viewHolder.timeText.setText(habitDate.get(position).getHabitTime());
        viewHolder.dayText.setText(habitDate.get(position).getGoalDay() + "天/" + habitDate.get(position).getCompleteDay() + "天");
        viewHolder.listViewRelativeLayout.setOnClickListener(new ListViewItem(position));
        return convertView;
    }

    class ViewHolder {
        private TextView habitText;
        private TextView timeText;
        private TextView dayText;
        private Button completeButton;
        private ProgressBar progressBar;
        private Button colorButton;
        private RelativeLayout listViewRelativeLayout;
        private int modify; //积分
    }

    public class ListButtonListener implements View.OnClickListener {
        int mPosition;
        Context mContext;

        Button button1;
        String name;
        String date;
        int modify;
        int goalDay;
        int completeDay;


        public ListButtonListener(int position, Context context, Button button, String date, String name, int modify, int goalDay, int completeDay) {
            this.mPosition = position;
            this.mContext = context;
            this.button1 = button;
            this.date = date;
            this.name = name;
            this.modify = modify;
            this.goalDay = goalDay;
            this.completeDay = completeDay;
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
                int i = goalDay + 1;
                //如果其完成天数等于其预计天数
                if (i == completeDay) {
                    Toast.makeText(mContext, "恭喜您，坚持完成了“" + name + "”该项，共计" + completeDay + "天", Toast.LENGTH_SHORT).show();
                    habitDate.get(mPosition).setGoalDay(goalDay + 1);
                    amendMyHabitIsStart(date,name,modify);
                    //添加后删除该项任务
                } else {
                    Toast.makeText(mContext, "您完成了" + name + "此项任务！", Toast.LENGTH_SHORT).show();
                    habitDate.get(mPosition).setGoalDay(goalDay + 1);
                    amendMyHabitIsStart(date, name, modify);
                }
            }
            isCheck[mPosition] = true;
            notifyDataSetChanged();
//            if(!isCompleteTag){
//                new MainActivity().completeTag();
//                isCompleteTag = true;
//            }
        }
    }

    private void amendMyHabitIsStart(String date, String name, int modify) {
        DBControl.createRealm(mContext).amendMyHabitIsStart(date, name, modify);
    }


    //ListView 列表项的点击事件
    public class ListViewItem implements View.OnClickListener {
        int position;

        public ListViewItem(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Activity ac = (Activity) mContext;
            Intent intent = new Intent(ac, SituationActivity.class);
            intent.setAction(habitDate.get(position).getHabitName());
            intent.setFlags(habitDate.get(position).getColorNumber());
            ac.startActivity(intent);
        }
    }
}
