package com.example.quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String QUESTION_KEY = "Question";
    String ANSWER_KEY = "Answer";
    int REQ_CODE = 1234;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          textView= findViewById(R.id.question_text);
        Button mCheatButton = (Button) findViewById(R.id.show_answer_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CheatActivity.class);
                String question = textView.getText().toString();
                intent.putExtra(QUESTION_KEY, question);
                startActivityForResult(intent, REQ_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                String answer = data.getStringExtra(ANSWER_KEY);
                Toast.makeText(this, answer, Toast.LENGTH_SHORT).show();
            }
        }
    }
}