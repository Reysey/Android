package com.reydev.tuto.androiddatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

public class AnimatedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated);



        // SHOW THE OPTIONS POPUP
        LinearLayout org = (LinearLayout) findViewById(R.id.origin);
        org.setAlpha(0f);
        org.setVisibility(View.VISIBLE);
        org.animate()
                .alpha(0.9f)
                .setDuration(1000)
                .setListener(null);

        LinearLayout  animatedLayout = (LinearLayout)findViewById(R.id.AnimatedLayout);
        ViewGroup.LayoutParams animatedLayout_params = animatedLayout.getLayoutParams();
        animatedLayout_params.height    = 0;
        animatedLayout_params.width     = ViewGroup.LayoutParams.MATCH_PARENT;
        animatedLayout.setLayoutParams(animatedLayout_params);
//                                                        //animatedLayout.getMeasuredHeight()
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int animationPercentage = (Integer) animator.getAnimatedValue();
                Button btn = findViewById(R.id.btn_title);
                btn.setText("VALUE: [  "+animationPercentage+"  ]");

                LinearLayout  animatedLayout = (LinearLayout)findViewById(R.id.AnimatedLayout);
                ViewGroup.LayoutParams animatedLayout_params = findViewById(R.id.AnimatedLayout).getLayoutParams();
                Log.e("ON ANIMATION", "VALUE SIZE: [  "+animationPercentage+"  ]");
                LinearLayout org = (LinearLayout)findViewById(R.id.origin);
                ViewGroup.LayoutParams para = org.getLayoutParams();
                Log.e("ON CREATE", "SIZE: [ "+ org.getMeasuredHeight()+" ]" );
                //animatedLayout_params.height =  ViewGroup.LayoutParams.MATCH_PARENT;
                if(animationPercentage < 5){
                    Log.e(" ON ANIMATION" , "SKIP");
                }else
                    if(animationPercentage < org.getMeasuredHeight()){
                        animatedLayout_params.height =  animationPercentage;
                    }

//                animatedLayout_params.width =  ViewGroup.LayoutParams.MATCH_PARENT;
                findViewById(R.id.AnimatedLayout).setLayoutParams(animatedLayout_params);
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();




        // HIDE THE OPTIONS POPUP
        Button btn_close = (Button)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout  animatedLayout = (LinearLayout)findViewById(R.id.AnimatedLayout);
                ViewGroup.LayoutParams animatedLayout_params = animatedLayout.getLayoutParams();

//                                                        //animatedLayout.getMeasuredHeight()
                ValueAnimator valueAnimator = ValueAnimator.ofInt(animatedLayout.getMeasuredHeight(), 0);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        int animationPercentage = (Integer) animator.getAnimatedValue();
                        Log.e("ON ANIMATION", "VALUE SIZE: [  "+animationPercentage+"  ]");
                        LinearLayout  animatedLayout = (LinearLayout)findViewById(R.id.AnimatedLayout);
                        ViewGroup.LayoutParams animatedLayout_params = findViewById(R.id.AnimatedLayout).getLayoutParams();
                        animatedLayout_params.height =  animationPercentage;

                            if(animationPercentage == 0) {
                                animatedLayout.setVisibility(View.GONE);
                                //Out transition: (alpha from 0.5 to 0)
                                LinearLayout org = (LinearLayout) findViewById(R.id.origin);
                                org.setAlpha(0.9f);
                                org.animate()
                                        .alpha(0f)
                                        .setDuration(1000)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                LinearLayout org = (LinearLayout) findViewById(R.id.origin);
                                                org.setVisibility(View.GONE);
                                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(i);
                                            }
                                        });

                            }
                        findViewById(R.id.AnimatedLayout).setLayoutParams(animatedLayout_params);
                    }

                });
                valueAnimator.setDuration(500);
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                valueAnimator.start();


            }
        });



    }
}