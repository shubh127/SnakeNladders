package com.example.shubh.snakenladders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.DisplayMetrics;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private RelativeLayout llparent;
    private LinearLayout lldice;
    private Button btnPlayer1, btnPlayer2;
    private TextView appName;
    private HashMap<Integer, Ladder> mLadderHash = new HashMap<>();
    private HashMap<Integer, Integer> mSnakeHash = new HashMap<>();
    int p1Score = 1, p2Score = 1;
    private SnakeNLadderBoard boardView;
    private Dice diceView;
    private MediaPlayer mp;
    private LinearLayout.LayoutParams params;
    private BezierView bezierView;
    private BezierView bezierView2;
    private BezierView bezierView3;
    private BezierView bezierView4;
    private TextView tvInstructons;
    private String p1Name = "";
    private String p2Name = "";
    ImageButton revealSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        playMusic(R.raw.finish);
        llparent = (RelativeLayout) findViewById(R.id.ll_parent_layout);
        lldice = (LinearLayout) findViewById(R.id.ll_dice_layout);
        tvInstructons = (TextView) findViewById(R.id.tv_instruction);
        btnPlayer1 = (Button) findViewById(R.id.btn_player_one);
        p1Name = getIntent().getStringExtra("P1NAME");
        btnPlayer1.setText(p1Name);
        btnPlayer2 = (Button) findViewById(R.id.btn_player_two);
        p2Name = getIntent().getStringExtra("P2NAME");
        btnPlayer2.setText(p2Name);

        appName = (TextView) findViewById(R.id.appName);
        Typeface face = Typeface.createFromAsset(MainActivity.this.getAssets(),
                "Quikhand.ttf");
        appName.setTypeface(face);

        revealSettings = (ImageButton) findViewById(R.id.revealSettings);
        revealSettings.setOnClickListener(this);

        btnPlayer1.setOnClickListener(this);
        btnPlayer2.setOnClickListener(this);

        btnPlayer2.setEnabled(false);
        tvInstructons.setText(p1Name + " it's your turn.\nPlease start the game.");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        setupLadderList();
        setupSnakeList();


        params = new LinearLayout.LayoutParams(widthPixels, widthPixels);
        boardView = new SnakeNLadderBoard(this, mLadderHash, mSnakeHash);
        boardView.setBackgroundColor(Color.BLACK);
        llparent.addView(boardView, params);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(lldice.getHeight(), lldice.getHeight());
                diceView = new Dice(MainActivity.this);
                diceView.setBackgroundColor(Color.BLACK);
                lldice.addView(diceView, params1);
//                boardView.updatePlayersScore(p1Score,p2Score);

                int diff = dpToPx(15);

                ArrayList<PointF> pArray = new ArrayList<>();
                pArray.add(new PointF(boardView.getX(18) + diff, boardView.getY(18) - diff));
                pArray.add(new PointF(boardView.getX(40), boardView.getY(40)));
                pArray.add(new PointF(boardView.getX(65), boardView.getY(65)));
                pArray.add(new PointF(boardView.getX(98) + diff, boardView.getY(98) - diff));
                bezierView = new BezierView(MainActivity.this, pArray);
                llparent.addView(bezierView, params);
                bezierView.setTangent(false);
//                bezierView.start();

                pArray = new ArrayList<>();
                pArray.add(new PointF(boardView.getX(55) + diff, boardView.getY(55) - diff));
//                pArray.add(new PointF(boardView.getX(58), boardView.getY(58)));
                pArray.add(new PointF(boardView.getX(65), boardView.getY(65)));
                pArray.add(new PointF(boardView.getX(77) + diff, boardView.getY(77) - diff));
                bezierView2 = new BezierView(MainActivity.this, pArray);
                llparent.addView(bezierView2, params);
                bezierView2.setTangent(false);
