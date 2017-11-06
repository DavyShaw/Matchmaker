package com.matchmaker.matchmaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    // public static final String EXTRA_MESSAGE = "com.matchmaker.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the send button
     * Note the necessary requirements in order for this method
     * to be recognised by the onclick property in the activity_main.xml
     * file:
     * Public Access, A void return value, A View as the only arg
     * (It is the View object that was clicked)*/
    public void sendMessage (View view) {
        Intent intent = new Intent(this, MatchPreferencesActivity.class);
        // EditText editText = (EditText) findViewById(R.id.editText);
        // String message = editText.getText().toString();
        // intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
