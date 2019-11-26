package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //if direction false -> up    if direction true -> down
    RelativeLayout mainLayout = null;
    Mole m1, m2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = this.findViewById(R.id.mainLayout);
        m1 = new Mole(this, 50, 50);
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
        m2.startAnimation();

    }
}