//                bezierView2.start();

                pArray = new ArrayList<>();
                pArray.add(new PointF(boardView.getX(14) + diff, boardView.getY(14) - diff));
                pArray.add(new PointF(boardView.getX(29), boardView.getY(29)));
                pArray.add(new PointF(boardView.getX(48) + 50, boardView.getY(48)));
                pArray.add(new PointF(boardView.getX(52) + diff, boardView.getY(52) - diff));
                bezierView3 = new BezierView(MainActivity.this, pArray);
                llparent.addView(bezierView3, params);
                bezierView3.setTangent(false);


                pArray = new ArrayList<>();
                pArray.add(new PointF(boardView.getX(27) + diff, boardView.getY(27) - diff));
                pArray.add(new PointF(boardView.getX(24), boardView.getY(24)));
                pArray.add(new PointF(boardView.getX(57), boardView.getY(57)));
                pArray.add(new PointF(boardView.getX(59) + diff, boardView.getY(59) - diff));
                bezierView4 = new BezierView(MainActivity.this, pArray);
                llparent.addView(bezierView4, params);
                bezierView4.setTangent(false);


            }
        }, 200);


    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    bezierView.start();
                    bezierView2.start();
                    bezierView3.start();
                    bezierView4.start();
                } catch (Exception ex) {

                }
            }
        }, 300);

    }

    private void setupLadderList() {

        mLadderHash.put(58, new Ladder(58, 79, Color.parseColor("#55FF55")));
        mLadderHash.put(4, new Ladder(4, 26, Color.parseColor("#AA00AA")));
        mLadderHash.put(8, new Ladder(8, 12, Color.parseColor("#5555FF")));
        mLadderHash.put(20, new Ladder(20, 74, Color.parseColor("#00AAAA")));
        mLadderHash.put(34, new Ladder(34, 46, Color.parseColor("#A05000")));
        mLadderHash.put(48, new Ladder(48, 54, Color.parseColor("#FF5555")));
        mLadderHash.put(40, new Ladder(40, 60, Color.parseColor("#55FFFF")));
        mLadderHash.put(70, new Ladder(70, 88, Color.parseColor("#AAAAAA")));
        mLadderHash.put(85, new Ladder(85, 95, Color.parseColor("#FF5555")));
        mLadderHash.put(30, new Ladder(30, 50, Color.parseColor("#AA00AA")));
        mLadderHash.put(90, new Ladder(90, 92, Color.parseColor("#FF5555")));
    }


    private void setupSnakeList() {

        mSnakeHash.put(98, 18);
        mSnakeHash.put(59, 27);
        mSnakeHash.put(77, 55);
        mSnakeHash.put(52, 14);
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.revealSettings) {
            PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
            popupMenu.setOnMenuItemClickListener(MainActivity.this);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.reveal_menu, popupMenu.getMenu());
            popupMenu.show();
        }


        Random r = new Random();
        int diceRoll = r.nextInt(6) + 1;
        switch (v.getId()) {
            case R.id.btn_player_one:
                tvInstructons.setText("");
                p1Score = p1Score + diceRoll;
                if (p1Score <= 100) {
                    p1Score = ladderSnakeCheck(p1Score, p1Name);
                    if (diceRoll != 6) {

                        btnPlayer1.setEnabled(false);
                        btnPlayer2.setEnabled(true);
                        tvInstructons.setText(tvInstructons.getText().toString() + "\n" + p2Name + " it's your turn");
                    } else {
//                        Toast.makeText(MainActivity.this, "you got 6", Toast.LENGTH_SHORT).show();
                        tvInstructons.setText("Hurray... " + p1Name + " got 6, It's your chance again");
                        playMusic(R.raw.jump);
                    }


                } else {
                    p1Score = p1Score - diceRoll;
                    btnPlayer1.setEnabled(false);
                    btnPlayer2.setEnabled(true);
//                    tvInstructons.setText(p2Name+" it's your turn");
//                    Toast.makeText(MainActivity.this, "YOU GOT FAR MORE THA U NEED", Toast.LENGTH_SHORT).show();
                    tvInstructons.setText("Oops!! " + p1Name + " got more than you need\n" + p2Name + " it's your turn");
                    playMusic(R.raw.max);
                }

                break;
            case R.id.btn_player_two:
                tvInstructons.setText("");
                p2Score = p2Score + diceRoll;
                if (p2Score <= 100)

                {
                    p2Score = ladderSnakeCheck(p2Score, p2Name);
                    if (diceRoll != 6) {

                        btnPlayer2.setEnabled(false);
                        btnPlayer1.setEnabled(true);
                        tvInstructons.setText(tvInstructons.getText().toString() + "\n" + p1Name + " it's your turn");
                    } else {
                        tvInstructons.setText("Hurray... " + p2Name + " got 6, It's your chance again");
//                        Toast.makeText(MainActivity.this, "YOU GOT 6", Toast.LENGTH_SHORT).show();
                        playMusic(R.raw.jump);
                    }
                } else {
                    p2Score = p2Score - diceRoll;
                    btnPlayer1.setEnabled(true);
                    btnPlayer2.setEnabled(false);
                    tvInstructons.setText("Oops!! " + p2Name + " got more than you need\n" + p1Name + " it's your turn");
//                    Toast.makeText(MainActivity.this, "YOU GOT FAR MORE THA U NEED", Toast.LENGTH_SHORT).show();
                    playMusic(R.raw.max);
                }
                break;
        }
        boardView.updatePlayersScore(p1Score, p2Score);

        diceView.update(diceRoll);

        if (p1Score == 100) {
            tvInstructons.setText("Congrats " + p1Name + " You are the winner..");
//            Toast.makeText(MainActivity.this, "PLAYER 1 WINS", Toast.LENGTH_LONG).show();
            btnPlayer1.setEnabled(false);
            btnPlayer2.setEnabled(false);
            playMusic(R.raw.finish);
        }

        if (p2Score == 100) {
//            tvInstructons.setText("Oops!! You got more than you need");
            tvInstructons.setText("Congrats " + p2Name + " You are the winner..");
//            Toast.makeText(MainActivity.this, "PLAYER 2 WINS", Toast.LENGTH_LONG).show();
            btnPlayer1.setEnabled(false);
            btnPlayer2.setEnabled(false);
            playMusic(R.raw.finish);
        }
    }

    private int ladderSnakeCheck(int score, String pName) {

        if (mSnakeHash.keySet().contains(score) && mLadderHash.keySet().contains(score)) {
            tvInstructons.setText("Oops!! " + pName + "Got 6 but Bitten by snake");
//            Toast.makeText(MainActivity.this, "OOOPPPSSS!!! BITTEN BY SNAKE ", Toast.LENGTH_SHORT).show();
            playMusic(R.raw.snake);
            return mSnakeHash.get(score);
        } else if (mLadderHash.keySet().contains(score)) {
            tvInstructons.setText("Yipiee!! " + pName + " found a ladder");
//            Toast.makeText(MainActivity.this, "YIPIEEEE!!! YOU FOUND LADDER ", Toast.LENGTH_SHORT).show();
            playMusic(R.raw.grow);
            return mLadderHash.get(score).to;


        } else if (mSnakeHash.keySet().contains(score)) {
            tvInstructons.setText("Oops!! " + pName + " Bitten by snake");
//            Toast.makeText(MainActivity.this, "OOOPPPSSS!!! BITTEN BY SNAKE ", Toast.LENGTH_SHORT).show();
            playMusic(R.raw.snake);
            return mSnakeHash.get(score);
        }
        return score;
    }

    private void playMusic(int resId) {

        try {
            mp.stop();
        } catch (Exception ignored) {

        }

        try {
            mp = MediaPlayer.create(this, resId);
            mp.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        try {
            mp.stop();
        } catch (Exception ignored) {

        }
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.newGame:
                Intent intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return false;
        }
    }
}
