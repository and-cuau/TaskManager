package com.example.andresapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.example.andresapp.databinding.ActivityMainBinding;


import com.google.android.gms.tasks.Task;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.android.gms.tasks.OnCompleteListener;
import androidx.annotation.NonNull;
import com.google.firebase.FirebaseApp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.app.AlarmManager;
import java.util.Calendar;


//hey


import android.content.Context;





public class MainActivity extends AppCompatActivity {

    AlarmManager myAlarm;
    RecyclerViewAdapter adapter;

    RecyclerView recyclerView;
    EditText taskEdit;
    EditText dateEdit;
    EditText timeEdit;

    EditText etToken;
    Button saveButton, deleteButton, getButton;
    TaskDatabase taskDB;
    private ActivityMainBinding binding;
    //private ActivityMainBinding binding;
    private MaterialTimePicker picker;
    private Calendar cal;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;



    private DatePickerDialog datePickerDialog;

    private TimePickerDialog timePickerDialog;

    private Button dateButton;
    private Button timeButton;

    List<taskManagerModel> taskList;

    //Resources res = getResources();
    ArrayList<taskManagerModel> taskManagerModels = new ArrayList<>();
    int[] images = {R.drawable.baseline_10k_24, R.drawable.baseline_album_24, R.drawable.baseline_assignment_turned_in_24, R.drawable.baseline_attractions_24,R.drawable.baseline_ballot_24,R.drawable.baseline_blind_24,R.drawable.baseline_card_membership_24, R.drawable.baseline_catching_pokemon_24, R.drawable.baseline_cottage_24 ,R.drawable.baseline_elderly_24,  R.drawable.baseline_blind_24, R.drawable.baseline_celebration_24};
    private void setup() {
        String[] list = getResources().getStringArray(R.array.amino_acids_full_txt);
        String[] list2 = getResources().getStringArray(R.array.idk_full_txt);
        String[] list3 = getResources().getStringArray(R.array.idk2_full_txt);


        for (int i = 0; i < list.length; i++){
            taskManagerModel tmm = new taskManagerModel(list[i], list2[i], list3[i], images[i]);
            taskManagerModels.add(tmm);
            addTaskInBackground(tmm);
        }
    }

    private static final String TAG = "MainActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        // setContentView(binding.getRoot());
         setContentView(R.layout.activity_main);
        //setContentView(binding.getRoot());



        taskEdit = findViewById(R.id.taskEdit);
       // dateEdit = findViewById(R.id.dateEdit);
        //timeEdit = findViewById(R.id.timeEdit);

       // etToken = findViewById(R.id.etToken);

//        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0); wtf was i thinking
//        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        createNotificationChannel();

        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
        getButton = findViewById(R.id.getTasks);

        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        taskDB = Room.databaseBuilder(getApplicationContext(),TaskDatabase.class,"TasKDB").addCallback(myCallBack).addMigrations(TaskDatabase.MIGRATION_1_2).build();

        saveButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              String task = taskEdit.getText().toString();
                                              String date = dateButton.getText().toString();
                                              String time = timeButton.getText().toString();
                                              taskManagerModel tmm = new taskManagerModel(task, date, time, 2131230844);
                                              // taskDB.getTaskDAO().addTask(tmm);
                                              addTaskInBackground(tmm);
                                              taskManagerModels.add(tmm);
                                              //adapter.selectedItems.add(false);  testing thing

                                              // Boolean arr[] = new

                                              adapter.notifyDataSetChanged();
                                              setAlarm();

                                          }
                                      });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = "";
// Delete selected items
                ArrayList<taskManagerModel> selectedTasks = new ArrayList<>();
                for (int i = 0; i< taskManagerModels.size(); i++) {
                    word = word + String.valueOf(adapter.selectedItems[i]); //word = word + String.valueOf(adapter.selectedItems.get(i));
                    if (taskManagerModels.get(i).isSelected) {   //  if (adapter.selectedItems.get(i)) {  // adapter.selectedItems[i]
                        selectedTasks.add(taskManagerModels.get(i));
                        deleteTaskInBackground(taskManagerModels.get(i));
                    }
                }
                taskManagerModels.removeAll(selectedTasks);
                Toast.makeText(MainActivity.this, word, Toast.LENGTH_SHORT).show();

                adapter.notifyDataSetChanged();
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getTaskInBackground();





            }
        });






        recyclerView = findViewById(R.id.mRecyclerView);

        setup();
        String strnum = String.valueOf(images[2]);
        Log.d("my num", strnum);

        adapter = new RecyclerViewAdapter(MainActivity.this, taskManagerModels);

        recyclerView.setAdapter(adapter); // ???

        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // ???


    initDatePicker();
    initTimePicker();
    dateButton = findViewById(R.id.datePickerButton);
    dateButton.setText(getTodaysDate());
    //timeButton.setText();
       //  timeButton = findViewById(R.id.timePickerButton);
       // timeButton.setText("hey");



    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                // dateButton.setText(getTodaysDate());
                Log.d(TAG, "test passed");
            }
        };

        // AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_Material_Light_Dialog_Alert);
        cal = Calendar.getInstance();
        int year= cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = R.style.CustomDatePickerDialogTheme;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
