package com.example.mega.othello;

import java.util.HashMap;

/**
 * Created by Shua on 5/2/2016.
 */
public class GamePiece {

    HashMap<String, Integer> pieces;

    public GamePiece(){
        pieces.put("white", R.drawable.disc_white_hd);
        pieces.put("black", R.drawable.disc_black_hd);
        pieces.put("transparent", R.drawable.transparent_tile);
    }

    public int getColor(String color)
    {
        if(pieces.containsKey(color)){
            return pieces.get(color);
        }else{
            //TODO: throw error
            return 0;
        }
    }
}
