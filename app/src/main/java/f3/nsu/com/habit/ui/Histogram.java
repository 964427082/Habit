package f3.nsu.com.habit.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * 柱状图绘制
 * Created by zhy on 2017/7/13.
 */

public class Histogram extends View {

    private Paint axisLinePaint;//坐标轴 轴线画笔
    private Paint hLinePaint;//坐标轴水平内部虚线画笔
    private Paint xTitlePaint;//绘制X轴文本的画笔
    private Paint yTitlePaint;//绘制Y轴文本的画笔
    private Paint recPaint;//矩形画笔
    private float[] habitData;//
    private String[] yTitlesStrings = new String[]{"100%","75%","50%","25%","0"};//Y轴坐标
    private String[] xTitlesStrings = new String[]{"一","二","三","四"};//X轴坐标

    public Histogram(Context context){
        super(context);
        init(context,null);
    }
    public Histogram(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        init(context,attributeSet);
    }

    /**
     * 初始化画笔，以及画笔颜色
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
    public void  setHabitData(float[] data){
        this.habitData = data;
    }
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //绘制坐标线：
        canvas.drawLine(50, 0, 50, 250, axisLinePaint);
        canvas.drawLine(50, 250, 850, 250, axisLinePaint);
        //绘制坐标内部的水平线
        int leftHeight = 230;// 左侧外周的 需要划分的高度：
        int hPerHeight = leftHeight / 4;
        hLinePaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < 4; i++) {
            canvas.drawLine(50,  20+i * hPerHeight, 850, 20+ i * hPerHeight, hLinePaint);
        }
        //绘制Y轴坐标
        Paint.FontMetrics metrics = yTitlePaint.getFontMetrics();
        int descent = (int) metrics.descent;
        yTitlePaint.setTextAlign(Paint.Align.RIGHT);
        yTitlePaint.setTextSize(18);
        for (int i = 0; i < yTitlesStrings.length; i++) {
            canvas.drawText(yTitlesStrings[i], 45, 20+i * hPerHeight + descent, yTitlePaint);
        }
        //绘制X轴做坐标
        int xAxisLength = 800;
        int columCount = xTitlesStrings.length + 1;
        int step = xAxisLength / columCount;
        xTitlePaint.setTextAlign(Paint.Align.RIGHT);
        xTitlePaint.setTextSize(26);
        for (int i = 0; i < columCount - 1; i++) {
            canvas.drawText(xTitlesStrings[i], 62 + step * (i + 1), 280, xTitlePaint);
        }
        //绘制矩形
        if (habitData != null && habitData.length > 0) {
            int thisCount = habitData.length;
            float maxValue = habitData[0];
            for (int j=0;j<habitData.length;j++){
                if (habitData[j]>maxValue){
                    maxValue = habitData[j];
                }
            }
            for (int i = 0; i < thisCount; i++) {
                float value = habitData[i];
                if (maxValue == value){
                    recPaint.setColor(0xff29e3b6);
                }
                else {
                    recPaint.setColor(0xFF2fa8e7);
                }
                float num = 10 - value /10;
                Rect rect = new Rect();
                rect.left = 50 + step * (i + 1) - 25;
                rect.right = 50 + step * (i + 1) + 25;
//              当前的相对高度：
                float rh = (leftHeight * num) / 10;
                rect.top = (int)(rh + 20);
                rect.bottom = 250;
                canvas.drawRect(rect, recPaint);
            }
        }
    }
}
