package com.example.anna.alzheimerapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button buttonReminder, buttonMyFamily, buttonMyLocalisation, buttonSOS, buttonSaveHome;
    EditText homeEditText, homeLng;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        buttonMyFamily = (Button) findViewById(R.id.buttonMyFamily);
        buttonMyFamily.setOnClickListener(this);
        buttonMyLocalisation = (Button) findViewById(R.id.buttonMyLocalisation);
        buttonMyLocalisation.setOnClickListener(this);
        buttonReminder = (Button) findViewById(R.id.buttonReminder);
        buttonReminder.setOnClickListener(this);
        buttonSOS = (Button) findViewById(R.id.buttonSOS);
        buttonSOS.setOnClickListener(this);
        buttonSaveHome = (Button) findViewById(R.id.buttonSaveHome);
        homeEditText = findViewById(R.id.homeLat);

        buttonSaveHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePreferencesUtil.putString(MainActivity.this, HomePreferencesUtil.HOME, MainActivity.this.homeEditText.getText().toString());
                Toast.makeText(MainActivity.this, "Zapisano ustawienia domu", Toast.LENGTH_LONG).show();
            }
        });

        String homeValue = HomePreferencesUtil.getString(this, HomePreferencesUtil.HOME, "");
        if (!homeValue.equals("")) {
            homeEditText.setText("" + homeValue, TextView.BufferType.EDITABLE);
        }
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Toast.makeText(MainActivity.this, "nie mam dostep do pozwolenia", Toast.LENGTH_LONG).show();

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);

            }
        };
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            }
        }
        else {
            configure();
        }
    }
    private void configure() {
        buttonMyLocalisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates("gps", 1000, 0, locationListener);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonMyFamily:
                Intent intent = new Intent(MainActivity.this, FamilyOption.class);
                startActivity(intent);
                break;
            case R.id.buttonMyLocalisation:

                    Intent intent2 = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(intent2);
                break;
            case R.id.buttonReminder:
                Intent intent3 = new Intent(MainActivity.this, Reminder.class);
                startActivity(intent3);
                break;
            case R.id.buttonSOS:
                Intent intent4 = new Intent(Intent.ACTION_SEND);
                intent4.setType("text/plain");
                intent4.putExtra(Intent.EXTRA_TEXT, "Zgubiłem się ! Potrzebuje pomocy !");
                startActivity(intent4);
                break;
        }
    }
}
