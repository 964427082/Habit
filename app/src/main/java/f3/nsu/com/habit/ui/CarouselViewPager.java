package f3.nsu.com.habit.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by zhy on 2017/8/14.
 * 3D轮播图控件，第一次使用时出现
 */

public class CarouselViewPager extends ViewPager {
    /**
     * 有数据时，才开始进行轮播
     */
    private boolean hasData;

    public CarouselViewPager(Context context) {
        super(context);
    }

    public CarouselViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startTimer();
    }

    public void startTimer() {
        if (!hasData) {
            return;
        }
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}

