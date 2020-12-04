package com.example.karllita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //RestTime Rest = new RestTime();

    //Switch

    CountDownTimer CDT;
    CountDownTimer Rest5;

    TextView Timer;
    TextView Pomos;
    EditText Time;

    Button StateButton;
    Button SetTimer;
    Button PomosButton;
    Button BreakButton;

    long TotalTime = 10000;
    long RemainingTime = TotalTime;

    boolean Started = true;
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

        StateButton = findViewById(R.id.StateButton);
        PomosButton = findViewById(R.id.DailyPomos);
        SetTimer = findViewById(R.id.SetTimer);
        BreakButton = findViewById(R.id.Break);


        StateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                State();
                SetTimer.setVisibility(View.INVISIBLE);
                BreakButton.setVisibility(View.VISIBLE);
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
                reset();
                Rest();

            }
        });
    }

    public void SetTimer (long mls){
        TotalTime = mls;
        reset();
    }

    public void reset (){
        RemainingTime = TotalTime;
        Timer(RemainingTime);
    }

    public void State (){

        if(Started){
            Start();
        }else{
            Pause();
        }

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
                reset();
                Rest();
            }
        }.start();
        Started = false;
        StateButton.setText("Pausar");

    }

    public void Pause (){
        CDT.cancel();
        Started = true;
        StateButton.setText("Continuar");
    }

    public void Timer(long RemainingTime){

        String ActualTime = "" + TimeUnit.MILLISECONDS.toMinutes(RemainingTime) + ":";

        if( (RemainingTime % 60000 / 1000) < 10){
            ActualTime += "0";
        }

        ActualTime += RemainingTime % 60000 / 1000;

        Timer.setText(ActualTime);
    }

    public void Rest(){
        Rest5 = new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                Timer(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                reset();
                Start();
            }
        }.start();
    }
}