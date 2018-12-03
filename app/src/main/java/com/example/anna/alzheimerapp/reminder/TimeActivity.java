//package com.example.anna.alzheimerapp.reminder;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.widget.TextView;
//import android.widget.TimePicker;
//
//import com.example.anna.alzheimerapp.R;
//
//public class TimeActivity extends AppCompatActivity {
//    private TimePicker timePicker;
//    TextView textViewSetHour, textViewSetMinute;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_time);
//        init();
//    }
//    public void init(){
//        timePicker = findViewById(R.id.time_picker);
//        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
//
//            }
//        });
//
//    }
//}
