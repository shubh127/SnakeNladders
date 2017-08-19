package com.example.shubh.snakenladders;

/**
 * Created by Shubh on 24-03-2016.
 */
public class Ladder {

    public int to;

    public int from;

    public int color;

    public Ladder(int from, int to , int color)
    {
        this.to = to;
        this.from = from;
        this.color = color;
    }
}
