package com.example.anna.alzheimerapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddReminder extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final int REPLAY_IN_MINUTES = 2;

    AlarmManager alarmManager;
    private EditText editTextNotificationContent;
    Spinner spinnerHour, spinnerMinute;
    private Button addAlarm;
    String hours, minutes;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        showAlert();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        init();
        showAlert();
    }
    public void showAlert() {
        String message = getIntent().getStringExtra(Alarm.REMINDER_TEXT);
        if (message != null) {

            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            final Ringtone ringtone = RingtoneManager.getRingtone(AddReminder.this, notification);
            ringtone.play();
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton("Wziąłem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(AddReminder.this, "Anulowano alarm", Toast.LENGTH_LONG).show();
                            ringtone.stop();

                        }
                    })
                    .setNegativeButton("Przypomnij później", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlarmUtil.replayAlarmAfterMinutes(AddReminder.this, REPLAY_IN_MINUTES, message);
                            ringtone.stop();
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            AlarmUtil.replayAlarmAfterMinutes(AddReminder.this, REPLAY_IN_MINUTES, message);
                        }
                    })
                    .create();
            dialog.show();
        }
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    public void init(){
        spinnerHour=(Spinner)findViewById(R.id.spinnerHour);
        spinnerHour.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.hours_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHour.setAdapter(adapter);
        spinnerMinute=(Spinner)findViewById(R.id.spinnerMinute);
        spinnerMinute.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.minutes_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinute.setAdapter(adapter2);
        editTextNotificationContent = (EditText)findViewById(R.id.editTextNotificationContent);
        addAlarm = (Button)findViewById(R.id.addAlarm);
        addAlarm.setOnClickListener(this);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.addAlarm:
                if (editTextNotificationContent.getText().toString().matches("") || spinnerHour.getSelectedItem() ==null ||spinnerMinute.getSelectedItem() ==null) {
                    Toast.makeText(this, "Uzupełnij wszystkie wymagane pola", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Alarm alarm = new Alarm();
                    alarm.setAlarm(this, editTextNotificationContent.getText().toString(), hours, minutes);
                    Toast.makeText(this, "Ustwiono przypomnienie na godzinę " + hours + ":" + minutes, Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.spinnerHour:
                hours = parent.getItemAtPosition(position).toString();
                break;
            case R.id.spinnerMinute:
                minutes = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
