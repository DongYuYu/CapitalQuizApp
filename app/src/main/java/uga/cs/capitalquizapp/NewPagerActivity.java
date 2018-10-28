package uga.cs.capitalquizapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class NewPagerActivity extends AppCompatActivity {



    public final static int qs = 6;
    public static Question[] questions = new Question[qs];
    NewPagerAdapter newPagerAdapter;
    QuestionsData questionsData = null;
    private static final int[][] per = new int[][]{{0,1,2},{0,2,1},{1,2,0},{1,0,2},{2,1,0},{2,0,1}};
    Random ran;
    Set<Integer> ids;
    private static int count = 0;
    public static final String DEBUG_TAG = "newpager";


    ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);





        count = 0;

        newPagerAdapter = new NewPagerAdapter(getSupportFragmentManager(), questions.length + 1);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(newPagerAdapter);
        questionsData = new QuestionsData(this);








        ids = new HashSet<>();










        questionsData = new QuestionsData(this);


        ran = new Random();
        while (ids.size() < qs) {

            ids.add(ran.nextInt(50) + 2);
            //Log.d(DEBUG_TAG, String.valueOf(ids));
        }
        new RetrieveJobLeadTask().execute(ids);

//        questions[0] = new Question("Taiwan", "Taipei", "Tainan", "Tainan");
//        questions[1] = new Question("China", "Taipei", "Tainan", "Tainan");
//        questions[2] = new Question("Taiwan", "Taipei", "Tainan", "Tainan");
    }
    public void loadView(TextView textView, String description, RadioButton option1, String capital, RadioButton option2, String city1, RadioButton option3, String city2, final RadioGroup choices, Button button, final int pos) {

        if (pos > questions.length) {
            textView.setText(description);

            button.setText("Save the result");


            choices.setVisibility(View.INVISIBLE);
























            button.setOnClickListener(new View.OnClickListener(){


                @Override
                public void onClick(View v) {


                 new SaveTask().execute(ids);



                    //Toast.makeText(getApplicationContext(), String.valueOf(choices.getCheckedRadioButtonId()), Toast.LENGTH_LONG).show();
                }
            });








            return;
        }
        int rand = ran.nextInt(6);





        final int[] p = per[rand];
        String[] s = new String[3];
        s[p[0]] = capital;


        s[p[1]] = city1;

        s[p[2]] = city2;



        textView.setText("What's the capital of " + description + " ?");
        option1.setText(s[0]);
        option2.setText(s[1]);




        option3.setText(s[2]);

        //choices.clearCheck();






        button.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Log.d(DEBUG_TAG, "onClick");
                for (int i = 0; i < choices.getChildCount(); i++) {
                    RadioButton radio = (RadioButton) choices.getChildAt(i);

                    //Log.d(DEBUG_TAG, radio.getText().toString());
                    Toast.makeText(getApplicationContext(), radio.getText(), Toast.LENGTH_LONG);
                    if (radio.isChecked()) {



                        if(i == p[0]) {
                            count = count + 1;
                            Toast.makeText(getApplicationContext(), "you get correct", Toast.LENGTH_LONG).show();
                        }

                    }
                }




                //Toast.makeText(getApplicationContext(), String.valueOf(choices.getCheckedRadioButtonId()), Toast.LENGTH_LONG).show();
            }
        });

//        choices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checked) {
//
//
//
//
//
//
//                RadioButton rad = (RadioButton) findViewById(checked);
//
//                Toast.makeText(getApplicationContext(), rad.getText(), Toast.LENGTH_LONG).show();
//
//            }
//        });
    }
    public static class PlaceholderFragment extends Fragment {




        private int index;
        private TextView textView;

        private RadioGroup choices;
        private RadioButton option1;
        private RadioButton option2;
        private RadioButton option3;


        private static final String ARG_SECTION_NUMBER = "section_number";

        private Button button;

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();




            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);


            return fragment;
        }







        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                index = getArguments().getInt(ARG_SECTION_NUMBER);
            } else {



                index = -1;
            }
        }









        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_new_question, container, false);
            textView = (TextView) rootView.findViewById(R.id.questionView);
            choices = (RadioGroup) rootView.findViewById(R.id.radioGroup);
            option1 = (RadioButton) rootView.findViewById(R.id.choice1);

            button = (Button) rootView.findViewById(R.id.submitButton);

            option2 = (RadioButton) rootView.findViewById(R.id.choice2);
            option3 = (RadioButton) rootView.findViewById(R.id.choice3);
            return rootView;
        }
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);





            if (NewPagerActivity.class.isInstance(getActivity())) {




                if (index > questions.length) {
                    final String description = "your score is " + count;

                    ((NewPagerActivity) getActivity()).loadView(textView, description, option1, "", option2, "", option3, "", choices, button, index);
                    return;
                }
                final String description = questions[index - 1].getState();
                final String capital = questions[index - 1].getCapital();








                final String city1 = questions[index - 1].getCity1();








                final String city2 = questions[index - 1].getCity2();




                Log.d(DEBUG_TAG, String.valueOf(index));





                ((NewPagerActivity) getActivity()).loadView(textView, description, option1, capital, option2, city1, option3, city2, choices, button, index);
            }
        }
    }






    public class NewPagerAdapter extends FragmentPagerAdapter {


        int size;
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }


        public NewPagerAdapter(FragmentManager fm, int size) {
            super(fm);




            this.size = size;
        }


        public int getCount() {return size; }
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
            questionsList.toArray(questions);
            return questionsList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        @Override
        protected void onPostExecute( List<Question> questionList ) {
            super.onPostExecute(questionList);





//            questionView.setText("what" + questionList.get(0).getState());
//            option1.setText(questionList.get(0).getCapital());
//            option2.setText(questionList.get(0).getCity1());
//            option3.setText(questionList.get(0).getCity2());
            // recyclerAdapter = new JobLeadRecyclerAdapter( jobLeadsList );
            // recyclerView.setAdapter( recyclerAdapter );
        }
    }
    public class SaveTask extends AsyncTask<Set<Integer>, Void, Long> {

        // This method will run as a background process to read from db.


        @Override
        protected Long doInBackground( Set<Integer>... ids ) {

            questionsData.open();
            long qid = -1;
            for (Set<Integer> id : ids) {
                qid = questionsData.storeJobLead(id, count);
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
            Toast.makeText( getApplicationContext(), "Result Saved",
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
