package uga.cs.capitalquizapp;

public class Question {

    private long id;
    private String state;
    private String capital;
    private String city1;

    private String city2;









    public Question() {
        this.id = -1;
        this.state = null;
        this.capital = null;
        this.city1 = null;
        this.city2 = null;
    }



    public Question(String[] line) {
        this.state = line[0];
        this.capital = line[1];
        this.city1 = line[2];
        this.city2 = line[3];
    }

    public Question(String state, String capital, String city1, String city2) {
        this.state = state;
        this.capital = capital;
        this.city1 = city1;
        this.city2 = city2;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getCapital() {
        return capital;
    }
}
