package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

//Look at these sources:
//https://developer.android.com/training/animation/reposition-view#java
//http://cogitolearning.co.uk/2014/01/android-property-animations-controlling-animation-flow/

public class MoleGame extends AppCompatActivity {

    private RelativeLayout mainLayout = null;
    private String gameDifficultySetting;
    private int numberOfMoles;
    private int width, height;
    Random r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create random generator
        r = new Random();

        //Retrieve values from Main Menu
        Intent intent = getIntent();
        gameDifficultySetting = intent.getStringExtra(MainMenu.gameDifficulty);
        numberOfMoles = intent.getIntExtra(MainMenu.gameNumMoles, 5);

        //Get layout that game will be played on
        mainLayout = this.findViewById(R.id.mainLayout);

        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                // gets called after layout has been done but before display.
                if (Build.VERSION.SDK_INT < 16) {
                    mainLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                 width  = mainLayout.getMeasuredWidth();
                 height = mainLayout.getMeasuredHeight();
                startGame();
            }
        });



        /*m1 = new Mole(this, 50, 50);
        m2 = new Mole(this, 100, 100);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200,200);
            params.leftMargin = 70;
            params.rightMargin = 80;
        mainLayout.addView(m1, params);

        params = new RelativeLayout.LayoutParams(200,200);
            params.leftMargin = 500;
            params.rightMargin = 600;
        mainLayout.addView(m2, params);

        m1.startAnimation();
        m2.startAnimation(); */

    }

    public void startGame()
    {
        Path path;
        RelativeLayout.LayoutParams params;

        //Create and configure moles
        ArrayList<Mole> moles = new ArrayList<Mole>();
        for(int i = 0; i < numberOfMoles; i++)
        {
            path = new Path();

            for(int j = 0; j < r.nextInt(10) + 1; j++)
                path.lineTo(r.nextFloat() * width, r.nextFloat() * height);

            moles.add(new Mole(this, path));
        }

        Drawable img = this.getResources().getDrawable(getResources().getIdentifier("mole", "drawable", this.getPackageName()));

        //200 is a hardcoded value for defining size of image
        for(Mole m : moles)
        {
            params = new RelativeLayout.LayoutParams(200,200);
            params.leftMargin = r.nextInt(width);
            params.topMargin = r.nextInt(height);
            mainLayout.addView(m);
            m.startAnimation();
        }
    }
}
