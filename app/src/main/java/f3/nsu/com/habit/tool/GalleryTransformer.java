package f3.nsu.com.habit.tool;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by zhy on 2017/8/14.
 * 说老实话  我也没看懂这代码  到底是****什么鬼
 */

public class GalleryTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View view, float position) {
        float scale = 0.5f;
        float scaleValue = 1 - Math.abs(position) * scale;
        view.setScaleX(scaleValue);

        view.setScaleY(scaleValue);
        view.setAlpha(scaleValue);
        view.setPivotX(view.getWidth() * (1 - position - (position > 0 ? 1 : -1) * 0.75f) * scale);
        view.setElevation(position > -0.25 && position < 0.25 ? 1 : 0);
    }
}