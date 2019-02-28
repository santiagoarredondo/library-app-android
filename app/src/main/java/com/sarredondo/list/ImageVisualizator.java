package com.sarredondo.list;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.sarredondo.finalapp.R;

public class ImageVisualizator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_visualizator);

        ImageView img = (ImageView) findViewById(R.id.imgFull);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        if (b!=null){
            //img.setImageResource(b.getInt("IMG"));
            byte[] im = b.getByteArray("IMG");
            try {
                byte[] byteArray = im;
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                img.setImageBitmap(bmp);
                //image.setImageResource((Integer) images.get(position));
                //image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));
            } catch (Exception e) {
                img.setImageResource(R.drawable.noimage);
            }
        }
    }

}
