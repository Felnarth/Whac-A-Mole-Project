package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MoleGame extends AppCompatActivity {

    private RelativeLayout mainLayout = null;
    private TextView timerView = null;
    private String gameDifficultySetting;
    private int numberOfMoles, width, height, countDown = 100, score = 0, FASTEST_SPEED, SLOWEST_SPEED;
    private ArrayList<Mole> moles;
    private Random r;
    private Path path;
    private boolean gameRunning = true;
    private ArrayList<Integer> markedForRemoval;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hold onto context to add moles to
        final Context context = this;

        //get the view that will change with the tick of the timer
        timerView = this.findViewById(R.id.timerView);

        //Create random generator
        r = new Random();

        //Add mole routine
        final Runnable addMolesRun = new Runnable() {
            @Override
            public void run() {
                addMole();
                synchronized (this) {
                    this.notify();
                }
            }
        };

        //Update Countdown TextView
        final Runnable updateTimer = new Runnable() {
            @Override
            public void run() {
                timerView.setText(String.valueOf(countDown));
            }
        };

        //ArrayList that keeps track of whacked moles that need to be removed from screen
        markedForRemoval = new ArrayList<>();

        //Retrieve game options from Main Menu
        intent = getIntent();
        gameDifficultySetting = intent.getStringExtra(MainMenu.gameDifficulty);
        numberOfMoles = intent.getIntExtra(MainMenu.gameNumMoles, 5);

        //Get layout that game will be played on
        mainLayout = this.findViewById(R.id.mainLayout);

        //https://stackoverflow.com/questions/28264139/view-getviewtreeobserver-addongloballayoutlistener-leaks-fragment
        //referenced above source and others like it to learn about listener
        //the following listener waits for the layout to be drawn so that its attributes can be accessed
        mainLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                //this listener is only called once, so remove listener
                mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                //get width and height of layout
                width  = mainLayout.getMeasuredWidth();
                height = mainLayout.getMeasuredHeight();

                //set bounds of random number generator based on difficulty setting
                switch (gameDifficultySetting) {
                    case "Easy":
                        FASTEST_SPEED = 5000;
                        SLOWEST_SPEED = 10000;
                        break;
                    case "Moderate":
                        FASTEST_SPEED = 3500;
                        SLOWEST_SPEED = 7000;
                        break;
                    case "Hard":
                        FASTEST_SPEED = 2000;
                        SLOWEST_SPEED = 5000;
                        break;
                }

                //start countdown timer (100 seconds)
                CountDownTimer waitTimer = new CountDownTimer(countDown * 1000, 1000) {

                    //executes every tick (every second)
                    public void onTick(long millisUntilFinished) {
                        //changes to views or animation must be done on UI Thread
                        runOnUiThread(updateTimer);

                        //decrement countdown
                        countDown--;
                    }

                    //when timer is finished, stop game and return to menu with score
                    public void onFinish() {
                        gameRunning = false;
                        intent.putExtra("GAME_SCORE", String.valueOf(score));
                        setResult(1,intent);
                        finish();
                    }
                };  waitTimer.start();

                //initial start of game
                startGame();

                //Create thread that watches for moles getting whacked
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                    while(gameRunning)
                    {
                        //for all moles currently on screen, check if whacked
                        for(Mole m : moles)
                            if(m.isWhacked())
                                markedForRemoval.add(moles.indexOf(m));

                            //for all whacked moles, remove from list of on-screen moles and add a new one
                            for(Integer i : markedForRemoval) {
                                score++;
                                moles.remove(i.intValue());
                                //synchronize on adding new mole
                                synchronized (addMolesRun) {
                                    runOnUiThread(addMolesRun);
                                    try {
                                        //stops current thread until mole is added to screen
                                        addMolesRun.wait();
                                    } catch (InterruptedException e) { e.printStackTrace();  }
                                }

                            }
                        //reset list of moles to be removed
                        markedForRemoval.clear();
                    }
                    }
                }; thread.start();
            }
        });
    }

    //This method only adds one mole. This method is called every time a mole is whacked
    public void addMole() {
        path = new Path();

        //set random starting position for mole
        path.moveTo(r.nextInt(width - 40), r.nextInt(height - 40));

        //add a random number of random points that the mole will move to
        for(int j = 0; j < r.nextInt(10) + 1; j++)
            path.lineTo(r.nextFloat() * width, r.nextFloat() * height);

        //create mole with random speed based on chosen difficulty
        //lower number is faster, higher number is slower
        Mole m = new Mole(this, path, r.nextInt(SLOWEST_SPEED) + FASTEST_SPEED);

        moles.add(m);

        mainLayout.addView(m);
        m.startAnimation();
    }

    //Creates moles for start of game
    public void startGame() {
        //Create and configure moles
        moles = new ArrayList<>();

        //creates number of moles chosen in the menu
        for(int i = 0; i < numberOfMoles; i++)
        {
            path = new Path();

            //set random start position for mole
            path.moveTo(r.nextInt(width - 40), r.nextInt(height - 40));

            //add a random number of random points that the mole will move to
            for(int j = 0; j < r.nextInt(10) + 1; j++)
                path.lineTo(r.nextFloat() * width, r.nextFloat() * height);

            //create mole with random speed based on chosen difficulty
            //lower number is faster, higher number is slower
            moles.add(new Mole(this, path, r.nextInt(SLOWEST_SPEED) + FASTEST_SPEED));
        }

        //add all moles to layout and start their animation
        for(Mole m : moles)
        {
            mainLayout.addView(m);
            m.startAnimation();
        }
    }
}
