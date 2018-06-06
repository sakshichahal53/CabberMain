package com.example.sakshi.cabber;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ReferralCodeActivity extends AppCompatActivity {

    ImageView im_background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.referral_code);

        im_background=findViewById(R.id.im_bg);
//        Glide.with(this)
//                .load()
//                .bitmapTransform(new BlurTransformation(context))
//                .centerCrop()
//                .into(im_background);
    }
}
