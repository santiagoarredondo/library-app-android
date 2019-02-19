package com.sarredondo.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            img.setImageResource(b.getInt("IMG"));
        }
    }
}
