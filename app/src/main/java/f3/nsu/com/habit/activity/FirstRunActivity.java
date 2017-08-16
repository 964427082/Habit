package f3.nsu.com.habit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import f3.nsu.com.habit.Adapter.ImagePagerAdapter;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.unChoose_button:
                startActivity(new Intent(FirstRunActivity.this,MainActivity.class));
                finish();
                break;
            case R.id.choose_button:
                Toast.makeText(this,"您选择的是****"  ,Toast.LENGTH_LONG).show();
                Log.i(TAG, "onClick: " + "getImgRes" + adapter.getImgRes());
                finish();
                break;
            default:
                break;
        }
    }
}
