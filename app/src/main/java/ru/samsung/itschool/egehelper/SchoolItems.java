package ru.samsung.itschool.egehelper;

public class SchoolItems {
    int  score, time,  operation = 0;
    String name;
    static int count = 0;

    public SchoolItems(String name) {
        this.score = 0;
        this.time = 0;
        this.name = name;
        count++;
    }

    public void setStatistic(int score, int time){
        this.score += score;
        this.time += time;
        operation += 1;
    }

    public int getSredScore(){
        if(operation == 0){
            return 0;
        } else {
            return score / operation;
        }
    }

    public int getSredTime(){
        if(operation == 0){
            return 0;
        } else {
            return time / operation;
        }
    }

    public int getCount(){ return count; }

    public void setCountNull(){
        this.count = 0;
    }
}
