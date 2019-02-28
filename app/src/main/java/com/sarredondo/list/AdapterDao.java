package com.sarredondo.list;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.sarredondo.datasource.Book;
import com.sarredondo.finalapp.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdapterDao extends ArrayAdapter<String> {

    //instanciar el archivo de diseño xml
    private static LayoutInflater inflater = null;

    Context context;
    private List lst;
    private Dao<Book, Integer> lstDao;

    public AdapterDao(Context context, int resource, List objects, Dao<Book, Integer> lstDao) {
        super(context, resource, objects);
        this.context = context;
        this.lst = lst;
        this.lstDao = lstDao;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.list_element, null);
        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_element, null);
        }
        TextView title = (TextView) view.findViewById(R.id.txtTitle);
        TextView author = (TextView) view.findViewById(R.id.txtAuthor);
        TextView isbn = (TextView) view.findViewById(R.id.txtISBN);
        TextView language = (TextView) view.findViewById(R.id.txtLanguage);
        TextView publisher = (TextView) view.findViewById(R.id.txtPublisher);
        ImageView image = (ImageView) view.findViewById(R.id.imgBook);
        try {
            Book currentBook = lstDao.queryForAll().get(position);
            title.setText(currentBook.getName());
            author.setText(currentBook.getAuthor());
            isbn.setText(currentBook.getISBN());
            language.setText(currentBook.getLanguage());
            publisher.setText(currentBook.getPublisher());
            image.setImageResource(R.drawable.noimage);
            //image.setImageResource((Integer)images.get(position));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return view;
    }
}
