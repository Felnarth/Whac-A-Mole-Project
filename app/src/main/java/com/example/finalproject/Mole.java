package com.example.finalproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.view.View;


import androidx.appcompat.widget.AppCompatImageView;

public class Mole extends AppCompatImageView {

    private Context context;
    private ObjectAnimator viewAnimator;
    private boolean hasBeenWhacked = false;
    AnimatorSet scaleSet;

    //constructor for custom ImageView
    public Mole(Context context, Path path, int duration) {
        super(context);
        this.context = context;
        this.setOnClickListener(new Listener());

        //set src instance of custom ImageView to resource
        this.setImageDrawable(context.getResources().getDrawable(getResources().getIdentifier("mole", "drawable", context.getPackageName())));


        scaleSet = new AnimatorSet();
        scaleSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                hasBeenWhacked = true;
            }
        });

        //create animation
        viewAnimator = ObjectAnimator.ofFloat(this, "translationX", "translationY", path);
        viewAnimator.setDuration(duration);
        viewAnimator.setRepeatMode(ValueAnimator.REVERSE);
        viewAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    public void startAnimation()
    {
        viewAnimator.start();
    }

    public Mole getOuter() { return this; }

    public boolean isWhacked() { return hasBeenWhacked; }

    //listener for when whacked
    class Listener implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {
            viewAnimator.cancel();
            ((AppCompatImageView) view).setImageDrawable(context.getResources().getDrawable(getResources().getIdentifier("dizzymole", "drawable", context.getPackageName())));
            ObjectAnimator scale1 = ObjectAnimator.ofFloat(getOuter(), "scaleX", 0);
            ObjectAnimator scale2 = ObjectAnimator.ofFloat(getOuter(), "scaleY", 0);
            scaleSet.setDuration(2000);
            scaleSet.playTogether(scale1, scale2);
            scaleSet.start();
        }
    }
}