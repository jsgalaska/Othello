package com.example.mega.othello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;

public class OthelloGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othello_game);

        Intent intent = getIntent();

        GridLayout layout = (GridLayout) findViewById(R.id.othelloGrid);
    }
}
