package com.example.beginnercookingapp;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    private long startTimeMsecs = 0;
    private long remainingTime = startTimeMsecs;
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
    private TextView countdownTextView;
    private EditText editText;

    private Button setButton;
    private Button startPauseButton;
    private Button resetButton;

    private CountDownTimer countDownTimer;
    private boolean timerOn;

    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        notificationManager = NotificationManagerCompat.from(this); //creates new notification

        countdownTextView = findViewById(R.id.countdownText);
        editText = findViewById(R.id.editTextTime);

        setButton = findViewById(R.id.setButton);
        startPauseButton = findViewById(R.id.startpauseButton);
        resetButton = findViewById(R.id.resetButton);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                if(input.length() == 0) {                       //check if input is 0 or nothing
                    Toast.makeText(TimerActivity.this, "Please enter a time", Toast.LENGTH_SHORT).show();
                    return;
                }
                int time = Integer.parseInt(input);
                if(time == 0) {                                 //check if input is 0 again just in case they put many 0's
                    Toast.makeText(TimerActivity.this, "Please enter a time", Toast.LENGTH_SHORT).show();
                    return;
                }
                setTimer(time);
                editText.setText("");
            }
        });

        startPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerOn) {
                    pauseTimer();
                }
                else {
                    startTimer();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateTimerText();
    }

    private void setTimer(int time) {                              //time input is in format: hours, minutes, seconds
        seconds = time % 100;                                      //get seconds from input ex) 01:30:30 = 13030, 13030 % 100 = 30
        minutes = ((time - seconds) % 10000) / 100;                //get minutes from input ex) 13030 - 30 = 13000, 13000 % 10000 = 3000, 3000 / 100 = 30
        hours = (time - (minutes * 100) - seconds) / 10000;        //get hours from input ex) 13030 - 3000 - 30 = 10000, 10000 / 10000 = 1
        if(seconds >= 60) {                                        //check if seconds is over 59, add 1 minute if true
            minutes += 1;
            seconds -= 60;
        }
        if(minutes >= 60) {                                        //check if minutes is over 59, add 1 hour if true
            hours += 1;
            minutes -= 60;
        }
        startTimeMsecs = (long)((hours * 3600000) + (minutes * 60000) + (seconds * 1000)); //convert time into msecs
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        startService();
        countDownTimer = new CountDownTimer(remainingTime, 1000) { //counts down by 1000 msecs or 1 sec
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timerOn = false;
                startPauseButton.setText("Start");
                sendNotification();
                stopService();
            }
        }.start();
        timerOn = true;                         //timer is on
        startPauseButton.setText("Pause");      //button now says pause instead of start
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerOn = false;
        startPauseButton.setText("Start");
        stopService();
    }

    private void resetTimer() {
        if(timerOn) {                           //reset button also stops timer
            countDownTimer.cancel();
            startPauseButton.setText("Start");
        }
        remainingTime = startTimeMsecs;         //reset time
        updateTimerText();
        stopService();
    }

    private void updateTimerText() {
        String timerFormat;
        int hours = (int)(remainingTime / 1000) / 3600;             //convert remaining time in msecs to hours ex) 4000000 / 1000 = 4000, 4000 / 3600 = 1 hour
        int minutes = (int)((remainingTime / 1000) % 3600) / 60;    //convert remaining time in msecs to minutes ex) 4000000 / 1000 = 4000, 4000 % 3600 = 400, 400 / 60 = 6 minutes
        int seconds = (int)((remainingTime / 1000) % 60);           //convert remaining time in msecs to seconds ex) 4000000 / 1000 = 4000, 4000 % 60 = 40 seconds
        if(hours > 0)
            timerFormat = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        else
            timerFormat = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countdownTextView.setText(timerFormat);
    }

    private void sendNotification() {
        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.alarm_black)
                .setContentTitle("Timer Alarm!")
                .setContentText("Timer is done!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .build();
        notificationManager.notify(1, notification);
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, TimerService.class);
        startService(serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, TimerService.class);
        stopService(serviceIntent);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
