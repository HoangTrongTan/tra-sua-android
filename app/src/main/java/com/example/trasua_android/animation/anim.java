package com.example.trasua_android.animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.trasua_android.R;

public class anim {
    public static void startAnimation(Context context, Button btn){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.animation);
        btn.startAnimation(animation);
    }
}
