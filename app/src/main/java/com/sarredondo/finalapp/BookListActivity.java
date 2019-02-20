package com.sarredondo.finalapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.sarredondo.list.Adapter;

public class BookListActivity extends AppCompatActivity {

    ListView lstBooks;

    String[][] data = {
            {"Cien años de soledad", "gabo", "123134", "spanish", "norma"},
            {"A","A","A","A","A"}
    };
    int[] imgData = {R.drawable.cienanios, R.drawable.booka};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        lstBooks = (ListView) findViewById(R.id.lstBooks);
        lstBooks.setAdapter(new Adapter(this,data,imgData));
    }

}
