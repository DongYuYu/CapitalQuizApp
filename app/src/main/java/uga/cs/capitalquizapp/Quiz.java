package uga.cs.capitalquizapp;

public class Quiz {






    private long id;
    private String date;








    private int result;


    public Quiz() {
        this.id = -1;
        this.date = null;




        this.result = -1;
    }

    public Quiz(String date, int result) {


        this.date = date;
        this.result = result;
    }


    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }



    public String getDate(){
        return date;
    }




    public int getResult() {













        return result;
    }
}
