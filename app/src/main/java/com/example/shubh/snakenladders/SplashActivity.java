package com.example.shubh.snakenladders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pbadmin on 23/11/16.
 */
public class SplashActivity extends Activity {
    private SplashView splashView;
    private ViewGroup mainViewView;
    private View mContentView;
    private Activity mContext;

    private TextInputEditText tvFirstPlayer, tvSecondPlayer;
    private TextView tvStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        mContext = this;

        splashView = (SplashView) findViewById(R.id.splash_view);
        mainViewView = (ViewGroup) findViewById(R.id.main_view);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoadingDataEnded();
            }
        }, 1500);
    }

    private void onLoadingDataEnded() {
        splashView.splashAndDisappear(new SplashView.ISplashListener() {
            @Override
            public void onStart() {
                mContentView = LayoutInflater.from(mContext).inflate(R.layout.splash_content_view, null);
                mainViewView.addView(mContentView, 0);
                tvFirstPlayer = (TextInputEditText) mContentView.findViewById(R.id.et_player_1_name);
                tvSecondPlayer = (TextInputEditText) mContentView.findViewById(R.id.et_player_2_name);
                tvStart = (TextView) mContentView.findViewById(R.id.tv_start);
                tvStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(tvFirstPlayer.getText().toString().trim().length()==0)
                        {
                            Toast.makeText(mContext, "Please enter first player name",Toast.LENGTH_SHORT).show();
                            tvFirstPlayer.setError("Field Required");
                            tvFirstPlayer.requestFocus();
                        }
                        else if(tvSecondPlayer.getText().toString().trim().length()==0)
                        {
                            Toast.makeText(mContext, "Please enter second player name",Toast.LENGTH_SHORT).show();
                            tvSecondPlayer.setError("Field Required");
                            tvSecondPlayer.requestFocus();
                        }
                        else
                        {
                            Intent in = new Intent(mContext, MainActivity.class);
                            in.putExtra("P1NAME", tvFirstPlayer.getText().toString().trim());
                            in.putExtra("P2NAME", tvSecondPlayer.getText().toString().trim());
                            startActivity(in);
                            finish();
                        }
                    }
                });
            }

            @Override
            public void onUpdate(float completionFraction) {

            }

            @Override
            public void onEnd() {
                splashView = null;
//                mContentView = LayoutInflater.from(mContext).inflate(R.layout.splash_content_view, null);
//                mainViewView.addView(mContentView,0);

            }
        });
    }


}
