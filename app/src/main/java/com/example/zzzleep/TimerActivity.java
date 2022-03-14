package com.example.zzzleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class TimerActivity extends AppCompatActivity {

    private Button btnTimerStart, btnTimerEnd;
    private Chronometer timerHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);


        btnTimerStart = findViewById(R.id.counter_button_start);
        btnTimerEnd = findViewById(R.id.counter_button_end);
        timerHere = findViewById(R.id.timerHere);

        //optional animation
        btnTimerEnd.setAlpha(0);

        btnTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start timer
                timerHere.setBase(SystemClock.elapsedRealtime());
                timerHere.start();
                btnTimerEnd.animate().alpha(1).translationY(-80).setDuration(300).start();
                btnTimerStart.animate().alpha(0).setDuration(300).start();
            }
        });
    }
}