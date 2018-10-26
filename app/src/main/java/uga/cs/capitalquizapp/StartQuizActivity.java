package uga.cs.capitalquizapp;








import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * This is an activity controller class for listing the current job leads.
 * The current job leads are listed as a RecyclerView.
 */





public class StartQuizActivity extends AppCompatActivity {

    public static final String DEBUG_TAG = "StartQuizActivity";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerAdapter;

    private QuestionsData  questionsData = null;
    private List<Question> questionsList;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        Log.d( DEBUG_TAG, "StartQuizActivity.onCreate()" );

        super.onCreate( savedInstanceState );
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
        new RetrieveJobLeadTask().execute();

    }

    public class RetrieveJobLeadTask extends AsyncTask<Void, Void, List<Question>> {

        // This method will run as a background process to read from db.


        @Override

        protected List<Question> doInBackground( Void... params ) {

            questionsData.open();






            int[] ids = new int[5];

            Random ran = new Random();
            for (int i = 0; i < 5; i++) {
                ids[i] = ran.nextInt(23);
            }
            //questionsList = questionsData.retrieveAllJobLeads(ids);

            Log.d( DEBUG_TAG, "RetrieveJobLeadTask: Job leads retrieved: " + questionsList.size() );

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
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "StartQuizActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "StartQuizActivity.onRestart()" );
        super.onRestart();
    }
}

