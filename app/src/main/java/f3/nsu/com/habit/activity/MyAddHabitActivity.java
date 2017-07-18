package f3.nsu.com.habit.activity;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.habit_back_adapter)
    ImageButton habitBackAdapter;
    @BindView(R.id.habit_save_adapter)
    ImageButton habitSaveAdapter;
    private RecyclerView mRecyclerView;
    private ItemAdapter adapter;
    public ArrayList<TaskList> myHabitItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_add_habit_activity);
        ButterKnife.bind(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        init();
    }


    public void init() {
        //已经选中的习惯
        final List<MyIntegralList> myIntegralLists = DBControl.createRealm(this).showTodayMyHabitIntegralList();
        //系统中的习惯
        final List<TaskList> systemList = DBControl.createRealm(this).showSystemTask();
        //自定义中的习惯
        final List<TaskList> customTasks = DBControl.createRealm(this).showCustomTask();

        int number[] = generateRandomNumber();
        final ArrayList<TaskList> myIntegral = new ArrayList<>();
        for (MyIntegralList m : myIntegralLists)
            myIntegral.add(new TaskList(m.getName(), m.getModify(), m.isStart(), m.getExpectDay(), m.getColorNumber(), m.getClockTime()));

        List<TaskList> everList = new ArrayList<>();
        for(int i = 0;i < number.length;i++){
            everList.add(systemList.get(number[i]));
        }
        everList.addAll(customTasks);

        //添加到推荐里面    需要筛选出已经选中的习惯
        final ArrayList<TaskList> recommendedTaskLists = new ArrayList<>();
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

        adapter = new ItemAdapter(this, helper, myIntegral, recommendedTaskLists);
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
        Log.i(TAG, "init: myIntegral  size = " + myIntegral.size());
        mRecyclerView.setAdapter(adapter);
        adapter.setOnMyChannelItemClickListener(new ItemAdapter.OnMyChannelItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(MyAddHabitActivity.this, myIntegral.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.habit_back_adapter, R.id.habit_save_adapter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.habit_back_adapter:
                finish();
                break;
            case R.id.habit_save_adapter:
                if (adapter != null){
                    myHabitItems = adapter.getMyHabitItems();
                    changeMyHabitTask(myHabitItems);
                }
                finish();
                break;
        }
    }

    private void changeMyHabitTask(ArrayList<TaskList> myHabitItems) {
        if(myHabitItems.size() == 0){
            //删除我的所有任务  保留积分
            DBControl.createRealm(this).deleteAllMyHabitTask();
//            DBControl.createRealm(this).showTodayMyHabitIntegralList();
        }else {
            //先删除再添加

        }
    }

    //获取随机数
    private int[] generateRandomNumber() {
        HashSet integerHashSet = new HashSet();
        Random random = new Random();
        int number[] = new int[4];
        for (int i = 0; i < 4; i++) {
            int randomInt = random.nextInt(20);
            if (!integerHashSet.contains(randomInt)) {
                integerHashSet.add(randomInt);
                number[i] = randomInt;
            }
        }
        return number;
    }
}