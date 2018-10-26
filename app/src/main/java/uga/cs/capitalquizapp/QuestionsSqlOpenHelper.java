package uga.cs.capitalquizapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * This is a SQLiteOpenHelper class, which Android uses to create, upgrade, delete an SQLite database
 * in an app.
 *
 * This class is a singleton, following the Singleton Design Pattern.
 * Only one instance of this class will exist.  To make sure, the
 * only constructor is private.
 * Access to the only instance is via the getInstance method.
 */
public class QuestionsSqlOpenHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "QuestionsSqlOpenHelper";

    private static final String DB_NAME = "capital.db";
    private static final int DB_VERSION = 1;




    private static final String TAG = "ReadCSV";
    // Define all names (strings) for table and column names.
    // This will be useful if we want to change these names later.
    public static final String TABLE_QUESTIONS = "questions";
    public static final String QUESTIONS_COLUMN_ID = "_id";
    public static final String QUESTIONS_COLUMN_STATE = "state";
    public static final String QUESTIONS_CAPITAL = "cap";
    public static final String QUESTIONS_COLUMN_CITY1 = "city1";
    public static final String QUESTIONS_COLUMN_CITY2 = "city2";

    public static final String TABLE_RELATIONS = "relations";
    public static final String RELATIONS_QUESTION_ID = "question_id";
    public static final String RELATIONS_QUIZ_ID = "quiz_id";


    private  static final String tag = "SQLIDEG";
    public static final String TABLE_QUIZ = "quiz";
    public static final String QUIZ_ID ="quiz_id";
    public static final String QUIZ_DATE = "quiz_data";
    public static final String QUIZ_RESULT = "quiz_res";
    // This is a reference to the only instance for the helper.
    private static QuestionsSqlOpenHelper helperInstance;

    // A Create table SQL statement to create a table for job leads.
    // Note that _id is an auto increment primary key, i.e. the database will
    // automatically generate unique id values as keys.
    private static final String CREATE_QUESTIONS =
            "create table " + TABLE_QUESTIONS + " ("
                    + QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUESTIONS_COLUMN_STATE + " TEXT, "
                    + QUESTIONS_CAPITAL + "  TEXT, "
                    + QUESTIONS_COLUMN_CITY1 + " TEXT, "
                    + QUESTIONS_COLUMN_CITY2 + " TEXT"
                    + ")";


    private static final String CREATE_QUIZ =


            "create table " + TABLE_QUIZ + " ("
                    + QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "


                    + QUIZ_DATE + " TEXT, "
                    + QUIZ_RESULT + " INTEGER"





                    +")";

    private static final String CREATE_RELATION =

            "create table " + TABLE_RELATIONS +" ("
            + RELATIONS_QUESTION_ID + " INTEGER,"
            + RELATIONS_QUIZ_ID + " INTEGER"
            + ")";

    private static InputStream csv = null;
    // Note that the constructor is private!
    // So, it can be called only from
    // this class, in the getInstance method.
    private QuestionsSqlOpenHelper( Context context ) {

        super( context, DB_NAME, null, DB_VERSION );

        Resources resource = context.getResources();
        csv = resource.openRawResource( R.raw.state_capitals);
    }
    public static ArrayList<String[]> getDbTableDetails() {
        Cursor c = helperInstance.getWritableDatabase().rawQuery(
                "SELECT * FROM questions", null);
        ArrayList<String[]> result = new ArrayList<String[]>();
        int i = 0;
        result.add(c.getColumnNames());

        //Log.d(tag, c.getColumnNames().toString());
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String[] temp = new String[c.getColumnCount()];
            for (i = 0; i < temp.length; i++) {
                temp[i] = c.getString(i);

                Log.d(tag, temp[i].toString());




            }
            result.add(temp);
        }

        c.close();
        return result;
    }
    // Access method to the single instance of the class
    public static synchronized QuestionsSqlOpenHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new QuestionsSqlOpenHelper( context.getApplicationContext() );
            //initiateDB(helperInstance.getWritableDatabase(), context);
            //getDbTableDetails();

        }



        return helperInstance;
    }

    // We must override onCreate method, which will be used to create the database if
    // it does not exist yet.
    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( CREATE_QUESTIONS );




        db.execSQL( CREATE_QUIZ);

        db.execSQL( CREATE_RELATION);

        initiateDB(db);
        Log.d( DEBUG_TAG, "Table " + TABLE_QUESTIONS + " created" );
    }

    // We should override onUpgrade method, which will be used to upgrade the database if
    // its version (DB_VERSION) has changed.  This will be done automatically by Android
    // if the version will be bumped up, as we modify the database schema.
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_QUESTIONS );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUESTIONS + " upgraded" );
    }

    private static void initiateDB(SQLiteDatabase db, Context context ) {

        try {
            Resources res = context.getResources();
            InputStream in_s = res.openRawResource( R.raw.state_capitals);

            CSVReader reader = new CSVReader(new InputStreamReader( in_s));



            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {



                ContentValues values = new ContentValues();
                values.put(QuestionsSqlOpenHelper.QUESTIONS_COLUMN_STATE, nextLine[0]);
                values.put(QuestionsSqlOpenHelper.QUESTIONS_CAPITAL, nextLine[1]);
                values.put(QuestionsSqlOpenHelper.QUESTIONS_COLUMN_CITY1, nextLine[2]);

                values.put(QuestionsSqlOpenHelper.QUESTIONS_COLUMN_CITY2, nextLine[3]);

                // Insert the new row into the database table;  the id (primary key) will be
                // automatically generated by the database system
                long id = db.insert(QuestionsSqlOpenHelper.TABLE_QUESTIONS, null, values);

                // store the id in the JobLead instance, as it is now persistent


                Log.d(DEBUG_TAG, "Stored new job lead with id: " + String.valueOf(id));




            }
        } catch (Exception e) {
            Log.e( TAG, e.toString() );
        }





    }
    private static void initiateDB(SQLiteDatabase db) {

        try {


            CSVReader reader = new CSVReader(new InputStreamReader( csv));



            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {



                ContentValues values = new ContentValues();
                values.put(QuestionsSqlOpenHelper.QUESTIONS_COLUMN_STATE, nextLine[0]);
                values.put(QuestionsSqlOpenHelper.QUESTIONS_CAPITAL, nextLine[1]);
                values.put(QuestionsSqlOpenHelper.QUESTIONS_COLUMN_CITY1, nextLine[2]);

                values.put(QuestionsSqlOpenHelper.QUESTIONS_COLUMN_CITY2, nextLine[3]);

                // Insert the new row into the database table;  the id (primary key) will be
                // automatically generated by the database system
                long id = db.insert(QuestionsSqlOpenHelper.TABLE_QUESTIONS, null, values);

                // store the id in the JobLead instance, as it is now persistent


                Log.d(DEBUG_TAG, "Stored new job lead with id: " + String.valueOf(id));




            }
        } catch (Exception e) {
            Log.e( TAG, e.toString() );
        }





    }
}