//179223660158510571

        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, "OK", datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel", datePickerDialog);
    }


    private void initTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute)
            {
                //hour = hour + 1;
                String time = makeTimeString(hour, minute);
                timeButton.setText(time);
                cal = Calendar.getInstance();
                cal.set(Calendar.HOUR_OF_DAY,hour);
                cal.set(Calendar.MINUTE,minute);
                cal.set(Calendar.SECOND,0);
                cal.set(Calendar.MILLISECOND,0);
                String mill = String.valueOf(cal.getTimeInMillis());
                Log.d(TAG, "milli initTimePick " +mill );


            }
        };

        // AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_Material_Light_Dialog_Alert);
        cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        timeButton = findViewById(R.id.timePickerButton);
        int style = R.style.CustomTimePickerDialogTheme;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hour, minute, false);
        //timeButton.setText("test");

//179223660158510571
    }




    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + " " + day + " " + year;
    }



    private String getMonthFormat(int month){
        if (month ==1)
            return "JAN";
        if (month ==2)
            return "FEB";
        if (month ==3)
            return "MAR";
        if (month ==4)
            return "APR";
        if (month ==5)
            return "MAY";
        if (month ==6)
            return "JUN";
        if (month ==7)
            return "JUL";
        if (month ==8)
            return "AUG";
        if (month ==9)
            return "SEP";
        if (month ==10)
            return "OCT";
        if (month ==11)
            return "NOV";
        if (month ==12)
            return "DEC";
        return "JAN";

    }

    private String makeTimeString(int hour, int minute){
        return hour + " " + minute;
    }

private String getTodaysDate(){
    cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    month = month + 1;
    int day = cal.get(Calendar.DAY_OF_MONTH);
    return makeDateString(day,month,year);
}

    public void addTaskInBackground(taskManagerModel tmm){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                taskDB.getTaskDAO().addTask(tmm);
                //taskDB.getTaskDAO().deleteTask();

                //taskList = taskDB.getTaskDAO().getAllTask();

                //on finishing task

                handler.post(new Runnable(){
                    @Override
                    public void run(){
                        Toast.makeText(MainActivity.this, "Added to database", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }



    public void deleteTaskInBackground(taskManagerModel tmm){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task
                taskDB.getTaskDAO().deleteTask(tmm);
/////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////

                //on finishing task

                handler.post(new Runnable(){
                    @Override
                    public void run(){
                        Toast.makeText(MainActivity.this, "Deleted from database", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void getTaskInBackground(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // background task

                taskList = taskDB.getTaskDAO().getAllTask();


                //on finishing task

                handler.post(new Runnable(){
                    @Override
                    public void run(){
                        StringBuilder sb = new StringBuilder();
                        for(taskManagerModel t : taskList){
                            sb.append(t.getTask());
                            sb.append("\n");

                        }
                        String finalData = sb.toString();
                        Toast.makeText(MainActivity.this, ""+finalData, Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }



    public void openDatePicker(View view){
        datePickerDialog.show();
    }
    public void openTimePicker(View view){
        timePickerDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Activity Started");
        // The activity is about to become visible
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Activity Resumed");
        // The activity has become visible (it is now "resumed")
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Activity Paused");
        // Another activity is taking focus (this activity is about to be "paused")
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Activity Stopped");
        // The activity is no longer visible (it is now "stopped")
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Activity Destroyed");
        // The activity is about to be destroyed
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Activity Restarted");
        // The activity is about to be restarted after stopping
    }

    private void cancelAlarm(){
        Intent intent = new Intent(this,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        if (alarmManager == null){

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();

    }


    private void setAlarm() {
        // Calendar cal = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_IMMUTABLE);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),
                1000 * 10,pendingIntent); // repeat alarm every 10 seconds
        String milli = String.valueOf(cal.getTimeInMillis());
        // Toast.makeText(this, milli, Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "milli: " + milli);

    }


    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "foxandroidReminderChannel";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foxandroid",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }


    }





}