package com.mega.game.gameplay;

import java.util.ArrayList;

/**
 * Created by Shua on 5/11/2016.
 */
public class GameSession {
    ArrayList<Player> players = new ArrayList<Player>();
    int turn = 0;

    public GameSession(){
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.get(0).setColor("white");
        players.get(1).setColor("black");
    }

    public void nextTurn(){
        turn = turn+1%2;
    }

    public int getTurn(){
        return turn;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public Player getPlayer(int player){
        return players.get(player);
    }

}
