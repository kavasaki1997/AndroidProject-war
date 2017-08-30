package com.kursx.tactics;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private Button fireButton;
    float x;
    float y;
    String sMove;



    private int muveList=0;
    TextView myTextView;


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    Activity activity;

    private Field field;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        myTextView = (TextView) findViewById(R.id.textView);
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                muveList += 1;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myTextView.setText(muveList + "");
                    }
                });

            }
        }, 500, 500);
        super.onCreate(savedInstanceState);


        field = (Field) findViewById(R.id.field);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        field.SetSize(width, height);
        field.start();
        field.setOnTouchListener(this);
        fireButton = (Button) findViewById(R.id.button);
        fireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                field.fire();
            }
        });
        field.SetActivity(this);
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                sMove = "Move: " + x + "," + y;
                field.moveShip((int) y);
                break;
            case MotionEvent.ACTION_MOVE: // движение
                sMove = "Move: " + x + "," + y;
                field.moveShip((int) y);
                break;


        }
        return true;
    }
boolean flag = false;
    public void gameOver() {
        if (!flag) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    flag=true;
                    Intent intent = new Intent(MainActivity.this, FinishActivity.class);
                    intent.putExtra("muveList", String.valueOf(muveList));

                    startActivity(intent);


                }
            });
        }
    }
    public int getMuveList() {
        return muveList;
    }



}
