package com.example.proxymed.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;
import androidx.databinding.BindingAdapter;

import com.example.proxymed.R;

public class BindingUtils{


    @BindingAdapter("photoProfil")
    public static void setPhotoProfil(ImageButton imageButton, byte[] photoProfil) {
        if (photoProfil != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(photoProfil, 0, photoProfil.length);
            imageButton.setImageBitmap(bitmap);
        } else {
            imageButton.setImageResource(R.drawable.defaultprofile_image); // Une image par défaut si le profil est vide
        }
    }
}

