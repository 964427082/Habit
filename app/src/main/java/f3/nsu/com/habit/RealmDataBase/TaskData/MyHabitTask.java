package f3.nsu.com.habit.RealmDataBase.TaskData;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 爸爸你好 on 2017/7/6.
 */

public class MyHabitTask  extends RealmObject{
    @PrimaryKey
    private String data;    //主键   日期：20170706     储存为某天完成的习惯

    @Index
    private String name;    // 选中的习惯名字

    private int modify;     //该习惯的积分
    private String completeTime;    //完成的时间     02：05
    private int expectDay;    //预设计的天数
    private int  insistDay;     //已经坚持的天数
    private String clockTime;   //提醒时间
    private int degreeOfHistory;    //历史完成次数
    private int optimumDegree;  //最佳次数
    private int monthFinishDegree;  //本月完成次数

    private boolean isStart;    //是否当天已经完成
}
