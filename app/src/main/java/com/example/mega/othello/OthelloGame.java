package com.example.mega.othello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.mega.game.gameplay.GameSession;

import java.util.ArrayList;
import java.util.HashMap;

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
                    Toast.makeText(OthelloGame.this, "Invalid Move:"+getTRight(position)+ ":"+ position, Toast.LENGTH_SHORT).show();
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
        ArrayList<Integer> possibleMoves = new ArrayList<>();
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
        while(!validMove){
            move = (int) (Math.random()*size);
            square = possibleMoves.get(move);
            if(isValidMove(square)){
                validMove = true;
            }
        }

        makeMove(square);
        switch (checkWin()){
            case 0:
                Toast.makeText(OthelloGame.this, "Player Wins!!!", Toast.LENGTH_LONG).show();
            case 1:
                Toast.makeText(OthelloGame.this, "CPU Wins!", Toast.LENGTH_LONG).show();
            case 2:
                Toast.makeText(OthelloGame.this, "TIE!", Toast.LENGTH_LONG).show();
            default:
        }
    }

    public void makeMove(int position){
        images[position] = R.drawable.disc_black_hd;
        ImageAdapter.ViewHolder holder = adapter.getHolder(position);
        holder.image.setImageResource(R.drawable.disc_black_hd);
        turn = 0;
    }

    public boolean isValidMove(int position){
        ImageAdapter.ViewHolder holder;
        HashMap<String,ArrayList<Integer>> lines = new HashMap<>();
        lines.put("topLeft", getTLeft(position));
        lines.put("top", getTop(position));
        lines.put("topRight", getTRight(position));
        lines.put("left", getLeft(position));
        lines.put("right", getRight(position));
        lines.put("bottomLeft", getBLeft(position));
        lines.put("bottom", getBottom(position));
        lines.put("bottomRight", getBRight(position));
        boolean foundNearby = false;
        if(!(position%8==0)){
            if((lines.get("topLeft").size() > 0) || (lines.get("left").size() > 0) || (lines.get("bottomLeft").size() > 0)){
                foundNearby = true;
            }
        }
        if(!((position+1)%8==0)){
            if((lines.get("topRight").size() > 0) || (lines.get("right").size() > 0) || (lines.get("bottomRight").size() > 0)){
                foundNearby = true;
            }
        }
        if((lines.get("top").size() > 0) || (lines.get("bottom").size() > 0)){
            foundNearby = true;
        }
        String bool = "";
        ArrayList<String> l = new ArrayList<>();
        l.add("topLeft");
        l.add("top");
        l.add("topRight");
        l.add("left");
        l.add("right");
        l.add("bottomLeft");
        l.add("bottom");
        l.add("bottomRight");
        for(int j = 0; j< lines.size(); j++){
            bool = bool +":"+ l.get(j);
            for(int i = 0; i< lines.get(l.get(j)).size()-1; i++){
                bool = bool + lines.get(l.get(j)).get(i);
                int k = lines.get(l.get(j)).get(i);
                holder = adapter.getHolder(k);
                if(turn == 0){
                    holder.image.setImageResource(R.drawable.disc_white_hd);
                    images[k] = R.drawable.disc_white_hd;
                }else{
                    holder.image.setImageResource(R.drawable.disc_black_hd);
                    images[k] = R.drawable.disc_black_hd;
                }
            }
        }
        Log.d("Debug", bool);
        //Toast.makeText(OthelloGame.this, "" + lines.get("topLeft").size(), Toast.LENGTH_SHORT).show();
        return foundNearby;
    }

    public ArrayList<Integer> getTLeft(int middle){
        Log.d("Debug", "topLeft");
        return checkLine(middle, -9);
    }

    public ArrayList<Integer> getTop(int middle){
        Log.d("Debug", "top");
        return checkLine(middle, -8);
    }

    public ArrayList<Integer> getTRight(int middle){
        Log.d("Debug", "topRight");
        return checkLine(middle, -7);
    }

    public ArrayList<Integer> getLeft(int middle){
        Log.d("Debug", "left");
        return checkLine(middle, -1);
    }

    public ArrayList<Integer> getRight(int middle){
        Log.d("Debug", "right");
        return checkLine(middle, +1);
    }

    public ArrayList<Integer> getBLeft(int middle){
        Log.d("Debug", "bottomLeft");
        return checkLine(middle, +7);
    }

    public ArrayList<Integer> getBottom(int middle){
        Log.d("Debug", "bottom");
        return checkLine(middle, +8);
    }

    public ArrayList<Integer> getBRight(int middle){
        Log.d("Debug", "bottomRight");
        return checkLine(middle, +9);
    }

    public boolean isEmptySquare(int position){
        if(position < 0 || position > 63){
            return true;
        }
        return(images[position] == R.drawable.transparent_tile);
    }

    public ArrayList<Integer> checkLine(int position, int modifier){
        ImageAdapter.ViewHolder holder = adapter.getHolder(position);
        boolean keepGoing = true;
        ArrayList<Integer> array = new ArrayList<>();
        do{
            position = position + modifier;
            //Log.d("Debug", "current position" + position);
            if((position < 0 || position > 63)){
                Log.d("Debug", "broken edge" + position);
                array.clear();
                keepGoing = false;
            }
            if(!isEmptySquare(position)){
                boolean test = R.drawable.disc_white_hd == images[position];
                //Log.d("Debug", "checkcolorsame:"+ test);
                Log.d("Debug", "turn:"+ turn);
                if((turn == 0 && R.drawable.disc_white_hd == images[position])
                        || (turn == 1 && R.drawable.disc_black_hd == images[position])){
                    Log.d("Debug", "checkcolorsame:"+ test);
                    Log.d("Debug", "broken same"+ position);
                    array.add(position);
                    keepGoing = false;
                }else{
                    Log.d("Debug", "add"+ position);
                    array.add(position);
                }
            }else{
                keepGoing = false;
            }
        }while(keepGoing);
        if(array.size()>1){
            Log.d("Debug", "get holder"+ array.get(array.size()-1));
            if(!(turn == 0 && R.drawable.disc_white_hd == images[array.get(array.size()-1)]
                    || turn == 1 && R.drawable.disc_black_hd == images[array.get(array.size()-1)])){
                array.clear();
            }
        }else{
            array.clear();
        }
        return array;
    }

    public int checkWin(){
        int white = 0;
        int black = 0;
        for(int i = 0; i<images.length; i++){
            if(images[i] == R.drawable.transparent_tile){
                return 3;
            }else if(images[i] == R.drawable.disc_white_hd){
                white++;
            }else if(images[i] == R.drawable.disc_black_hd){
                black++;
            }
        }
        if(white>black){
            return 0;
        }else if(black>white){
            return 1;
        }else{
            return 2;
        }
    }
}
