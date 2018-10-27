package uga.cs.capitalquizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;










import java.util.Calendar;

public class QuestionsData {
    public static final String DEBUG_TAG = "JobLeadsData";

    private SQLiteDatabase db;
    private SQLiteOpenHelper questionsSqlOpenHelper;
    private static final String[] allColumns = {
            QuestionsSqlOpenHelper.QUESTIONS_COLUMN_ID,
            QuestionsSqlOpenHelper.QUESTIONS_COLUMN_STATE,
            QuestionsSqlOpenHelper.QUESTIONS_CAPITAL,
            QuestionsSqlOpenHelper.QUESTIONS_COLUMN_CITY1,
            QuestionsSqlOpenHelper.QUESTIONS_COLUMN_CITY2
    };




    private static final String[] alls = {
            QuestionsSqlOpenHelper.QUIZ_ID,
            QuestionsSqlOpenHelper.QUIZ_DATE,
            QuestionsSqlOpenHelper.QUIZ_RESULT

    };


    public QuestionsData( Context context ) {
        this.questionsSqlOpenHelper = QuestionsSqlOpenHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = questionsSqlOpenHelper.getWritableDatabase();

        Log.d( DEBUG_TAG, "JobLeadsData: db open" );
    }

    // Close the database
    public void close() {
        if( questionsSqlOpenHelper != null ) {
            questionsSqlOpenHelper.close();
            //SQLiteDatabase.deleteDatabase("capital");
            Log.d(DEBUG_TAG, "JobLeadsData: db closed");
        }
    }

    // Retrieve all job leads as a List.
    // This is how we restore persistent objects stored as rows in the job leads table in the database.
    // For each retrieved row, we create a new JobLead (Java object) instance and add it to the list.

    public List<Quiz> retrieveAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = null;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( QuestionsSqlOpenHelper.TABLE_QUIZ, alls,
                    null, null, null, null, null );
            // collect all job leads into a List
            if( cursor.getCount() > 0 ) {
                while( cursor.moveToNext() ) {
                    long id = cursor.getLong( cursor.getColumnIndex( QuestionsSqlOpenHelper.QUIZ_ID ) );
                    String date = cursor.getString( cursor.getColumnIndex( QuestionsSqlOpenHelper.QUIZ_DATE ) );
                    int result = cursor.getInt( cursor.getColumnIndex( QuestionsSqlOpenHelper.QUIZ_RESULT ) );


                    Quiz quiz = new Quiz( date, result );
                    quiz.setId( id );
                    quizzes.add( quiz );
                    Log.d( DEBUG_TAG, "Retrieved JobLead: " + quiz );
                }
            }
            Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return quizzes;
    }







    public List<Question> retrieveAllJobLeads(Set<Integer> ids) {
        ArrayList<Question> questions = new ArrayList<>();
        Cursor cursor = null;
        try {


            StringBuilder s = new StringBuilder();
            s.append(QuestionsSqlOpenHelper.QUESTIONS_COLUMN_ID + " in ");
            s.append("(");

            for (int id : ids) {
                s.append(id);
                s.append(",");
            }
            s.deleteCharAt(s.length() - 1);

            s.append(")");

            // Execute the select query and get the Cursor to iterate over the retrieved rows

            cursor = db.query( QuestionsSqlOpenHelper.TABLE_QUESTIONS, allColumns,
                    s.toString(), null, null, null, null );
            // collect all job leads into a List
            if( cursor.getCount() > 0 ) {
                while( cursor.moveToNext() ) {
                    long id = cursor.getLong( cursor.getColumnIndex( QuestionsSqlOpenHelper.QUESTIONS_COLUMN_ID ) );
                    String state = cursor.getString( cursor.getColumnIndex( QuestionsSqlOpenHelper.QUESTIONS_COLUMN_STATE ) );
                    String capital = cursor.getString( cursor.getColumnIndex( QuestionsSqlOpenHelper.QUESTIONS_CAPITAL ) );


                    String city1 = cursor.getString( cursor.getColumnIndex( QuestionsSqlOpenHelper.QUESTIONS_COLUMN_CITY1 ) );









                    String city2 = cursor.getString( cursor.getColumnIndex( QuestionsSqlOpenHelper.QUESTIONS_COLUMN_CITY2 ) );
                    Question question = new Question(state , capital, city1, city2 );
                    question.setId( id );



                    questions.add(question);


                    Log.d( DEBUG_TAG, "Retrieved JobLead: " + question );
                }
            }
            Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return questions;
    }





    // Store a new job lead in the database
    public long storeJobLead( Set<Integer> ids , int res) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the JobLead argument.
        // This is how we are providing persistence to a JobLead (Java object) instance
        // by storing it as a new row in the database table representing job leads.


        ContentValues quizValues = new ContentValues();
        quizValues.put( QuestionsSqlOpenHelper.QUIZ_DATE, String.valueOf(Calendar.getInstance().getTime()));
        quizValues.put( QuestionsSqlOpenHelper.QUIZ_RESULT, res );


        // Insert the new row into the database table;  the id (primary key) will be
        // automatically generated by the database system
        long id = db.insert( QuestionsSqlOpenHelper.TABLE_QUIZ, null, quizValues );

        // store the id in the JobLead instance, as it is now persistent


        for (Integer i : ids) {
            ContentValues idd = new ContentValues();
            idd.put( QuestionsSqlOpenHelper.RELATIONS_QUIZ_ID, id);
            idd.put( QuestionsSqlOpenHelper.RELATIONS_QUESTION_ID, i);
            db.insert( QuestionsSqlOpenHelper.TABLE_RELATIONS, null, idd);
        }
        Log.d( DEBUG_TAG, "Stored new job lead with id: " + String.valueOf( id ) );


        return id;
    }





}
