package edu.uga.cs.statecapitalquiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class NewQuizActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "NewQuizActivity";


    private TextView questionView;
    private RadioGroup choices;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private int score;
    private Button submitButton;
    private boolean correctAnswer;
    private String state;

   // private QuizData quizData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        //set question text for quiz
        questionView = (TextView) findViewById(R.id.textView);
        questionView.setText(" 1. What is the capital of " + state);

        //submit button
        submitButton = (Button) findViewById(R.id.submitButton);

        //answer choices
        choices = (RadioGroup) findViewById(R.id.radioGroup);
        option1 = (RadioButton) findViewById(R.id.choice1);
        option1.setText("Atlanta");
        option2 = (RadioButton) findViewById(R.id.choice2);
        option2.setText("Macon");
        option3 = (RadioButton) findViewById(R.id.choice3);
        option3.setText("Athens");

        //create a QuizData instance, so we can save a new Quiz to the db.
        //quizData = new QuizData(this);

        submitButton.setOnClickListener(new ButtonClickListener());
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (correctAnswer) {
                score++;
            }
        }
    }
}
