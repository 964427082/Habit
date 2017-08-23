package f3.nsu.com.habit.Adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import f3.nsu.com.habit.R;
import f3.nsu.com.habit.ui.CarouselViewPager;

/**
 * Created by zhy on 2017/8/14.
 * 继承自CarouselPagerAdapter，并使用
 */



public class ImagePagerAdapter extends CarouselPagerAdapter<CarouselViewPager> {

    public ImagePagerAdapter(Context context, CarouselViewPager viewPager) {
        super(viewPager);
    }

    int[] imgRes = {R.mipmap.pattern_body, R.mipmap.pattern_gather, R.mipmap.pattern_healthy};

    @Override
    public Object instantiateRealItem(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setAdjustViewBounds(true);
        view.setImageResource(imgRes[position]);
        view.setLayoutParams(new LinearLayout.LayoutParams(900, 400));
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    public int[] getImgRes() {
        return imgRes;
    }

    @Override
    public int getRealDataCount() {
        return imgRes != null ? imgRes.length : 0;
    }
}

