package f3.nsu.com.habit.tool;

/**
 * Created by 爸爸你好 on 2017/7/13.
 */

public interface OnDragVHListener {
    /**
     * Item被选中时触发
     */
    void onItemSelected();


    /**
     * Item在拖拽结束/滑动结束后触发
     */
    void onItemFinish();
}