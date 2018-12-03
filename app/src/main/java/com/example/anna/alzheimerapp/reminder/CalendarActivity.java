//package com.example.anna.alzheimerapp.reminder;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.CalendarView;
//import android.widget.TextView;
//
//import com.example.anna.alzheimerapp.R;
//
//public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {
//    private CalendarView calendarView;
//    TextView textViewSetDate;
//    static final int REQUEST_CALENDAR = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_calendar);
//        init();
//    }
//    public void init(){
//        textViewSetDate = (TextView)findViewById(R.id.textViewSetHour);
//        calendarView = (CalendarView)findViewById(R.id.calendarView);
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
//                String date = year + "/" + (month + 1) + "/" + day + "/";
//                Log.d("DATA", date);
//
//                Intent intent = new Intent(CalendarActivity.this, AddReminder.class);
//                intent.putExtra("date", date);
//                startActivityForResult(intent, REQUEST_CALENDAR);
//
//
//            }
//        });
//
//
//    }
//
//
//
//    @Override
//    public void onClick(View view) {
//
//
//
//    }
//}
