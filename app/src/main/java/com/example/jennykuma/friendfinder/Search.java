package com.example.jennykuma.friendfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button bComp = (Button) findViewById(R.id.button3); // create button object 'Competitive'
        Button bCas = (Button) findViewById(R.id.button5); // create button object 'Casual;

        bComp.setOnClickListener(new View.OnClickListener() { // redirects to Profile page
            public void onClick(View v) {
                startActivity(new Intent(Search.this, Competitive.class));
            }
        });

        bCas.setOnClickListener(new View.OnClickListener() { // redicts to Search page
            public void onClick(View v) {
                startActivity(new Intent(Search.this, Casual.class));
            }
        });
    }
}
