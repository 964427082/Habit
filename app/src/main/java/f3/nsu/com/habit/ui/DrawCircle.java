package f3.nsu.com.habit.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhy on 2017/7/8.
 */

public class DrawCircle extends View {


    private int color1 = Color.parseColor("#ff0000");
    private int color2 = Color.parseColor("#000000");
    private int color3 = Color.parseColor("#7ed321");
    public DrawCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onDraw(Canvas canvas){
        Paint p = new Paint();
        p.setColor(color1);// 设置红色
        p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除
        canvas.drawCircle(12, 12, 12, p);
    }
}
