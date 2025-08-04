package com.example.basicstopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView timerText;
    Button startButton, stopButton, resetButton;

    int mseconds = 0;
    boolean running=false;
    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(running){
                mseconds += 1;
                timerText.setText(formatTime(mseconds));
                handler.postDelayed(this,10);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        timerText = findViewById(R.id.timerText);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton); resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!running){
                    running = true;
                    handler.post(runnable);
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                running=false;
                mseconds = 0;
                timerText.setText(formatTime(mseconds));
            }
        });
    }

    String formatTime(int miliSeconds){
        int minutes = miliSeconds / 6000;
        int secs = (miliSeconds / 100)%60;
        int msecs = miliSeconds %100;
        return String.format("%02d:%02d:%02d", minutes, secs, msecs);
    }
}
