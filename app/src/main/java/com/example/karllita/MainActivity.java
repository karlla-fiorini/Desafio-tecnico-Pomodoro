package com.example.karllita;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    CountDownTimer CDT;

    TextView Timer;
    TextView Pomos;
    TextView Pergunta;
    EditText Time;

    Button StateButton;
    Button SetTimer;
    Button PomosButton;
    Button BreakButton;
    Button AgreeBotton;
    Button DesagreeBotton;

    long TotalTime = 25 * 60000;
    long RemainingTime = TotalTime;

    boolean SET = false;
    boolean PomoView = false;

    int PomosAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timer = findViewById(R.id.TimerText);
        Time = findViewById(R.id.TotalTimer);
        Pomos = findViewById(R.id.Pomos);
        Pergunta = findViewById(R.id.Break10);

        StateButton = findViewById(R.id.StateButton);
        PomosButton = findViewById(R.id.DailyPomos);
        SetTimer = findViewById(R.id.SetTimer);
        BreakButton = findViewById(R.id.Break);
        AgreeBotton = findViewById(R.id.Agree);
        DesagreeBotton = findViewById(R.id.Desagree);


        StateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start();
                SetTimer.setVisibility(View.INVISIBLE);
                BreakButton.setVisibility(View.VISIBLE);
                StateButton.setVisibility(View.INVISIBLE);
            }
        });

        PomosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PomoView){
                    Pomos.setVisibility(View.INVISIBLE);
                    PomoView = false;
                }else{
                    Pomos.setVisibility(View.VISIBLE);
                    PomoView = true;
                }
            }
        });

        SetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(SET) {
                    Time.setVisibility(View.INVISIBLE);
                    SET = false;
                    SetTimer((long) Integer.parseInt(Time.getText().toString()) * 60000);

                }else{
                    Time.setVisibility(View.VISIBLE);
                    SET = true;
                }

            }
        });

        BreakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pause();
                Break();
            }
        });

        AgreeBotton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgreeBotton.setVisibility(View.INVISIBLE);
                DesagreeBotton.setVisibility(View.INVISIBLE);
                Pergunta.setVisibility(View.INVISIBLE);

                Pause();
                Break10();
            }
        });

        DesagreeBotton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgreeBotton.setVisibility(View.INVISIBLE);
                DesagreeBotton.setVisibility(View.INVISIBLE);
                Pergunta.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void SetTimer (long mls){
        TotalTime = mls;
        reset();
    }

    public void Start (){

        CDT = new CountDownTimer(RemainingTime + 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                RemainingTime = millisUntilFinished;
                Timer(RemainingTime);
            }

            @Override
            public void onFinish() {
                PomosAmount++;
                Pomos.setText(Integer.toString(PomosAmount));

                if(PomosAmount % 4 == 0){
                    Break10Not();
                }

                reset();
                Start();
            }
        }.start();

    }

    public void Pause (){
        CDT.cancel();
    }

    public void reset (){
        RemainingTime = TotalTime;
        Timer(RemainingTime);
    }

    public void Timer(long RemainingTime){

        String ActualTime = "" + TimeUnit.MILLISECONDS.toMinutes(RemainingTime) + ":";

        if( (RemainingTime % 60000 / 1000) < 10){
            ActualTime += "0";
        }

        ActualTime += RemainingTime % 60000 / 1000;

        Timer.setText(ActualTime);
    }

    public void Break(){
        Timer.setTextColor(Color.parseColor("#FF6200EE"));
        CDT = new CountDownTimer(5 * 60000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                Timer(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Timer.setTextColor(Color.parseColor("#808080"));
                Pause();
                Start();
            }
        }.start();
    }

    public void Break10Not() {
        AgreeBotton.setVisibility(View.VISIBLE);
        DesagreeBotton.setVisibility(View.VISIBLE);
        Pergunta.setVisibility(View.VISIBLE);
    }

    public void Break10(){
        Timer.setTextColor(Color.parseColor("#FF6200EE"));
        CDT = new CountDownTimer(600000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                Timer(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Timer.setTextColor(Color.parseColor("#808080"));
                Pause();
                Start();
            }
        }.start();
    }
}