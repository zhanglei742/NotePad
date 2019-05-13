package com.example.json.mytouzhisystem.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import com.example.json.mytouzhisystem.R;

public class NoteEditText extends EditText {
    private int padding = 50;
    private int lineColor = Color.BLACK;
    private float strokeWidth = 1;

    /**
     * 自定义View中必须定义构造方法，至少要重写两参构造方法
     *
     * @param context
     * @param attrs   该参数很重要，xml布局文件中对该控件定义的所有的属性数值都会被保存进该对象中
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public NoteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        //设置让EditText中的内容物置顶（EditText中内容物默认是居中排列）
        setGravity(Gravity.TOP);

        //接收布局文件中的属性设置数据，赋值给当前类中的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NoteEditText);
        lineColor = typedArray.getColor(R.styleable.NoteEditText_lineColor, lineColor);
        padding = (int) typedArray.getDimension(R.styleable.NoteEditText_padding, padding);
        strokeWidth = typedArray.getDimension(R.styleable.NoteEditText_strokeWidth, strokeWidth);
        Drawable drawable = typedArray.getDrawable(R.styleable.NoteEditText_background);
        if (drawable != null) {
            setBackground(drawable);
        } else {
            setBackgroundColor(Color.TRANSPARENT);
        }
        //释放array数组
        typedArray.recycle();
        // 设置EditText的内填充，左右留白，上下不留空隙
        setPadding(padding, 0, padding, 0);

    }

    public NoteEditText(Context context) {
        super(context);
    }

    /**
     * 该回调方法是自定义View中最重要的方法，用来绘制视图
     *
     * @param canvas 画布 Paint 画笔
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //定义画笔对象
        Paint paint = new Paint();
        //让画笔抗锯齿
        paint.setAntiAlias(true);
        //设置画笔颜色
        paint.setColor(lineColor);
        //设置画笔笔画的宽度
        paint.setStrokeWidth(strokeWidth);
        // 获取当前控件的高度和宽度
        int viewHeight = getHeight();
        int viewWidth = getWidth();

        //根据字体大小EditText控件会自动计算出每行应该显示的高度
        int lineHeight = getLineHeight()+30;
        //根据当前控件的高度，结合每行的高度，计算出当前控件上可以绘制的线条个数
        int pageLineCounts = viewHeight / lineHeight-1;
        //通过for循环，依次绘制直线
        for (int i = 0; i < pageLineCounts; i++) {
            canvas.drawLine(padding, (i + 1) * lineHeight, viewWidth - padding, (i + 1) *
                    lineHeight, paint);
        }

        // 获取当前EditText控件中所有文字的行数
        int textLineCounts = getLineCount();
        //为了避免重复绘制，已经绘制的线条不再重复绘制。只绘制超出该控件以外的线条
        if (textLineCounts > pageLineCounts) {
            for (int i = pageLineCounts; i < textLineCounts; i++) {
                canvas.drawLine(padding, (i + 1) * lineHeight, viewWidth - padding, (i + 1) *
                        lineHeight, paint);
            }
        }
    }
}
