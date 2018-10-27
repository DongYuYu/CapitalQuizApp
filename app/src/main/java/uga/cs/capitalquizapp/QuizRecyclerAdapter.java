package uga.cs.capitalquizapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class QuizRecyclerAdapter extends RecyclerView.Adapter<QuizRecyclerAdapter.QuizHolder> {




    public static final String DEBUG_TAG = "QuizRecyclerAdapter";









    public List<Quiz> quizList;


    public QuizRecyclerAdapter (List<Quiz> quizList) {


        this.quizList = quizList;



    }

    class QuizHolder extends RecyclerView.ViewHolder {

        TextView date;



        TextView result;




        public QuizHolder(View itemView) {

            super(itemView);





            date = (TextView) itemView.findViewById( R.id.date);


            result = (TextView) itemView.findViewById( R.id.result);

        }
    }




    @Override
    public QuizHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz, null);
        return new QuizHolder( view);
    }
    public void onBindViewHolder( QuizHolder holder, int position) {
        Quiz quiz = quizList.get(position);
        Log.d(DEBUG_TAG, String.valueOf(quiz.getResult()));



        holder.date.setText("Date: " + quiz.getDate());


        holder.result.setText("Result: " + String.valueOf(quiz.getResult()));


    }




    public int getItemCount() { return quizList.size();}



}

