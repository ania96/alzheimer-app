package com.example.anna.alzheimerapp.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anna.alzheimerapp.AlarmUtil;
import com.example.anna.alzheimerapp.MainActivity;

import java.util.Calendar;

public class Alarm extends BroadcastReceiver
{
    public static final String REMINDER_TEXT = "REMINDER_TEXT";
    String hour, minute;
    String notificationContent;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        // Put here YOUR code.
        String message = intent.getStringExtra(REMINDER_TEXT);
        Intent i = new Intent(context, AddReminder.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.putExtra(REMINDER_TEXT, message);
        context.startActivity(i);
        wl.release();
    }

    public void setAlarm(Context context, String text, String hour, String minute)
    {

        Integer hourInt = Integer.parseInt(hour);
        Integer minuteInt = Integer.parseInt(minute);

        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent i = new Intent(context, Alarm.class);
        i.putExtra(REMINDER_TEXT, "Pamiętaj aby zażyć lekarstwa: " + text);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

        long currentTimeMillis = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        calendar.set(Calendar.HOUR_OF_DAY, hourInt);
        calendar.set(Calendar.MINUTE, minuteInt);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        AlarmUtil.addAlarm(context, i, calendar);

        //long intervalMillis = 10 * 1000;

// With setInexactRepeating(), you have to use one of the AlarmManager interval
// constants--in this case, AlarmManager.INTERVAL_DAY.
//        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                intervalMillis, pi);

    }


    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}