package com.example.jennykuma.friendfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class MyProfile extends AppCompatActivity {

    private TextView vname;
    private TextView vemail;
    private TextView vgameplat;
    private TextView vgame;
    private TextView vgameexp;
    private TextView vgamestyle;

    String[] somePlatforms = new String[]{
        "PS4", "PC", "XBOX"
    };

    String[] someGames = new String[]{
        "League of Legends", "Dota", "Rocket League"
    };

    String[] someSkill = new String[]{ // drop down items for gaming style
        "Newbie", "Casual", "Competitive"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        vname = (TextView) findViewById(R.id.dName);
        vemail = (TextView) findViewById(R.id.dEmail);
        vgameplat = (TextView) findViewById(R.id.dGamePlat);
        vgame = (TextView) findViewById(R.id.dGame);
        vgameexp = (TextView) findViewById(R.id.dGamingExp);
        vgamestyle = (TextView) findViewById(R.id.dPrefGamingStyle);


        try {
            InputStream in = openFileInput("profile.txt");

            if (in != null) {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);

                String str;
                String[] buf = new String[6];

                while ((str = reader.readLine()) != null) {

                    buf = str.split("\t");
                }

                in.close();
                vname.setText(buf[0]);
                vemail.setText(buf[1]);
                vgameplat.setText(somePlatforms[Integer.parseInt(buf[2])]);
                vgame.setText(someGames[Integer.parseInt(buf[3])]);
                vgameexp.setText(buf[4]);
                vgamestyle.setText(someSkill[Integer.parseInt(buf[5])]);
            }
        } catch (Throwable e) {
            //Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){ // redirects to Profile page
            public void onClick(View v){
                startActivity(new Intent(MyProfile.this, Profile.class));
            }
        });
    }
}

