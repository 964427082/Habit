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
    private Paint titlePaint;//绘制文本的画笔
    private Paint recPaint;//矩形画笔
    private int[] habitData;//
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
        titlePaint = new Paint();
        recPaint = new Paint();
        axisLinePaint.setColor(Color.DKGRAY);
        hLinePaint.setColor(Color.LTGRAY);
        titlePaint.setColor(Color.BLACK);
    }
    public void  setHabitData(int[] data){
//        habitData = Data;
//        this.postInvalidate();
        this.habitData = data;
    }
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        //绘制坐标线：
        canvas.drawLine(50, 0, 50, 250, axisLinePaint);
        canvas.drawLine(50, 250, 900, 250, axisLinePaint);
        //绘制坐标内部的水平线
        int leftHeight = 100;// 左侧外周的 需要划分的高度：
        int hPerHeight = leftHeight / 4;
        hLinePaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < 4; i++) {
            canvas.drawLine(100, 20 + i * hPerHeight, width - 10, 20 + i * hPerHeight, hLinePaint);
        }
        //绘制Y轴坐标
        Paint.FontMetrics metrics = titlePaint.getFontMetrics();
        int descent = (int) metrics.descent;
        titlePaint.setTextAlign(Paint.Align.RIGHT);
        for (int i = 0; i < yTitlesStrings.length; i++) {
            canvas.drawText(yTitlesStrings[i], 80, 20 + i * hPerHeight + descent, titlePaint);
        }
        //绘制X轴做坐标
        int xAxisLength = width - 110;
        int columCount = xTitlesStrings.length + 1;
        int step = xAxisLength / columCount;
        for (int i = 0; i < columCount - 1; i++) {
            canvas.drawText(xTitlesStrings[i], 100 + step * (i + 1), 360, titlePaint);
        }
        //绘制矩形
        if (habitData != null && habitData.length > 0) {
            int thisCount = habitData.length;
            for (int i = 0; i < thisCount; i++) {
                int value = habitData[i];
                int num = 8 - value / 10000;
                recPaint.setColor(0xFF1078CF);
                Rect rect = new Rect();
                rect.left = 100 + step * (i + 1) - 10;
                rect.right = 100 + step * (i + 1) + 10;
//              当前的相对高度：
                int rh = (leftHeight * num) / 8;
                rect.top = rh + 20;
                rect.bottom = 320;
                canvas.drawRect(rect, recPaint);
            }
        }
    }
}
