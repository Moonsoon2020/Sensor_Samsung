package com.example.sensor_samsung;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

public class Draw extends View {
    Paint paint = new Paint();
    Canvas canvas;
    float x, dx, dy;
    float y;

    public Draw(Context context) {
        super(context);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Выбираем кисть
        paint.setStyle(Paint.Style.FILL);
        // Белый цвет кисти
        paint.setColor(Color.WHITE);
        // Закрашиваем холст
//        canvas.drawPaint(paint);
        // Включаем сглаживание
        paint.setAntiAlias(true);
        this.canvas = canvas;
        int radius = 100;
        float k = 10F;
//        // Закрашиваем холст
        canvas.drawPaint(paint);
        // Включаем сглаживание

        paint.setColor(Color.argb(127,0,0,255));
        float x_result =  dx / k + x;
        if (x_result < radius)
            x_result = radius;
        if (x_result > getWidth() - radius)
            x_result = getWidth() - radius;
        float y_result =  - dy / k + y;
        if (y_result < radius)
            y_result = radius;
        if (y_result > getHeight() - radius)
            y_result = getHeight() - radius;
        Log.i("DRAW", "X_res: " + x_result + " Y_rez: " + y_result);
        invalidate();
        canvas.drawCircle(x_result, y_result, radius, paint);
        x = x_result; y = y_result;

    }

    public void update(float dy, float dx) {
        if (canvas == null || paint == null)
            return;
        this.dx = dx;
        this.dy = dy;
    }


}