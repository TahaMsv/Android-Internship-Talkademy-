package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    String ANSWER_KEY = "Answer";
    String QUESTION_KEY = "Question";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        TextView questionText = findViewById(R.id.question_text2);
        Bundle extras=getIntent().getExtras();
        String question = extras.getString(QUESTION_KEY);
        questionText.setText(question);
        Button yesBtn = findViewById(R.id.true_button);
        Button noBtn = findViewById(R.id.false_button);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(ANSWER_KEY, "Yes");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(ANSWER_KEY, "No");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}