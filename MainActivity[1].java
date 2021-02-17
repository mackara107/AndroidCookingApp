package com.example.beginnercookingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity{
    private FloatingActionButton profileButton;
    private FloatingActionButton cartButton;
    private Button timerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileButton = (FloatingActionButton)findViewById(R.id.profilebutton);
        profileButton.setOnClickListener(new buttonClick());

        cartButton = (FloatingActionButton) findViewById(R.id.cartbutton);
        cartButton.setOnClickListener(new buttonClick());

        timerButton = (Button) findViewById(R.id.timerbutton);
        timerButton.setOnClickListener(new buttonClick());
    }

    private class buttonClick implements View.OnClickListener{
        public void openProfileActivity(){
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        public void openCartActivity(){
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        }
        public void openTimerActivity(){
            Intent intent = new Intent(MainActivity.this, TimerActivity.class);
            startActivity(intent);
        }
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.profilebutton:
                    openProfileActivity();
                    break;
                case R.id.cartbutton:
                    openCartActivity();
                    break;
                case R.id.timerbutton:
                    openTimerActivity();
                    break;
            }
        }
    }
}


