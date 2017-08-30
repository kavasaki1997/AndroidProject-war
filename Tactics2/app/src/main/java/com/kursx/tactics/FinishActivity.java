package com.kursx.tactics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zhenya on 25.10.16.
 */

public class FinishActivity extends Activity {
    TextView myTextView;
    private Button replayButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_activity);
        Intent intent = getIntent();
        String muveList = intent.getStringExtra("muveList");
        myTextView = (TextView) findViewById(R.id.textView3);
        myTextView.setText("Your score = " + muveList + "");
        replayButton= (Button) findViewById(R.id.replayButton);
        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }
}
