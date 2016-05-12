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

import com.mega.game.gameplay.GameSession;

import java.util.ArrayList;
import java.util.Random;

public class OthelloGame extends AppCompatActivity {
    GridView gridView;
    ImageAdapter adapter;
    GameSession session;
    int turn = 0;
    public int[] images ={
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.disc_white_hd, //Middle top
            R.drawable.disc_black_hd, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.disc_black_hd, //Middle bottom
            R.drawable.disc_white_hd, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
            R.drawable.transparent_tile, R.drawable.transparent_tile,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othello_game);

        Intent intent = getIntent();

        session = new GameSession();

        gridView = (GridView) findViewById(R.id.othelloGrid);
        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                ImageAdapter.ViewHolder holder = (ImageAdapter.ViewHolder) v.getTag();
                //int imageID = (int) holder.image.getTag();
                if(images[position] == R.drawable.transparent_tile){
                    if(turn == 0){
                        holder.image.setImageResource(R.drawable.disc_white_hd);
                        images[position] = R.drawable.disc_white_hd;
                    }/*else{
                        holder.image.setImageResource(R.drawable.disc_black_hd);
                        images[position] = R.drawable.disc_black_hd;
                    }*/
                    turn = 1;
                    cpuMove();
                }
                //Toast.makeText(OthelloGame.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void cpuMove(){
        ArrayList possibleMoves = new ArrayList();
        int move;
        for(int i = 0; i < 64; i++){
            if(images[i] == R.drawable.transparent_tile){
                possibleMoves.add(i);
            }
        }
        int size = possibleMoves.size();
        move = (int) (Math.random()*possibleMoves.size());
        Toast.makeText(OthelloGame.this, size+":"+move, Toast.LENGTH_SHORT).show();
        makeMove(move);
    }

    public void makeMove(int position){
        images[position] = R.drawable.disc_black_hd;
        ImageAdapter.ViewHolder holder = adapter.getHolder(position);
        holder.image.setImageResource(R.drawable.disc_black_hd);
        turn = 0;
    }

    public GameSession getSession(){
        return session;
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
