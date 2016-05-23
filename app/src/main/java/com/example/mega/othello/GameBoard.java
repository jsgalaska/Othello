package com.example.mega.othello;

import java.util.HashMap;

/**
 * Created by Shua on 5/2/2016.
 */
public class GameBoard {
    HashMap<String, Integer> pieces = new HashMap<>();
    int whitePiece;
    int blackPiece;
    int transparent;
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

    public GameBoard(){
        pieces.put("white", R.drawable.disc_white_hd);
        pieces.put("black", R.drawable.disc_black_hd);
        pieces.put("transparent", R.drawable.transparent_tile);

        whitePiece = pieces.get("white");
        blackPiece = pieces.get("black");
        transparent = pieces.get("transparent");
    }

    public void setPiece(int color, int position){
        images[position] = color;
    }

    public int getPiece(int position){
        return images[position];
    }

    public int getSize(){
        return images.length;
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
