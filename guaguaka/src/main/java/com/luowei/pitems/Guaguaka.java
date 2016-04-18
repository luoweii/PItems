package com.luowei.pitems;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 骆巍 on 2016/4/15.
 */
public class Guaguaka extends View {
    private Path path;
    private Paint paint;
    private Canvas canvas;
    private Bitmap bitmap;

    private float lastX;
    private float lastY;

    public Guaguaka(Context context) {
        this(context, null);
    }

    public Guaguaka(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Guaguaka);
        float width = a.getDimension(R.styleable.Guaguaka_gg_paintWidth, 50);
        a.recycle();

        paint = new Paint();
        paint.setStrokeWidth(width);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        //设置橡皮擦的画笔
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawColor(Color.LTGRAY);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.reset();
                lastX = x;
                lastY = y;
                path.moveTo(lastX, lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas.drawPath(path, paint);
        canvas.drawBitmap(bitmap, 0, 0, null);
        super.onDraw(canvas);
    }
}
