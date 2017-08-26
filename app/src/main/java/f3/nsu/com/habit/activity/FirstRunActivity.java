package f3.nsu.com.habit.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import f3.nsu.com.habit.Adapter.ImagePagerAdapter;
import f3.nsu.com.habit.PushClass.FistPushClass;
import f3.nsu.com.habit.PushClass.SecondPushClass;
import f3.nsu.com.habit.PushClass.ThirdPushClass;
import f3.nsu.com.habit.R;
import f3.nsu.com.habit.tool.GalleryTransformer;
import f3.nsu.com.habit.ui.CarouselViewPager;

/**
 * Created by zhy on 2017/8/14.
 * 用户第一次使用时
 */

public class FirstRunActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "FirstRunActivity";
    private CarouselViewPager viewPager;
    private Button chooseButton;
    private Button unChooseButton;
    private ImagePagerAdapter adapter;
    private int itemPosition = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first_run_activity);
        viewPager = (CarouselViewPager) findViewById(R.id.id_viewpager);
        chooseButton = (Button) findViewById(R.id.choose_button);
        unChooseButton = (Button) findViewById(R.id.unChoose_button);
        chooseButton.setOnClickListener(this);
        unChooseButton.setOnClickListener(this);


        adapter = new ImagePagerAdapter(this, viewPager);
        viewPager.setOffscreenPageLimit(3);

        viewPager.setAdapter(adapter);
        // 设置3d效果
        viewPager.setPageTransformer(true, new GalleryTransformer());
        // 设置已经有数据了，可以进行轮播，一般轮播的图片等数据是来源于网络，网络数据来了后才设置此值，此处因为是demo，所以直接赋值了
        viewPager.setHasData(true);
        //对轮播图顶层图片的position获取
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                itemPosition = position % 3;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.unChoose_button:
                //跳过
//                startActivity(new Intent(FirstRunActivity.this,MainActivity.class));
                finish();
                break;
            case R.id.choose_button:
                switch (itemPosition){
                    case 0:
                        new FistPushClass(this).showMyFistPush();
                        finish();
                       break;
                    case 1:
                        new SecondPushClass(this).showMySecondPush();
                        finish();
                        break;
                    case 2:
                        new ThirdPushClass(this).showMyThirdPush();
                        finish();
                        break;
                    default:
                        break;
                }
        }
    }
}
