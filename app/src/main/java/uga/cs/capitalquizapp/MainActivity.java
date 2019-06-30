package uga.cs.capitalquizapp;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

//import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {






    public final String DEBUG_TAG = "StartQuizActivity";






    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    private QuestionsData  questionsData = null;
    private List<Question> questionsList;

    private Button startQuiz;
    private Button quizResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        Log.d( DEBUG_TAG, "StartQuizActivity.onCreate()" );


            //setContentView( R.layout.activity_review_job_leads );

            //recyclerView = (RecyclerView) findViewById( R.id.recyclerView );

            // use a linear layout manager for the recycler view
        layoutManager = new LinearLayoutManager(this );
            //recyclerView.setLayoutManager( layoutManager );

            // Create a JobLeadsData instance, since we will need to save a new JobLead to the dn.
            // Note that even though more activites may create their own instances of the JobLeadsData
            // class, we will be using a single instance of the JobLeadsDBHelper object, since
            // that class is a singleton class.
        questionsData = new QuestionsData( this );

            // Execute the retrieval of the job leads in an asynchronous way,
            // without blocking the UI thread.









        new MainActivity.RetrieveJobLeadTask().execute();
        //SQLiteStudioService.instance().start(this);
       // questionsData.close();
       // getApplicationContext().deleteDatabase("capital");

        startQuiz = (Button) findViewById(R.id.button);
        quizResults = (Button) findViewById(R.id.button2);
        startQuiz.setOnClickListener(new ButtonClickListener());
       // quizResults.setOnClickListener(new ReviewButtonClickListener());



        quizResults.setOnClickListener(new ReviewButtonClickListener());
    }

    public class RetrieveJobLeadTask extends AsyncTask<Void, Void, List<Question>> {

            // This method will run as a background process to read from db.


            @Override
            protected List<Question> doInBackground( Void... params ) {

                questionsData.open();






                Set<Integer> ids = new HashSet<>();

                Random ran = new Random();
                while (ids.size() < 5) {

                    ids.add(ran.nextInt(23) + 1);
                    //Log.d(DEBUG_TAG, String.valueOf(ids));
                }





                for (Integer i : ids){
                    Log.d(DEBUG_TAG, String.valueOf(i));
                }
                questionsList = questionsData.retrieveAllJobLeads(ids);

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
            protected void onPostExecute( List<Question> jobLeadsList ) {
                super.onPostExecute(jobLeadsList);
                // recyclerAdapter = new JobLeadRecyclerAdapter( jobLeadsList );
                // recyclerView.setAdapter( recyclerAdapter );
            }
    }

        @Override
        protected void onResume() {
            Log.d( DEBUG_TAG, "StartQuizActivity.onResume()" );
            if( questionsData != null )
                questionsData.open();
            super.onResume();
        }

        @Override
        protected void onPause() {
            Log.d( DEBUG_TAG, "StartQuizActivity.onPause()" );
            if( questionsData != null )
                questionsData.close();
            super.onPause();
        }

        // These activity callback methods are not needed and are for edational purposes only
        @Override
        protected void onStart() {
            Log.d( DEBUG_TAG, "StartQuizActivity.onStart()" );
            super.onStart();
        }

        @Override
        protected void onStop() {
            Log.d( DEBUG_TAG, "StartQuizActivity.onStop()" );
            questionsData.close();
            //File dbfile = getApplicationContext().getDatabasePath("capital");
            //SQLiteDatabase.deleteDatabase(dbfile);
            //getApplicationContext().deleteDatabase("capital");

            super.onStop();
        }

        @Override
        protected void onDestroy() {
            Log.d( DEBUG_TAG, "StartQuizActivity.onDestroy()" );
            super.onDestroy();
        }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick (View view) {
            Intent intent = new Intent(view.getContext(), NewPagerActivity.class);
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



    private class ReviewButtonClickListener implements View.OnClickListener {
        public void onClick (View view) {
            Intent intent = new Intent(view.getContext(), ReviewQuizActivity.class);
            view.getContext().startActivity(intent);
        }
    }
    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "StartQuizActivity.onRestart()" );
        super.onRestart();




    }
}







