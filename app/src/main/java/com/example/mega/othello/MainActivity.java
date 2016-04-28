package com.example.mega.othello;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    /* sound for button (super basic)*/
    MediaPlayer menuSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* sound for button (super basic)*/
        menuSound = MediaPlayer.create(this, R.raw.menu_select0);

    }

    public void launchNewGame(View view){
        Intent intent = new Intent(this, OthelloGame.class);
        startActivity(intent);

        /* sound for button (super basic)*/
        menuSound.start();
    }
}
