package com.example.mega.othello;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class OthelloGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othello_game);

        Intent intent = getIntent();

        GridLayout layout = (GridLayout) findViewById(R.id.othelloGrid);
    }

    public void registerClick(View view){
        TextView t = (TextView) view;
        if(("O").equals(t.getText())){
            t.setText("X");
        }else{
            t.setText("O");
        }
    }
}
