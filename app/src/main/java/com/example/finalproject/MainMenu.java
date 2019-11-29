package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class MainMenu extends AppCompatActivity {

    public static final String gameDifficulty = "com.example.finalproject.gameDifficulty";
    public static final String gameNumMoles = "com.example.finalproject.gameNumMoles";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, MoleGame.class);
        Spinner difficultySpinner = findViewById(R.id.difficultySpinner);
        Spinner numMolesSpinner = findViewById(R.id.numMolesSpinner);
        intent.putExtra(gameDifficulty, difficultySpinner.getSelectedItem().toString());
        intent.putExtra(gameNumMoles, Integer.parseInt(numMolesSpinner.getSelectedItem().toString()));
        startActivity(intent);
    }
}
