package com.sarredondo.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.sarredondo.datasource.Book;
import com.sarredondo.finalapp.R;

import java.io.ByteArrayOutputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {

    //instanciar el archivo de diseño xml
    private static LayoutInflater inflater = null;

    Context context;
    ArrayList<Book> books = new ArrayList<Book>();
    ArrayList<Integer> images = new ArrayList<Integer>();
    String[][] data;
    int[] imgData;
    public byte[] currentImage;


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
        final Book currentBook = books.get(position);
        title.setText(currentBook.getName());
        author.setText(currentBook.getAuthor());
        isbn.setText(currentBook.getISBN());
        language.setText(currentBook.getLanguage());
        publisher.setText(currentBook.getPublisher());
        try {
            byte[] byteArray = currentBook.getImg();
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            image.setImageBitmap(bmp);

        } catch (Exception e) {
            image.setImageResource(R.drawable.noimage);
        }

        //image.setTag(position);
        image.setTag(getImage(image));
        /*
        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //para visualizar la imagen
                Intent viewFinder = new Intent(context, ImageVisualizator.class);
                viewFinder.putExtra("IMG", images.get((Integer)v.getTag()));
                context.startActivity(viewFinder);
            }
        });*/
        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //para visualizar la imagen
                Intent viewFinder = new Intent(context, ImageVisualizator.class);
                viewFinder.putExtra("IMG", currentBook.getImg());
                context.startActivity(viewFinder);
            }
        });
        return view;
    }

    private byte[] getImage(ImageView iv){
        byte[] imageInByte = null;
        try {
            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            imageInByte = baos.toByteArray();
            baos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return imageInByte;
    }
}
