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
    public int[] images ={
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
            R.drawable.disc_black_hd, R.drawable.disc_black_hd,
    };

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
                ImageAdapter.ViewHolder holder = (ImageAdapter.ViewHolder) v.getTag();
                //int imageID = (int) holder.image.getTag();
                if(images[position] == R.drawable.disc_black_hd){
                    holder.image.setImageResource(R.drawable.disc_white_hd);
                    images[position] = R.drawable.disc_white_hd;
                }else{
                    holder.image.setImageResource(R.drawable.disc_black_hd);
                    images[position] = R.drawable.disc_black_hd;
                }
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
