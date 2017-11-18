package com.matchmaker.matchmaker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;


public class MatchDetailsActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // MatchData should probably be passed as an intent from the previous screen?
        // Or maybe make an API call to the MatchMaker server here.
        MatchData md = getMatchData();

        TextView tvWhat = (TextView) findViewById(R.id.tvWhat);
        tvWhat.setText(md.getWhat());

        TextView tvWhere = (TextView) findViewById(R.id.tvWhere);
        tvWhere.setText(md.getWhere());

        TextView tvWhen = (TextView) findViewById(R.id.tvWhen);
        tvWhen.setText(md.getWhen());


        // https://stackoverflow.com/questions/5070830/populating-a-listview-using-an-arraylist
        ListView lvParticipants = (ListView) findViewById(R.id.lvParticipants);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, md.getParticipants());
        lvParticipants.setAdapter(aa);
    }



    private MatchData getMatchData() {
        MatchData md = new MatchData("football", "Wednesday 4pm", "Phoenix Park");
        md.addParticipant("Emma");
        md.addParticipant("Pam");
        md.addParticipant("Cara");
        md.addParticipant("Andy");
        md.addParticipant("Davey");
        return md;
    }
}