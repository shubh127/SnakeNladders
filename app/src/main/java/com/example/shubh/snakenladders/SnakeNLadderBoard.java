package com.example.shubh.snakenladders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.atan2;

/**
 * Created by Shubh on 24-03-2016.
 */
public class SnakeNLadderBoard extends SurfaceView implements SurfaceHolder.Callback {


    private final HashMap<Integer, Ladder> mLadderHash;
    private RectF mBoardRect;
    private float linedifference = 0;
    private int p1Score = 1;
    private int p2Score = 1;


    public SnakeNLadderBoard(Context context, HashMap<Integer, Ladder> ladderHash, HashMap<Integer, Integer> mSnakeHash) {
        super(context);
        getHolder().addCallback(this);
        mLadderHash = ladderHash;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        createBoxes(canvas);
        setCounting(canvas);
        createAllLadders(canvas);
        createSnake1(canvas);
        setPlayers(canvas);
    }


    private void createBoxes(Canvas canvas) {
        Paint boardPaint = new Paint();
        boardPaint.setTextSize(20);
        boardPaint.setStrokeWidth(2);
        boardPaint.setColor(Color.WHITE);
        boardPaint.setStyle(Paint.Style.STROKE);

        mBoardRect = new RectF(10, 10, getWidth() - 10, getWidth() - 10);
        canvas.drawRect(mBoardRect, boardPaint);

        linedifference = (mBoardRect.right - mBoardRect.left) / 10;


        for (float i = mBoardRect.left + linedifference; i < mBoardRect.right; i = i + linedifference) {
            canvas.drawLine(i, mBoardRect.top, i, mBoardRect.bottom, boardPaint);
            canvas.drawLine(mBoardRect.left, i, mBoardRect.right, i, boardPaint);
        }
    }

    private void setCounting(Canvas canvas) {
        Paint countPaint = new Paint();
        countPaint.setTextSize(dpToPx(13));
        countPaint.setFakeBoldText(true);


        countPaint.setColor(Color.WHITE);
        countPaint.setStyle(Paint.Style.FILL);
        float startX = linedifference / 2, startY = linedifference * 10 / 12, increment = linedifference;
        int rowcount = 10;
        for (int index = 100; index > 0; index--) {

            canvas.drawText(String.valueOf(index), startX, startY, countPaint);
            if (index % 10 == 1) {
                rowcount--;
                startY = startY + increment;
                index--;
                if (index == 0)
                    break;

                canvas.drawText(String.valueOf(index), startX, startY, countPaint);
            }
            if (rowcount % 2 == 0) {
                startX = startX + increment;
            } else {
                startX = startX - increment;
            }
        }


    }

    private void createAllLadders(Canvas canvas) {

        for (Map.Entry<Integer, Ladder> entry : mLadderHash.entrySet()) {
            createLadder(canvas, entry.getKey(), entry.getValue().to, entry.getValue().color);
        }


    }



    private void createLadder(Canvas canvas, int from, int to, int color) {

        Paint ladderPaint = new Paint();
        ladderPaint.setColor(color);
        ladderPaint.setStrokeWidth(dpToPx(1));
        ladderPaint.setStyle(Paint.Style.FILL);
        if (from > to) {
            int temp = from;
            from = to;
            to = temp;
        }
        float x1, y1, x2, y2;
//        setcolor(color);
        x1 = getX(from) + 20;
        y1 = getY(from) - 20;
        x2 = getX(to) + 20;
        y2 = getY(to) - 20;
//        line(x1,y1,x2,y2);

        canvas.drawLine(x1 + 5, y1, x2 + 5, y2, ladderPaint);
        canvas.drawLine(x1 + 30, y1, x2 + 30, y2, ladderPaint);
//        line(x1+22,y1,x2+22,y2);

////////////////////steps////////////////////
        float m1 = (y1 - y2) / (x1 - x2);
        float newY = 0;
        if (x1 == x2) {

            for (y1 = y1 - 10; y1 > y2 + 10; y1 = y1 - 5) {
                canvas.drawLine(x1 + 5, y1 - 1, x1 + 30, y1 - 1, ladderPaint);
//      line(x1,y1-1,x1+22,y1-1);
            }
        } else {
            if (m1 < 0) {
                for (float newX = x1 + 5; newX < x2 - 5; newX = newX + 5) {
                    newY = m1 * (newX - x1) + y1;
//                    line(newX,newY,newX+22,newY);
                    canvas.drawLine(newX + 5, newY, newX + 30, newY, ladderPaint);
                }
            } else if (m1 > 0) {
                for (float newX = x1 - 5; newX > x2 + 5; newX = newX - 5) {
                    newY = m1 * (newX - x1) + y1;
//                    line(newX,newY,newX+22,newY);
                    canvas.drawLine(newX + 5, newY, newX + 30, newY, ladderPaint);
                }
            }
        }
    }


    public int getX(int position) {
        int row = 0, column = 0, x = 0;
        row = 10 - (position / 10);
        if (position < 10) {
            column = position;
        } else {
            if (row % 2 == 0) {
                column = position % 10;
                if (column == 0) {
                    column = 1;
                }
            } else {
                column = 11 - (position % 10);
                if (position % 10 == 0) {
                    column = 10;
                }
            }
        }
        x = (int) ((column - 1) * linedifference);
        return x;
    }

    public int getY(int position) {
        int row = 0, column = 0, y = 0;
        row = 10 - (position / 10);
        if (position < 10) {
            row = 10;
        } else {
            if (row % 2 == 0) {
                column = position % 10;
                if (column == 0) {
                    row = row + 1;
                }
            } else {
                if (position % 10 == 0) {
                    row = row + 1;
                }
            }
        }
        y = (int) (row * linedifference);
        return y;
    }

    private void createSnake1(Canvas canvas) {


//        Paint paint = new Paint();
//        paint.setColor(Color.GREEN);
//        paint.setStrokeWidth(2);
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawLine(getX(98) + 25, getY(98), getX(18) + 25, getY(18), paint);
//        canvas.drawLine(getX(59) + 25, getY(59), getX(27) + 25, getY(27), paint);
//        canvas.drawLine(getX(52) + 25, getY(52), getX(14) + 25, getY(14), paint);
//        canvas.drawLine(getX(77) + 25, getY(77), getX(55) + 25, getY(55), paint);


    }


    private void setPlayers(Canvas canvas) {
        Paint p1Paint = new Paint();
        p1Paint.setColor(Color.BLUE);
        p1Paint.setStrokeWidth(2);
        p1Paint.setAntiAlias(true);

        p1Paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(getX(p1Score) + 35, getY(p1Score) - 15, dpToPx(6), p1Paint);
        Paint p2Paint = new Paint();
        p2Paint.setColor(Color.RED);
        p2Paint.setStrokeWidth(dpToPx(2));
        p2Paint.setAntiAlias(true);
        p2Paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getX(p2Score) + 70, getY(p2Score) - 15, dpToPx(6), p2Paint);
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


    public void updatePlayersScore(int p1Score, int p2Score) {
        this.p1Score = p1Score;
        this.p2Score = p2Score;
        postInvalidate();
    }


    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
