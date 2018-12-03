package com.example.anna.alzheimerapp.reminder;

import android.app.AlarmManager;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.example.anna.alzheimerapp.FamilyAddMember;
import com.example.anna.alzheimerapp.FamilyOption;
import com.example.anna.alzheimerapp.R;

public class Reminder extends AppCompatActivity  implements View.OnClickListener {
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        init();
    }
    public void init(){
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButton:
                Intent intent = new Intent(Reminder.this, AddReminder.class);
                startActivity(intent);
                break;
        }
    }
}
