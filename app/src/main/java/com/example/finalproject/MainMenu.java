package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    public static final String gameDifficulty = "com.example.finalproject.gameDifficulty";
    public static final String gameNumMoles = "com.example.finalproject.gameNumMoles";
    public static final int GAME_COMPLETE = 1;
    private TextView scoreReport = null;
    private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        scoreReport = this.findViewById(R.id.scoreReport);

        //setup and start music
        //Music: Ben Sound - The Elevator Bossanova (Royalty Free)
        music = MediaPlayer.create(this, R.raw.elev);
        music.setLooping(true);
        music.start();
    }

    public void startGame(View view) {
        //Creates a relationship between this activity (MainMenu) and the game activity (MoleGame)
        //allows for the accessing of data between the two activities
        Intent intent = new Intent(this, MoleGame.class);

        //gets selected settings
        Spinner difficultySpinner = findViewById(R.id.difficultySpinner);
        Spinner numMolesSpinner = findViewById(R.id.numMolesSpinner);

        //saves them as Extras that can be accessed by MoleGame activity
        intent.putExtra(gameDifficulty, difficultySpinner.getSelectedItem().toString());
        intent.putExtra(gameNumMoles, Integer.parseInt(numMolesSpinner.getSelectedItem().toString()));

        //starts MoleGame activity while telling the activity that it expects a result
        startActivityForResult(intent, GAME_COMPLETE);
    }

    //this listener waits for MoleGame to complete and runs immediately after
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GAME_COMPLETE)
        {
            try {
                //set score
                scoreReport.setText("Nice work! Score: " + data.getStringExtra("GAME_SCORE"));
                //if game was left/canceled before finishing
            }   catch(RuntimeException e) { scoreReport.setText("Game Canceled");  }
        }
    }
}
