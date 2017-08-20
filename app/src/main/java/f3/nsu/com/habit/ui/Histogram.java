package f3.nsu.com.habit.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 柱状图绘制
 * Created by zhy on 2017/7/13.
 */

public class Histogram extends View implements Runnable {
    private static final String TAG = "Histogram";
    private Handler handler = new Handler(); // 用于延时更新，实现动画
    private float animHeight; // 进度条动画高度

    private Paint axisLinePaint;//坐标轴 轴线画笔
    private Paint hLinePaint;//坐标轴水平内部虚线画笔
    private Paint xTitlePaint;//绘制X轴文本的画笔
    private Paint yTitlePaint;//绘制Y轴文本的画笔
    private Paint recPaint;//矩形画笔
    private float currentHeight; // 当前柱状图应有的高度，应由计算得来
    private float[] habitData;//  柱状的高度
    private float hPerHeight;
    private String[] yTitlesStrings = new String[]{"100%", "75%", "50%", "25%", "0"};//Y轴坐标
    private String[] xTitlesStrings = new String[]{"一", "二", "三", "四"};//X轴坐标
    private OnChartClickListener listener;
    private static float bottom = 250;
    private int step;   //x轴  梯度

    public Histogram(Context context) {
        super(context);
        init(context, null);
    }

    public Histogram(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    /**
     * 初始化画笔，以及画笔颜色
     *
     * @param context
     * @param attributeSet
     */
    private void init(Context context, AttributeSet attributeSet) {
        axisLinePaint = new Paint();
        hLinePaint = new Paint();
        xTitlePaint = new Paint();
        yTitlePaint = new Paint();
        recPaint = new Paint();
        axisLinePaint.setAntiAlias(true);
        hLinePaint.setAntiAlias(true);
        xTitlePaint.setAntiAlias(true);
        yTitlePaint.setAntiAlias(true);
        recPaint.setAntiAlias(true);
        axisLinePaint.setColor(0xff2fa8e7);
        hLinePaint.setColor(Color.LTGRAY);
        xTitlePaint.setColor(Color.BLACK);
        xTitlePaint.setColor(Color.BLACK);
    }

    public void setHabitData(float[] data) {
        this.habitData = data;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制坐标线：
        canvas.drawLine(50, 0, 50, bottom, axisLinePaint);
        canvas.drawLine(50, bottom, 850, bottom, axisLinePaint);
        //绘制坐标内部的水平线
        float leftHeight = 230;// 左侧外周的 需要划分的高度：
        hPerHeight = leftHeight / 4;   // 57.5
        hLinePaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < 4; i++) {
            canvas.drawLine(50, 20 + i * hPerHeight, 850, 20 + i * hPerHeight, hLinePaint);
        }
    //绘制Y轴坐标
        Paint.FontMetrics metrics = yTitlePaint.getFontMetrics();
        int descent = (int) metrics.descent;
        yTitlePaint.setTextAlign(Paint.Align.RIGHT);
        yTitlePaint.setTextSize(18);
        for (int i = 0; i < yTitlesStrings.length; i++) {
            canvas.drawText(yTitlesStrings[i], 45, 20 + i * hPerHeight + descent, yTitlePaint);
        }
        //绘制X轴做坐标
        int xAxisLength = 800;
        int columCount = xTitlesStrings.length + 1;
        step = xAxisLength / columCount;        //160
        xTitlePaint.setTextAlign(Paint.Align.RIGHT);
        xTitlePaint.setTextSize(26);
        for (int i = 0; i < columCount - 1; i++) {
            canvas.drawText(xTitlesStrings[i], 62 + step * (i + 1), 280, xTitlePaint);
        }
        //    延时绘制，实现动画效果。数字越大，延时越久，动画效果就会越慢
        handler.postDelayed(this, 1);

        int thisCount = habitData.length;
        float maxValue = habitData[0];
        for (int j = 0; j < thisCount; j++) {
            if (habitData[j] > maxValue) {
                maxValue = habitData[j];
            }
        }
        for (int i = 0; i < thisCount; i++) {
            float value = habitData[i];
            if (maxValue == value) {
                recPaint.setColor(0xff29e3b6);
            } else {
                recPaint.setColor(0xFF2fa8e7);
            }
            float num = 10 - value / 10;
            RectF dataRectF = new RectF();
            dataRectF.left = 50 + step * (i + 1) - 25;
            dataRectF.right = 50 + step * (i + 1) + 25;
            //获取柱子高度
            currentHeight = habitData[i];
            float rh = (leftHeight * num) / 10;
//            dataRectF.top = (int) (rh + 20);
//            dataRectF.bottom = 250;

            if (currentHeight == 0.0) {
                dataRectF.top = bottom;
            } else {
                if (animHeight >= currentHeight) {
                    dataRectF.top = bottom - (int) (currentHeight * 2.3);
                } else {
                    dataRectF.top = bottom - animHeight * 2;
                }
            }
            dataRectF.bottom = bottom;
            //限制最高高度
            if (dataRectF.top < 20) {
                dataRectF.top = 20;
            }
            canvas.drawRoundRect(dataRectF, 10, 10, recPaint);
        }


        //绘制矩形
//        if (habitData != null && habitData.length > 0) {
//            int thisCount = habitData.length;
//            float maxValue = habitData[0];
//            for (int j = 0; j < habitData.length; j++) {
//                if (habitData[j] > maxValue) {
//                    maxValue = habitData[j];
//                }
//            }
//            for (int i = 0; i < thisCount; i++) {
//                float value = habitData[i];
//                if (maxValue == value) {
//                    recPaint.setColor(0xff29e3b6);
//                } else {
//                    recPaint.setColor(0xFF2fa8e7);
//                }
//                float num = 10 - value / 10;
//                Rect rect = new Rect();
//                rect.left = 50 + step * (i + 1) - 25;
//                rect.right = 50 + step * (i + 1) + 25;
////              当前的相对高度：
//                float rh = (leftHeight * num) / 10;
//                rect.top = (int) (rh + 20);
//                rect.bottom = 250;
//                canvas.drawRect(rect, recPaint);
//            }
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                //获取点击坐标
                float x = event.getX();
                float y = event.getY();
                //判断点击位置
                float leftX = 0;
                float rightX = 0;
                for(int i = 0;i < 4;i++){
                    leftX = 62 + (i + 1) * step;
                    rightX = 88 + (i + 1) * step;
                    if(x < leftX){
                        continue;
                    }
                    if(leftX <= x && rightX >= x){
                        //获取点击的柱子区域的y值
                        float top =bottom - (int) (habitData[i] * 2.3) ;
                        if(y > top && y <= bottom){
                            //判断是否设置监听
                            // 将点击的第几条柱子，点击柱子顶部的坐值，用于弹出dialog提示数据，
                            // 还要返回百分比currentHeidht = Float.parseFloat(data[num - 1 - i])
                            if(listener != null){
                                listener.onClick(i + 1,leftX + step,top,habitData[i]);
                            }
                            break;
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public void run() {
        animHeight += 2.3;
        if (animHeight >= 115) {
            return;
        } else {
            invalidate();
        }
    }

    /**
     *    * 柱子点击时的监听接口
     *    
     */
    public interface OnChartClickListener {
        void onClick(int num, float x, float y, float value);
    }

    /**
     *    * 设置柱子点击监听的方法
     *    * @param listener
     */

    public void setOnChartClickListener(OnChartClickListener listener) {
        this.listener = listener;
    }
}
