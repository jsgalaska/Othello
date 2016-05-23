package com.mega.game.gameplay;

import android.util.Log;

import com.example.mega.othello.GameBoard;
import com.example.mega.othello.ImageAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Shua on 5/11/2016.
 */
public class GameController {

    ImageAdapter adapter;
    GameBoard board = new GameBoard();
    int whitePiece = board.getColor("white");
    int blackPiece = board.getColor("black");
    int transparent = board.getColor("transparent");
    int turn = 0;

    public GameController(){

    }

    //calculate the cpu player's move
    public String cpuMove(){
        ArrayList<Integer> possibleMoves = new ArrayList<>();
        int move;
        int square = 0;
        String poss = "";
        for(int i = 0; i < 64; i++){
            if(board.getPiece(i) == transparent){
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
        String message = "";
        switch (checkWin()){
            case 0:
                message = "Player Wins!!!";
            case 1:
                message = "CPU Wins!";
            case 2:
                message = "Tie!";
            default:
        }
        return message;
    }

    //perform move
    public void makeMove(int position){
        board.setPiece(blackPiece, position);
        ImageAdapter.ViewHolder holder = adapter.getHolder(position);
        holder.getImage().setImageResource(blackPiece);
        turn = 0;
    }

    //checks to see if the player's intended move is legal
    public boolean isValidMove(int position){
        Log.d("Debug", "ClickPosition" + position);
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
                    holder.getImage().setImageResource(whitePiece);
                    board.setPiece(whitePiece, k);
                }else{
                    holder.getImage().setImageResource(blackPiece);
                    board.setPiece(blackPiece, k);
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
        Log.d("Debug", "color" + board.getPiece(position));
        return(board.getPiece(position) == transparent);
    }

    //checks the squares in a line going away from the players intended move to see if the move is
    //legal. If the move is legal, the method returns an array of the squares so they can be flipped.
    public ArrayList<Integer> checkLine(int position, int modifier){
        ImageAdapter.ViewHolder holder = adapter.getHolder(position);
        Log.d("Debug", "holder" + holder.getImage());
        boolean keepGoing = true;
        if(!isEmptySquare(position)){
            keepGoing = false;
        }
        ArrayList<Integer> array = new ArrayList<>();
        while(keepGoing){
            position = position + modifier;
            Log.d("Debug", "current position" + position);
            if((position < 0 || position > 63)){
                Log.d("Debug", "broken edge" + position);
                array.clear();
                keepGoing = false;
            }
            if(!isEmptySquare(position)){
                Log.d("Debug", "turn:"+ turn);
                if((turn == 0 && whitePiece == board.getPiece(position))
                        || (turn == 1 && blackPiece == board.getPiece(position))){
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
            if(!(turn == 0 && whitePiece == board.getPiece(array.get(array.size()-1))
                    || turn == 1 && blackPiece == board.getPiece(array.get(array.size()-1)))){
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
        for(int i = 0; i<board.getSize(); i++){
            if(board.getPiece(i) == transparent){
                return 3;
            }else if(board.getPiece(i) == whitePiece){
                white++;
            }else if(board.getPiece(i) == blackPiece){
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

    public GameBoard getBoard(){
        return board;
    }

    public int getTurn(){
        return turn;
    }

    public void setAdapter(ImageAdapter adapter){
        this.adapter = adapter;
    }
}
