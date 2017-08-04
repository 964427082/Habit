package f3.nsu.com.habit.activity;

import android.app.Activity;
import android.content.Context;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import f3.nsu.com.habit.Adapter.ItemAdapter;
import f3.nsu.com.habit.GetTime.GetTime;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.RealmDataBase.DBControl;
import f3.nsu.com.habit.RealmDataBase.TaskData.MyIntegralList;
import f3.nsu.com.habit.RealmDataBase.TaskData.TaskList;
import f3.nsu.com.habit.tool.ItemDragHelperCallback;

import static f3.nsu.com.habit.RealmDataBase.DBControl.createRealm;

/**
 * Created by 爸爸你好 on 2017/7/13.
 */

public class MyAddHabitActivity extends Activity {
    private static final String TAG = "MyAddHabitActivity";
    @BindView(R.id.habit_back_adapter)
    ImageButton habitBackAdapter;
    @BindView(R.id.habit_save_adapter)
    ImageButton habitSaveAdapter;
    private RecyclerView mRecyclerView;
    private ItemAdapter adapter;
    public ArrayList<TaskList> myHabitItems = null;
    private String data = new GetTime().getData();
    private Context context = this;
    private boolean isSave = false;
    private boolean isExit = false;

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
        final List<MyIntegralList> myIntegralLists = createRealm(this).showTodayMyHabitIntegralList();
        //系统中的习惯
        final List<TaskList> systemList = createRealm(this).showSystemTask();
        //自定义中的习惯
        final List<TaskList> customTasks = createRealm(this).showCustomTask();
        int number[] = generateRandomNumber();
        final ArrayList<TaskList> myIntegral = new ArrayList<>();
        for (MyIntegralList m : myIntegralLists)
            myIntegral.add(new TaskList(m.getName(), m.getModify(), m.isStart(), m.getExpectDay(), m.getColorNumber(), m.getClockTime()));

        List<TaskList> everList = new ArrayList<>();
        for (int i = 0; i < number.length; i++) {
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
                    float width = paint.measureText(ce.getName());
                    if (width <= 48.0 && width > 1.0) {
                        return 1;
                    } else if (width <= 108.0) {
                        return 2;
                    } else
                        return 3;
                }
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

    @OnClick({R.id.habit_back_adapter, R.id.habit_save_adapter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.habit_back_adapter:
                if(isExit)
                    finish();
                if (isSave && !isExit){
                    Toast.makeText(context,"你还没保存习惯,继续点击可退出",Toast.LENGTH_SHORT).show();
                    isExit = true;
                }
                else {
                    if (myHabitItems == null && !isExit) {
                        Toast.makeText(context,"你还没添加习惯,继续点击可退出",Toast.LENGTH_SHORT).show();
                        isExit = true;
                    }
                }
                break;
            case R.id.habit_save_adapter:
                isSave = true;
                myHabitItems = adapter.getMyHabitItems();
                if (myHabitItems.size() == 0) {
                    Toast.makeText(context, "亲，至少保留一个习惯哟！", Toast.LENGTH_SHORT).show();
                } else {
                    if (ItemAdapter.isAdd) {
                        //先删除再添加
                        /**
                         * myIntegralLists 为未修改之前的习惯列表
                         * myHabitItems  为修改之后的习惯列表
                         */
                        List<MyIntegralList> myIntegralLists = DBControl.createRealm(this).showTodayMyHabitIntegralList();
                        getNoDifferent(myIntegralLists, myHabitItems);
                        ItemAdapter.isAdd = false;
                        finish();
                        break;
                    } else {
                        Toast.makeText(context, "未做修改，无法保存！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    /**
     * 筛选习惯 进行添加  删除操作
     * 1.用myIntegralTaskList  来存储未修改之前的我的习惯
     * 2.采用长度为 修改之前与修改之后的两个列表长度之和 的map
     * 3.将修改之前的表存入map里，名字唯一，值为1
     * 4.用修改之后的表与map里面的数据做对比，如果名字存在map里面值就变为2，否则将这条数据存入修改后的习惯表里
     * 5.找到map中值为1的数据，根据名字去删除修改之前习惯表里的数据
     */
    private void getNoDifferent(List<MyIntegralList> myIntegralLists, List<TaskList> myHabitItems) {
        List<TaskList> myIntegralTaskList = new ArrayList<>();
        for (MyIntegralList m : myIntegralLists) {
            myIntegralTaskList.add(new TaskList(m.getName(), m.getModify(), m.isStart(), m.getExpectDay(), m.getColorNumber(), m.getClockTime()));

        }
        Map<String, Integer> map = new HashMap<String, Integer>(myIntegralTaskList.size() + myHabitItems.size());
        for (TaskList m : myIntegralTaskList) {
            map.put(m.getName(), 1);
        }
        for (TaskList t : myHabitItems) {
            Integer c = map.get(t.getName());
            if (c != null) {
                map.put(t.getName(), ++c);
                continue;
            } else {
                //此处保存的是新添加的
                DBControl.createRealm(this).addMyHabitTask(data, t.getName(), t.getModify(), t.getExpectDay(), t.getTime(), t.getColorNumber());
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                for (TaskList m : myIntegralTaskList) {
                    if (m.getName().equals(entry.getKey())) {
                        //此处删除的是下滑的    删除当天数据库里面的数据
                        DBControl.createRealm(this).deleteMyHabitTaskList(m.getName());
                    }
                }
            }
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