package uga.cs.capitalquizapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


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
    private QuestionsData questionsData;
    private Set<Integer> iid;
   // private QuizData quizData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);
        Set<Integer> ids = new HashSet<>();








        questionsData = new QuestionsData(this);
        Random ran = new Random();
        while (ids.size() < 5) {

            ids.add(ran.nextInt(23) + 2);
            //Log.d(DEBUG_TAG, String.valueOf(ids));
        }
        //set question text for quiz
        questionView = (TextView) findViewById(R.id.questionView);

      //  questionView.setText(" 1. What is the capital of " + state);

        //submit button
        submitButton = (Button) findViewById(R.id.submitButton);

        //answer choices
        choices = (RadioGroup) findViewById(R.id.radioGroup);
        option1 = (RadioButton) findViewById(R.id.choice1);


        ///option1.setText("Atlanta");
        option2 = (RadioButton) findViewById(R.id.choice2);
        //option2.setText("Macon");

        option3 = (RadioButton) findViewById(R.id.choice3);


      //  option3.setText("Athens");

        //create a QuizData instance, so we can save a new Quiz to the db.
        //quizData = new QuizData(this);
        iid = ids;
        submitButton.setOnClickListener(new ButtonClickListener());

        new RetrieveJobLeadTask().execute(ids);
    }




    public class RetrieveJobLeadTask extends AsyncTask<Set<Integer>, Void, List<Question>> {

        // This method will run as a background process to read from db.


        @Override
        protected List<Question> doInBackground( Set<Integer>... ids ) {

            questionsData.open();



            List<Question> questionsList;






            for (Integer i : ids[0]){
                Log.d(DEBUG_TAG, String.valueOf(i));
            }
            questionsList = questionsData.retrieveAllJobLeads(ids[0]);

            Log.d( DEBUG_TAG, "RetrieveJobLeadTask: Job leads retrieved: " + questionsList.size() );

            for (Question q : questionsList) {
                Log.d(DEBUG_TAG, q.getCapital());
            }
            return questionsList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        @Override
        protected void onPostExecute( List<Question> questionList ) {
            super.onPostExecute(questionList);





            questionView.setText("what" + questionList.get(0).getState());
            option1.setText(questionList.get(0).getCapital());
            option2.setText(questionList.get(0).getCity1());
            option3.setText(questionList.get(0).getCity2());
            // recyclerAdapter = new JobLeadRecyclerAdapter( jobLeadsList );
            // recyclerView.setAdapter( recyclerAdapter );
        }
    }
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {









            new SaveLeadTask().execute(iid);
        }
    }




    public class SaveLeadTask extends AsyncTask<Set<Integer>, Void, Long> {

        // This method will run as a background process to read from db.


        @Override
        protected Long doInBackground( Set<Integer>... ids ) {

            questionsData.open();
            long qid = -1;
            for (Set<Integer> id : ids) {

                qid = questionsData.storeJobLead(id, 23);
            }
            return qid;


        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        @Override
        protected void onPostExecute(Long qid) {
            super.onPostExecute( qid);

            // Show a quick confirmation
            Toast.makeText( getApplicationContext(), "Job lead created for " + qid,
                    Toast.LENGTH_SHORT).show();

            // Clear the EditTexts for next use.
//            companyNameView.setText( "" );
//            phoneView.setText( "" );
//            urlView.setText( "" );
//            commentsView.setText( "" );

            Log.d( DEBUG_TAG, "Job lead saved: " + qid );
            // recyclerAdapter = new JobLeadRecyclerAdapter( jobLeadsList );
            // recyclerView.setAdapter( recyclerAdapter );
        }
    }
}
