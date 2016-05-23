package com.example.mega.othello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.mega.game.gameplay.GameController;

public class OthelloGame extends AppCompatActivity {
    GridView gridView;
    ImageAdapter adapter;
    GameController controller = new GameController();
    int whitePiece = controller.getBoard().getColor("white");
    int blackPiece = controller.getBoard().getColor("black");
    int transparent = controller.getBoard().getColor("transparent");
    int turn = controller.getTurn();
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othello_game);

        getIntent();

        gridView = (GridView) findViewById(R.id.othelloGrid);
        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);
        controller.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Log.d("Debug", "ClickPosition" + position);
                if(!controller.isValidMove(position)){
                    return;
                }
                ImageAdapter.ViewHolder holder = (ImageAdapter.ViewHolder) v.getTag();
                //int imageID = (int) holder.image.getTag();
                if(controller.getBoard().getPiece(position) == transparent){
                    if(turn == 0){
                        holder.image.setImageResource(whitePiece);
                        controller.getBoard().setPiece(whitePiece, position);
                        holder.image.setBackgroundResource(R.drawable.disc_black_hd);
                    }/*else{
                        holder.image.setImageResource(R.drawable.disc_black_hd);
                        images[position] = R.drawable.disc_black_hd;
                    }*/
                    turn = 1;
                    message = controller.cpuMove();
                    if(("").equals(message)){
                        Toast.makeText(OthelloGame.this, message, Toast.LENGTH_SHORT).show();
                    }

                }
                //Toast.makeText(OthelloGame.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
