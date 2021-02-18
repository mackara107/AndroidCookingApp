package com.example.beginnercookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.widget.Button;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity{
    private FloatingActionButton profileButton;
    private FloatingActionButton cartButton;
    private Button timerButton;
    private TextView txt;

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

        txt = (TextView) findViewById(R.id.text);
        this.parse(txt);


    }

    private void parse(TextView t){
        Parser p = new Parser();
        InputStream is = null;
        try{
            is = getAssets().open("recipes.xml");
        }catch(IOException e){
        }
        String recipes = p.parseXML(is);
        t.setText(recipes);
        SpannableString ss = new SpannableString(recipes);
        ClickableSpan firstClick = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                startActivity(intent);
            }
        };
        ss.setSpan(firstClick, 0,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        t.setText(ss);
        t.setMovementMethod(LinkMovementMethod.getInstance());
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


