package com.example.shubh.snakenladders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by Shubh on 25-03-2016.
 */
public class Dice extends SurfaceView implements SurfaceHolder.Callback {
    private int mDiceRoll = 0;

    public Dice(Context context) {
        super(context);
    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint boardPaint = new Paint();
        boardPaint.setTextSize(20);
        boardPaint.setStrokeWidth(2);
        boardPaint.setColor(Color.WHITE);
        boardPaint.setStyle(Paint.Style.STROKE);

        RectF mBoardRect = new RectF(10, 10, getWidth() - 10, getWidth() - 10);
        canvas.drawRect(mBoardRect, boardPaint);

        boardPaint.setStyle(Paint.Style.FILL);
        switch (mDiceRoll) {
            case 1:

                canvas.drawCircle(getWidth() / 2, getWidth() / 2, dpToPx(10), boardPaint);

                break;

            case 2:
                canvas.drawCircle(dpToPx(25), dpToPx(25), dpToPx(10), boardPaint);
                canvas.drawCircle(getWidth() - dpToPx(25), getWidth() - dpToPx(25), dpToPx(10), boardPaint);
                break;

            case 3:

                canvas.drawCircle(getWidth() / 2, getWidth() / 2, dpToPx(10), boardPaint);

                canvas.drawCircle(dpToPx(25), dpToPx(25), dpToPx(10), boardPaint);
                canvas.drawCircle(getWidth() - dpToPx(25), getWidth() - dpToPx(25), dpToPx(10), boardPaint);

                break;

            case 4:

                canvas.drawCircle(dpToPx(25), dpToPx(25), dpToPx(10), boardPaint);
                canvas.drawCircle(dpToPx(25), getWidth() - dpToPx(25), dpToPx(10), boardPaint);
                canvas.drawCircle(getWidth() - dpToPx(25), getWidth() - dpToPx(25), dpToPx(10), boardPaint);
                canvas.drawCircle(getWidth() - dpToPx(25), dpToPx(25), dpToPx(10), boardPaint);
                break;

            case 5:
                canvas.drawCircle(getWidth() / 2, getWidth() / 2, dpToPx(10), boardPaint);
                canvas.drawCircle(dpToPx(25), dpToPx(25), dpToPx(10), boardPaint);
                canvas.drawCircle(dpToPx(25), getWidth() - dpToPx(25), dpToPx(10), boardPaint);
                canvas.drawCircle(getWidth() - dpToPx(25), getWidth() - dpToPx(25), dpToPx(10), boardPaint);
                canvas.drawCircle(getWidth() - dpToPx(25), dpToPx(25), dpToPx(10), boardPaint);
                break;

            case 6:
                canvas.drawCircle(dpToPx(25), getWidth() / 2, dpToPx(10), boardPaint);
                canvas.drawCircle(getWidth() - dpToPx(25), getWidth() / 2, dpToPx(10), boardPaint);
                canvas.drawCircle(dpToPx(25), dpToPx(25), dpToPx(10), boardPaint);
                canvas.drawCircle(dpToPx(25), getWidth() - dpToPx(25), dpToPx(10), boardPaint);
                canvas.drawCircle(getWidth() - dpToPx(25), getWidth() - dpToPx(25), dpToPx(10), boardPaint);
                canvas.drawCircle(getWidth() - dpToPx(25), dpToPx(25), dpToPx(10), boardPaint);
                break;

            default:
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false); //Allows us to use invalidate() to call onDraw()
        Canvas c = getHolder().lockCanvas();

        postInvalidate();
        getHolder().unlockCanvasAndPost(c);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    public void update(int diceRoll) {
        mDiceRoll=diceRoll;
        postInvalidate();

    }
}
