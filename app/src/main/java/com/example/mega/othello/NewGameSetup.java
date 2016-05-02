package com.example.mega.othello;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewGameSetup extends AppCompatActivity {

    // sound for button
    MediaPlayer menuSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game_setup);

        // sound for button
        menuSound = MediaPlayer.create(this, R.raw.menu_select0);
    }

    public void launchGame(View view){
        Intent intent = new Intent(this, OthelloGame.class);
        startActivity(intent);
        menuSound.start(); // sound for button
    }
    public void returnToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        menuSound.start(); // sound for button
    }
}
