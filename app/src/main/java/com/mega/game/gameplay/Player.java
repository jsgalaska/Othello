package com.mega.game.gameplay;

/**
 * Created by Shua on 5/11/2016.
 */
public class Player {
    String name;
    String color;

    public Player(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }
}
