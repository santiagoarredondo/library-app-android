package com.sarredondo.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sarredondo.datasource.Book;
import com.sarredondo.finalapp.R;

import java.io.PipedOutputStream;
import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    //instanciar el archivo de diseño xml
    private static LayoutInflater inflater = null;

    Context context;
    ArrayList<Book> books = new ArrayList<Book>();
    ArrayList<Integer> images = new ArrayList<Integer>();
    String[][] data;
    int[] imgData;

    public Adapter(Context context, ArrayList<Book> books, ArrayList<Integer> images) {
        this.context = context;
        this.books = books;
        this.images = images;
        // instancia el xml
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.list_element, null);
        TextView title = (TextView) view.findViewById(R.id.txtTitle);
        TextView author = (TextView) view.findViewById(R.id.txtAuthor);
        TextView isbn = (TextView) view.findViewById(R.id.txtISBN);
        TextView language = (TextView) view.findViewById(R.id.txtLanguage);
        TextView publisher = (TextView) view.findViewById(R.id.txtPublisher);
        ImageView image = (ImageView) view.findViewById(R.id.imgBook);
        // llenar la lista
        Book currentBook = books.get(position);
        title.setText(currentBook.getName());
        author.setText(currentBook.getAuthor());
        isbn.setText(currentBook.getISBN());
        language.setText(currentBook.getLanguage());
        publisher.setText(currentBook.getPublisher());
        image.setImageResource((Integer)images.get(position));

        /*
        title.setText(data[position][0]);
        author.setText(data[position][1]);
        isbn.setText(data[position][2]);
        language.setText(data[position][3]);
        publisher.setText(data[position][4 ]);
        image.setImageResource((Integer)imgData[position]);
        */
        image.setTag(position);
        /*
        view.setTag(position);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (Integer)v.getTag();
                Toast.makeText(context,data[0][pos].toString(), Toast.LENGTH_SHORT);
            }
        });
        */
        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //para visualizar la imagen
                Intent viewFinder = new Intent(context, ImageVisualizator.class);
                viewFinder.putExtra("IMG", images.get((Integer)v.getTag()));
                context.startActivity(viewFinder);
            }
        });
        return view;
    }
}
