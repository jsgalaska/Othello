package com.example.mega.othello;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OthelloGame extends AppCompatActivity {
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othello_game);

        Intent intent = getIntent();

        gridView = (GridView) findViewById(R.id.othelloGrid);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                /*ImageView image = (ImageView) gridView.getItemAtPosition(position);
                if(image.getDrawable().equals(R.drawable.disc_simple_black)){
                    image.setImageResource(R.drawable.disc_simple_white);
                }else{
                    image.setImageResource(R.drawable.disc_simple_black);
                }*/
                Toast.makeText(OthelloGame.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
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
