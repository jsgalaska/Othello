package com.example.mega.othello;

/**
 * Created by Shua on 5/2/2016.
 */
public class GameBoard {
    private static volatile GameBoard instance;
    int whitePiece = R.drawable.i14;
    int blackPiece = R.drawable.i0;
    int transparent = R.drawable.transparent_tile;

    public int[] images ={
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, whitePiece, //Middle top
            blackPiece, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, blackPiece, //Middle bottom
            whitePiece, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
            transparent, transparent,
    };

    private GameBoard(){

    }

    public static GameBoard getInstance() {
        if (instance == null ) {
            synchronized (GameBoard.class) {
                if (instance == null) {
                    instance = new GameBoard();
                }
            }
        }

        return instance;
    }

    public int getSquare(int position){
        return images[position];
    }

    public void setSquare(int position, int color){
        images[position] = color;
    }

    public void newInstance(){
        instance = null;
    }

}
