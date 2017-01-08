package com.example.mega.othello;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
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
import android.os.Handler;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

//import java.util.logging.LogRecord;
//import android.content.Intent;

public class OthelloGame extends AppCompatActivity {

    //For disc-piece animations
    AnimationDrawable discAnimBtw, discAnimWtb;
    //View discAnimationView; *Probably unnecessary

    GridView gridView;
    ImageAdapter adapter;
    GameSession session;
    GameBoard board = GameBoard.getInstance();
    int whitePiece = R.drawable.i14;
    int blackPiece = R.drawable.i0;
    int transparent = R.drawable.transparent_tile;
    int turn = 0;

    private Handler handler0;
    long timer0 = 0; // Player-1 flipping
    long timer1 = 0; // Player-2 flipping

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othello_game);

        handler0 = new Handler();

        // Engaged when Player-1 makes a move
        final Runnable runnable0 = new Runnable(){
            @Override
            public void run(){
                /* Debug
                Log.d("runnable0", "runnable0 did something");
                System.out.println("timer0 in runnable0 is: " + timer0); */

                // Count down by this much time (ms)
                timer0 = timer0 - 500;

                // If time allotted, repeat until 0 every ms specified
                if (timer0 > 0) {
                    handler0.postDelayed(this, 500);
                }

                // AI/Player-2 must wait
                if (timer0 == 0) {
                    turn = 1;
                    cpuMove();
                }


            }
        };

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        BitmapFactory.decodeResource(getResources(), R.id.pieceImage, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;

        getIntent();

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
                if(board.getSquare(position) == transparent){
                    if(turn == 0 && timer1 == 0){
                        //holder.image.setImageResource(whitePiece);
                        holder.image.setImageBitmap(decodeSampledBitmapFromResource(getResources(), whitePiece, 64,64));
                        board.setSquare(position, whitePiece);
                    }
                    /*else{
                        holder.image.setImageResource(R.drawable.disc_black_hd);
                        images[position] = R.drawable.disc_black_hd;
                    }*/
                    /* turn = 1;
                    cpuMove();*/

                    // Engage timer1 (AI/Player-2)
                    timer0 = 500;
                    handler0.postDelayed(runnable0, 500);

                }
                //Toast.makeText(OthelloGame.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);

    }

    //calculate the cpu player's move
    public void cpuMove(){
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        int move;
        int square = 0;
        String poss = "";
        for(int i = 0; i < 64; i++){
            if(board.getSquare(i) == transparent){
                possibleMoves.add(i);
                poss = poss + i +",";
            }
        }
        int size = possibleMoves.size();

        if(size > 0){
            boolean validMove = false;
            while(!validMove){
                move = (int) (Math.random()*size);
                square = possibleMoves.get(move);
                if(isValidMove(square)){
                    validMove = true;
                }
            }
            makeMove(square);
        }

        switch (checkWin()){
            case 0:
                Toast.makeText(OthelloGame.this, "Player Wins!!!", Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(OthelloGame.this, "CPU Wins!", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(OthelloGame.this, "TIE!", Toast.LENGTH_LONG).show();
                break;
            default:
        }
    }

    //perform move
    public void makeMove(int position){

        // Engaged when AI/Player-2 makes a move
        final Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                /*Debug
                Log.d("runnable1", "runnable1 did something");
                System.out.println("timer0 in runnable0 is: " + timer1);*/

                // Count down by this much time (ms)
                timer1 = timer1 - 500;

                // If time allotted, repeat until 0 every ms specified
                if (timer1 > 0) {
                    handler0.postDelayed(this, 500);
                }

                // Player-1 must wait
                if (timer1 == 0){
                    turn = 0;
                }
            }
        };

        board.setSquare(position, blackPiece);
        ImageAdapter.ViewHolder holder = adapter.getHolder(position);
        //holder.image.setImageResource(blackPiece);
        holder.image.setImageBitmap(decodeSampledBitmapFromResource(getResources(), blackPiece, 64,64));

        //Engage timer0 (Player-1)
        timer1 = 500;
        handler0.postDelayed(runnable1, 500);
    }

    //checks to see if the player's intended move is legal
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

                //disc piece animation *Probably unnecessary
                //discAnimationView = findViewById(R.id.id_btw_anim);

                if(turn == 0){
                    //holder.image.setImageResource(whitePiece);
                    //holder.image.setImageBitmap(decodeSampledBitmapFromResource(getResources(), whitePiece, 64,64));
                    holder.image.setImageResource(R.drawable.dpap_btw);
                    discAnimBtw = (AnimationDrawable) holder.image.getDrawable();
                    discAnimBtw.start();
                    board.setSquare(k, whitePiece);

                }if(turn == 1){
                    //holder.image.setImageResource(blackPiece);
                    //holder.image.setImageBitmap(decodeSampledBitmapFromResource(getResources(), blackPiece, 64,64));
                    holder.image.setImageResource(R.drawable.dpap_wtb);
                    discAnimWtb = (AnimationDrawable)holder.image.getDrawable();
                    discAnimWtb.start();
                    board.setSquare(k, blackPiece);
                }
            }
        }
        Log.d("Debug", bool);
        //Toast.makeText(OthelloGame.this, "" + lines.get("topLeft").size(), Toast.LENGTH_SHORT).show();
        return foundNearby;
    }

    //checks the squares to the upper left of the selected move
    public ArrayList<Integer> getTLeft(int middle){
        Log.d("Debug", "topLeft");
        return checkLine(middle, -9);
    }

    //checks the squares above the selected move
    public ArrayList<Integer> getTop(int middle){
        Log.d("Debug", "top");
        return checkLine(middle, -8);
    }

    //checks the squares to the upper right of the selected move
    public ArrayList<Integer> getTRight(int middle){
        Log.d("Debug", "topRight");
        return checkLine(middle, -7);
    }

    //checks the squares to the left of the selected move
    public ArrayList<Integer> getLeft(int middle){
        Log.d("Debug", "left");
        return checkLine(middle, -1);
    }

    //checks the squares to the right of the selected move
    public ArrayList<Integer> getRight(int middle){
        Log.d("Debug", "right");
        return checkLine(middle, +1);
    }

    //checks the squares to the lower left of the selected move
    public ArrayList<Integer> getBLeft(int middle){
        Log.d("Debug", "bottomLeft");
        return checkLine(middle, +7);
    }

    //checks the squares under the selected move
    public ArrayList<Integer> getBottom(int middle){
        Log.d("Debug", "bottom");
        return checkLine(middle, +8);
    }

    //checks the squares to the lower right of the selected move
    public ArrayList<Integer> getBRight(int middle){
        Log.d("Debug", "bottomRight");
        return checkLine(middle, +9);
    }

    //checks a square to see if it's empty or out of bounds of the board
    public boolean isEmptySquare(int position){
        if(position < 0 || position > 63){
            return true;
        }
        return(board.getSquare(position) == transparent);
    }

    //checks the squares in a line going away from the players intended move to see if the move is
    //legal. If the move is legal, the method returns an array of the squares so they can be flipped.
    public ArrayList<Integer> checkLine(int position, int modifier){
        ImageAdapter.ViewHolder holder = adapter.getHolder(position);
        boolean keepGoing = true;
        if(!isEmptySquare(position)){
            keepGoing = false;
        }
        ArrayList<Integer> array = new ArrayList<>();
        while(keepGoing){
            position = position + modifier;
            //Log.d("Debug", "current position" + position);
            if((position < 0 || position > 63)){
                Log.d("Debug", "broken edge" + position);
                array.clear();
                keepGoing = false;
            }
            if(!isEmptySquare(position)){
                Log.d("Debug", "turn:"+ turn);
                if((turn == 0 && whitePiece == board.getSquare(position))
                        || (turn == 1 && blackPiece == board.getSquare(position))){
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
        }
        if(array.size()>1){
            Log.d("Debug", "get holder"+ array.get(array.size()-1));
            if(!(turn == 0 && whitePiece == board.getSquare(array.get(array.size()-1))
                    || turn == 1 && blackPiece == board.getSquare(array.get(array.size()-1)))){
                array.clear();
            }
        }else{
            array.clear();
        }
        return array;
    }

    //checks for a winner
    public int checkWin(){
        int white = 0;
        int black = 0;
        for(int i = 0; i<64; i++){
            if(board.getSquare(i) == transparent){
                return 3;
            }else if(board.getSquare(i) == whitePiece){
                white++;
            }else if(board.getSquare(i) == blackPiece){
                black++;
            }
        }
        if(white>black){
            Log.d("Debug", "White wins");
            return 0;
        }else if(black>white){
            Log.d("Debug", "Black wins");
            return 1;
        }else if(white == black){
            Log.d("Debug", "Tie");
            return 2;
        }else{
            Log.d("Debug" ,"No winner");
            return 3;
        }
    }
}
