package com.example.mega.othello;

/**
 * Created by Shua on 5/2/2016.
 */
public class GameBoard {
    private GamePiece gameBoard[];
    private final static int BOARD_SIZE = 64;

    public static int getBoardSize(){
        return BOARD_SIZE;
    }

    public GameBoard(){

        gameBoard = new GamePiece[BOARD_SIZE];

        clearBoard();
    }

    public void clearBoard(){
        //resets the board to empty state
        for(int i = 0; i < BOARD_SIZE; i++){
            gameBoard[i] = new GamePiece("Empty");
        }
    }

    public void setMove(String color, int location){
        gameBoard[location].setColor(color);
    }

    public int checkForWin(){
        //return 0 for no win
        //return 1 for white win
        //return 2 for black win
        //return 3 for tie

        int whiteTotal = 0;
        int blackTotal = 0;

        //check each square for piece color
        for(int i = 0; i < BOARD_SIZE; i++){
            if(("Empty").equals(gameBoard[i].getColor())){
                return 0;
            }else if(("white").equals(gameBoard[i].getColor())){
                whiteTotal++;
            }else if(("Black").equals(gameBoard[i].getColor())){
                blackTotal++;
            }
        }

        //check winner
        if(whiteTotal > blackTotal){
            return 1;
        }else if(blackTotal > whiteTotal){
            return 2;
        }else{
            return 3;
        }

    }

}
