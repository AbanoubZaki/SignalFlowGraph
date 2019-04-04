package com.train.abanoubashraf.signalflowgraphsolver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStartSolving, btnHowToSolve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartSolving = (Button)findViewById(R.id.btnStartSolving);
        btnHowToSolve = (Button)findViewById(R.id.btnHowToSolve);

        btnStartSolving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), graphBuilder.class));
            }
        });

        btnHowToSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HowToActivity.class));
            }
        });
    }
}
