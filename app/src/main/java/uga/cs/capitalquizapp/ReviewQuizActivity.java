package uga.cs.capitalquizapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

/*
 * This is an activity for listing the current quizzes as a RecyclerView
 */
public class ReviewQuizActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "ReviewQuizActivity";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    private QuestionsData quizData = null;
    private List<Quiz> quizList;

    @Override
    protected void onCreate(Bundle savedOnInstanceState) {


        super.onCreate(savedOnInstanceState);
        Log.d(DEBUG_TAG, "ReviewQuizActivity.onCreate()");

        //super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_quiz);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //use a linear layout manager for the recycler view
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //create a QuizData instance, so that we can save a new Quiz to the db.
        quizData = new QuestionsData(this);


        //Execute the retrieval of quiz in an asynchronous way, without blocking the UI thread
        new RetrieveQuizTask().execute();
    }

    public class RetrieveQuizTask extends AsyncTask<Void, Void, List<Quiz>> {

        //this method will run as a background process to read from db.
        @Override
        protected List<Quiz> doInBackground(Void... params) {
            quizData.open();


            quizList = quizData.retrieveAllQuizzes();

            Log.d(DEBUG_TAG, "RetrieveQuizTask: Quiz retrieved: " + quizList.size());

            return quizList;
        }


        //This method will automatically be called by Android once the Db reading background process
        //is finished. It will then create and set an adapter to provide values for the RecyclerView.


        // @Override
        protected void onPostExecute(List<Quiz> quizList) {
            super.onPostExecute(quizList);
            recyclerAdapter = new QuizRecyclerAdapter(quizList);
            recyclerView.setAdapter(recyclerAdapter);
        }

    }
}
