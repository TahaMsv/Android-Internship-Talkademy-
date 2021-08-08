package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        TextView questionText = findViewById(R.id.question_text2);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String question = extras.getString(MainActivity.QUESTION_KEY);
            questionText.setText(question);
        }
        Button yesBtn = findViewById(R.id.true_button);
        Button noBtn = findViewById(R.id.false_button);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult("Yes");
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult("No");
            }
        });
    }

    private void setResult(String result) {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.ANSWER_KEY, result);
        setResult(RESULT_OK, intent);
        finish();
    }
}