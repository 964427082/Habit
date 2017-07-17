package f3.nsu.com.habit.actvity;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import f3.nsu.com.habit.Adapter.ItemAdapter;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;
import f3.nsu.com.habit.tool.ItemDragHelperCallback;

import static com.wx.wheelview.common.WheelConstants.TAG;

/**
 * Created by 爸爸你好 on 2017/7/13.
 */

public class MyAddHabitActivity extends Activity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_add_habit_activity);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        init();
    }

    private void init() {
        //已经选中的习惯
        final List<MyIntegralList> myIntegralLists = DBControl.createRealm(this).showTodayMyHabitIntegralList();
        //系统中的习惯
        final List<TaskList> systemList = DBControl.createRealm(this).showSystemTask();
        //自定义中的习惯
        final List<TaskList> customTasks = DBControl.createRealm(this).showCustomTask();
        final List<TaskList> myIntegral = new ArrayList<>();
        for (MyIntegralList m : myIntegralLists)
            myIntegral.add(new TaskList(m.getName(), m.getModify(), m.isStart(), m.getExpectDay(), m.getColorNumber(), m.getClockTime()));

        List<TaskList> everList = new ArrayList<>();
        everList.addAll(systemList);
        everList.addAll(customTasks);
        Log.i(TAG, "init: everListSize = " + everList.size());

        //添加到推荐里面    需要筛选出已经选中的习惯
        final List<TaskList> recommendedTaskLists = new ArrayList<>();
        Log.i(TAG, "init: size1 = " + recommendedTaskLists.size());
        for (TaskList t2 : everList) {
            boolean is = false;
            for (TaskList t1 : myIntegral) {
                if (t1.getName().equals(t2.getName())) {
                    is = true;
                    break;
                }
            }
            if (!is)
                recommendedTaskLists.add(t2);
        }

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        //设置布局
        mRecyclerView.setLayoutManager(manager);
        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);

        final ItemAdapter adapter = new ItemAdapter(this, helper, myIntegral, recommendedTaskLists);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Paint paint = new Paint();
                int viewType = adapter.getItemViewType(position);
                if (viewType == ItemAdapter.TYPE_MY_CHANNEL_HEADER || viewType == ItemAdapter.TYPE_OTHER_CHANNEL_HEADER) {
                    return 4;
                }
                if (position <= myIntegral.size() && position >= ItemAdapter.TYPE_MY) {
                    TaskList ce = myIntegral.get(position - ItemAdapter.TYPE_MY);
                    float width = paint.measureText(ce.getName());
                    if (width <= 48.0 && width > 1.0) {
                        return 1;
                    } else if (width <= 108.0) {
                        return 2;
                    } else
                        return 3;
                } else {
                    TaskList ce = recommendedTaskLists.get(position - myIntegral.size() - ItemAdapter.TYPE_OTHER_CHANNEL_HEADER);
//                    Log.i(TAG, "getSpanSize: position = " + position + "name = " + ce.getName());
                    float width = paint.measureText(ce.getName());
                    if (width <= 48.0 && width > 1.0) {
                        return 1;
                    } else if (width <= 108.0) {
                        return 2;
                    } else
                        return 3;
                }
//                int viewType = adapter.getItemViewType(position);
//                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        //设置adapter
        mRecyclerView.setAdapter(adapter);
        adapter.setOnMyChannelItemClickListener(new ItemAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(MyAddHabitActivity.this, myIntegral.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}