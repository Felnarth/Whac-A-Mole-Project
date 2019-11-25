package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //if direction false -> up    if direction true -> down
    private boolean direction = true;
    private TextView animateTextView = null;
    private ObjectAnimator textViewAnimator = null;
    ConstraintLayout mainLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = this.findViewById(R.id.mainLayout);

        animateTextView = findViewById(R.id.helloWorld);
        textViewAnimator = ObjectAnimator.ofFloat(animateTextView, "translationY", 0f, 0f);
        textViewAnimator.setDuration(2000);
        textViewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        animateTextView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(direction)
                    textViewAnimator.setFloatValues(0f,500f);
                else
                    textViewAnimator.setFloatValues(500f,0f);

                direction = !direction;
                textViewAnimator.start();
            }
        });




    }
}
