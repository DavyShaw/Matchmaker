package com.matchmaker.matchmaker;
/**************************************************************************************************
 The following code template was supplied by Android studio using the new activity functionality.
 MainActivity
 Authors: Emma Byrne, Pamela Kelly
 Date: 30/10/2017
 Course: COMP 41690 Android Programming
 Usage: Main activity for the application. It is the first page that the user sees. If the user
 has not logged in, they will see a prompt to login/register. If they are logged in, they will see
 buttons to bring them to activities to create an event, search for an event or view their profile
 **************************************************************************************************/

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.location.*;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //######################### SENSORS ###########################################
    // Sensor referenced from example provided in sensorsimple practical from lectures
    SensorManager sensorMan = null;
    List list;

    myDbAdapter dbAdapter;
    private FirebaseAuth firebaseAuth;

    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            // Write the accelerometer values to the TextView
            float[] values = sensorEvent.values;
            //accText.setText("x: "+values[0]+"\ny: "+values[1]+"\nz: "+values[2]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            // Isn't required for this example
        }
    };
    //############################# WEATHER ##################################
    // endpoint: http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID={APIKEY}
    // Called when the activity is first created
    // Pam's API Key: e4e001f4cb12e58da30d4da229c685f7

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is logged in
        myDbAdapter dbAdapter;
        FirebaseAuth firebaseAuth;
        // Check if user is logged in and produce appropriate xml depending
        dbAdapter = new myDbAdapter(this); // start a db instance
        firebaseAuth = FirebaseAuth.getInstance(); // get the info from firebase
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // if the user is logged in - link to profile instead of login prompt
            setContentView(R.layout.activity_main_log_in);
        }
        else {
            // if the user is not logged in - should show login screen
            setContentView(R.layout.activity_main);
        }

        // Get a sensor manager instance
        sensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);

        // This corresponds to a TextView element in main.xml with android:id="@+id/accText"
        //accText= (TextView)findViewById(R.id.accText);

        // Get list of accelerometers
        list = sensorMan.getSensorList(Sensor.TYPE_ACCELEROMETER);

        // If there are any accelerometers register a listener to the first
        // else print a little error message
        if(list.size()>0){
            sensorMan.registerListener(sensorEventListener, (Sensor) list.get(0),
                    SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(getBaseContext(), "Error: No Accelerometer", Toast.LENGTH_LONG);
        }

    }

    @Override
    protected void onStop() {
        // always a good idea to unregister, disconnect, close, etc things
        if (list.size() > 0) {
            // this actually unregisters a listener for all sensors, but it can be done
            // on a sensor by sensor basis
            sensorMan.unregisterListener(sensorEventListener);
        }
        super.onStop();
    }


    // Method to bring the user to the login screen when they click the login button
    public void proceedToLogin(View view) {
        // Create new intent to link button click to activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // Method to bring the user to the create activity screen - will be removed when results page finalised
    public void proceedToCreate(View view) {
        // Create new intent to link button click to activity
        Intent intent = new Intent(this, CreateEvent.class);
        startActivity(intent);
    }

    public void proceedToProfile(View view) {
        // Create new intent to link button click to profile activity
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }


    /** Called when the user taps the send button
     * Note the necessary requirements in order for this method
     * to be recognised by the onclick property in the activity_main.xml
     * file:
     * Public Access, A void return value, A View as the only arg
     * (It is the View object that was clicked)*/
    // TODO: Change sendMessage name to something that makes more sense for this action
    public void sendMessage (View view) {
        Intent intent = new Intent(this, MatchPreferencesActivity.class);
        startActivity(intent);
    }
}
