package f3.nsu.com.habit.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 带过度动画的折叠收缩布局，主界面日历显示部分
 * Created by zhy on 2017/7/6.
 */

public class ExpandLayout extends RelativeLayout {

    public ExpandLayout(Context context) {
        this(context, null);
    }

    public ExpandLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private View layoutView;
    private int viewHeight;
    public boolean isExpand;//扩展状态判断
    private long animationDuration;//动画时间

    private void initView() {
        layoutView = this;
        animationDuration = 300;
        setViewDimensions();//设置view
    }

    /**
     * @param isExpand 初始状态是否折叠
     */
    public void initExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            collapse();
        }
    }

    /**
     * 设置动画时间
     *
     * @param animationDuration 动画时间
     */
    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    /**
     * 获取subView的总高度
     * View.post()的runnable对象中的方法会在View的measure、layout等事件后触发
     */
    private void setViewDimensions() {
        layoutView.post(new Runnable() {
            @Override
            public void run() {
                if (viewHeight <= 0) {
                    viewHeight = layoutView.getMeasuredHeight();
                }
            }
        });
    }


    public static void setViewHeight(View view, int height) {
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.requestLayout();
    }

    /**
     * 切换动画实现
     */
    private void animateToggle(long animationDuration) {
        ValueAnimator heightAnimation = isExpand ?
                ValueAnimator.ofFloat(0f, viewHeight) : ValueAnimator.ofFloat(viewHeight, 0f);
        heightAnimation.setDuration(animationDuration / 2);
        heightAnimation.setStartDelay(animationDuration / 2);
        heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                setViewHeight(layoutView, (int) val);
            }
        });
        heightAnimation.start();
    }

    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 折叠view
     */
    public void collapse() {
        isExpand = false;
        animateToggle(animationDuration);
    }

    /**
     * 展开view
     */
    public void expand() {
        isExpand = true;
        animateToggle(animationDuration);
    }

    public void toggleExpand() {
        if (isExpand) {
            collapse();
        } else {
            expand();
        }
    }
}
