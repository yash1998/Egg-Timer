package com.example.yash1998.eggbreaktimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    ImageView egg;
    Button timerControl;
    CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer;
    int timerIsActive=0;

    public void updateTimer(int secondsLeft){
        int minutes=(int)secondsLeft/60;
        int seconds=secondsLeft-minutes*60;
        String mins="",secs="";
        if (minutes<10){
            mins="0"+Integer.toString(minutes);
        }
        else{
            mins=Integer.toString(minutes);
        }
        if (seconds<10){
            secs="0"+Integer.toString(seconds);
        }
        else{
            secs=Integer.toString(seconds);
        }
        timerTextView.setText(mins+":"+secs);
    }

    public void controlTimer(View view){
        if (timerIsActive==0){
            timerIsActive=1;
            timerControl.setText("STOP!");
            timerSeekBar.setEnabled(false);
            countDownTimer =new CountDownTimer(timerSeekBar.getProgress()*1000,1000){
                @Override
                public void onTick(long l) {
                    int val=(int)l/1000;
                    updateTimer(val);
                    if (val==1){
                        egg.setImageResource(R.drawable.eggbreak3);
                    }
                    else if (val==0){
                        egg.setImageResource(R.drawable.eggbreak4);
                        mediaPlayer.start();
                    }
                }

                @Override
                public void onFinish() {
                    timerIsActive=0;
                    timerSeekBar.setEnabled(true);
                    timerControl.setText("START!");
                    mediaPlayer.start();
                }
            }.start();
        }
        else{
            timerIsActive=0;
            timerSeekBar.setEnabled(true);
            timerControl.setText("START!");
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerControl=(Button)findViewById(R.id.timerControl);
        mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.beep);
        egg=(ImageView)findViewById(R.id.eggImage);
        timerTextView=(TextView)findViewById(R.id.timerTextView);
        timerSeekBar=(SeekBar)findViewById(R.id.timerSeekBar);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(10);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                egg.setImageResource(R.drawable.egg1);
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
