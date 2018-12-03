package com.example.anna.alzheimerapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.example.anna.alzheimerapp.reminder.AddReminder;
import com.example.anna.alzheimerapp.reminder.Alarm;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AlarmUtil {
//kazdy alarm dostaje swoje wlasne id
    private static int id = 0;

    public static void addAlarm(Context context, Intent intent, Calendar calendar) {
//alarmmanager - robi tak ze chdzi w tle
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id++, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public static void replayAlarmAfterMinutes(Context context, int replayInMinutes, String message) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // przesunięcie daty o określoną liczbę minut
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + replayInMinutes);

        Intent intent = new Intent(context, Alarm.class);
        intent.putExtra(Alarm.REMINDER_TEXT, message);

        addAlarm(context, intent, cal);
    }
}
