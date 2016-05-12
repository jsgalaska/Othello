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
                if(!isValidMove(position)){
                    return;
                }
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
        ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
        int move;
        int square = 0;
        String poss = "";
        for(int i = 0; i < 64; i++){
            if(images[i] == R.drawable.transparent_tile){
                possibleMoves.add(i);
                poss = poss + i +",";
            }
        }
        int size = possibleMoves.size();
        boolean validMove = false;
        while(validMove == false){
            move = (int) (Math.random()*size);
            square = possibleMoves.get(move);
            if(isValidMove(square)){
                validMove = true;
            }
        }

        makeMove(square);
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

    public boolean isValidMove(int position){
        boolean foundNearby = false;
        if(!(position%8==0)){
            if(getTLeft(position) == true || getLeft(position) == true || getBLeft(position) == true){
                foundNearby = true;
            }
        }
        if(!(position%7==0)){
            if(getTRight(position) == true || getRight(position) == true || getBRight(position) == true){
                foundNearby = true;
            }
        }
        if(getTop(position) == true || getBottom(position) == true){
            foundNearby = true;
        }
        return foundNearby;
    }

    public boolean getTLeft(int middle){
        int position = middle - 9;
        if(!isEmptySquare(position)){
            //Toast.makeText(OthelloGame.this,""+position, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }
    }

    public boolean getTop(int middle){
        int position = middle - 8;
        if(!isEmptySquare(position)){
            //makeText(OthelloGame.this,""+position, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }
    }

    public boolean getTRight(int middle){
        int position = middle - 7;
        if(!isEmptySquare(position)){
            //Toast.makeText(OthelloGame.this,""+position, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }
    }

    public boolean getLeft(int middle){
        int position = middle - 1;
        if(!isEmptySquare(position)){
            //Toast.makeText(OthelloGame.this,""+position, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }
    }

    public boolean getRight(int middle){
        int position = middle + 1;
        if(!isEmptySquare(position)){
           // Toast.makeText(OthelloGame.this,""+position, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }
    }

    public boolean getBLeft(int middle){
        int position = middle + 7;
        if(!isEmptySquare(position)){
           // Toast.makeText(OthelloGame.this,""+position, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }
    }

    public boolean getBottom(int middle){
        int position = middle + 8;
        if(!isEmptySquare(position)){
            //Toast.makeText(OthelloGame.this,""+position, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }
    }

    public boolean getBRight(int middle){
        int position = middle + 9;
        if(!isEmptySquare(position)){
            //Toast.makeText(OthelloGame.this,""+position, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return false;
        }
    }

    public boolean isEmptySquare(int position){
        if(position < 0 || position > 63){
            return true;
        }
        if(images[position] == R.drawable.transparent_tile){
            return true;
        }else{
            return false;
        }
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
