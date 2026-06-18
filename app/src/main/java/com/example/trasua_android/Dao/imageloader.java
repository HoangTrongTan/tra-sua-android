package com.example.trasua_android.Dao;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
public class imageloader {
    public static void loadImage(Context context, Uri uri, ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .into(imageView);
    }
    public static Uri getImageUriFromGallery(Context context, Intent data) {
        return data.getData();
    }
}
