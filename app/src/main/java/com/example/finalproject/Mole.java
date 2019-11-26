package com.example.finalproject;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import androidx.appcompat.widget.AppCompatImageView;

public class Mole extends AppCompatImageView {

    private float x, y;
    private Context context;
    private ObjectAnimator viewAnimator;

    public Mole(Context context, float x, float y) {
        super(context);
        this.context = context;
        this.setOnClickListener(new Listener());
        this.setImageDrawable(context.getResources().getDrawable(getResources().getIdentifier("mole", "drawable", context.getPackageName())));
        this.x = x;
        this.y = y;

        viewAnimator = ObjectAnimator.ofFloat(this, "translationY", 0f, 200f);
        viewAnimator.setDuration(2000);
        viewAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        viewAnimator.setRepeatMode(ValueAnimator.REVERSE);
        viewAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    public void startAnimation()
    {
        viewAnimator.start();
    }

    public Mole getOuter() {
        return this;
    }

    class Listener implements View.OnClickListener {
        @Override
        public void onClick(View view)
        {

            ((AppCompatImageView) view).setImageDrawable(context.getResources().getDrawable(getResources().getIdentifier("dizzymole", "drawable", context.getPackageName())));
            ObjectAnimator scale1 = ObjectAnimator.ofFloat(getOuter(), "scaleX", 0);
            scale1.setDuration(2000);
            ObjectAnimator scale2 = ObjectAnimator.ofFloat(getOuter(), "scaleY", 0);
            scale2.setDuration(2000);
            scale1.start();
            scale2.start();
        }
    }

    //will need a listener for when animation is finished
}
