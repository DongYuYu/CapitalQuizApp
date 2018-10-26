package edu.uga.cs.statecapitalquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button startQuiz;
    private Button quizResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startQuiz = (Button) findViewById(R.id.button);
        quizResults = (Button) findViewById(R.id.button);
        startQuiz.setOnClickListener(new ButtonClickListener());
       // quizResults.setOnClickListener(new ReviewButtonClickListener());
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick (View view) {
            Intent intent = new Intent(view.getContext(), NewQuizActivity.class);
            view.getContext().startActivity(intent);

        }
    }
    /*
    private class ReviewButtonClickListener implements View.OnClickListener {
        @Override
        public void onCLick (View view) {
            Intent intent = new Intent(view.getContext(), ReviewQuizActivity.class);
            view.getContext().startActivity(intent);
        }
    }
    */

}
