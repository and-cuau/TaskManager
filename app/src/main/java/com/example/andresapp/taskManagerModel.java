package com.example.andresapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Task")
public class taskManagerModel {
    @ColumnInfo(name="task_id")
    @PrimaryKey(autoGenerate = true)
    int task_id;

    @ColumnInfo(name = "task")
    //???
    String task;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "time")
    String time;

    @ColumnInfo(name = "image")
    int image;


    @ColumnInfo(name = "is_selected")
    Boolean isSelected = false;

    public taskManagerModel(String task, String date, String time, int image){
        this.task = task;
        this.date = date;
        this.time = time;
        this.image = image;
    }

    public String getTask(){
        return task;
    } //???

    public String getw2(){
        return date;
    }

    public String getw3(){
        return time;
    }
    public int getImg(){
        return image;
    }


    public void setSelected(Boolean b) {
        isSelected = b;

    }

    public boolean isSelected() {
        return false;
    }

}
