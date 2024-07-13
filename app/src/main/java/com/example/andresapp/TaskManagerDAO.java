package com.example.andresapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface TaskManagerDAO {
    @Insert
    public void addTask(taskManagerModel task);
    @Update
    public void updatePerson(taskManagerModel task);
    @Delete
    public void deleteTask(taskManagerModel task);
    @Query("select * from task")
    public List<taskManagerModel> getAllTask();
    @Query("select * from task where task_id==:task_id")
    public taskManagerModel getTask(int task_id);


}
