package com.sarredondo.finalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sarredondo.datasource.Book;
import com.sarredondo.list.Adapter;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    ListView lstBooks;

    private ArrayList<Book> books = new ArrayList<Book>();
    private  ArrayList<Integer> lstImages = new ArrayList<Integer>();
    /*
    String[][] data = {
            {"Cien años de soledad", "gabo", "123134", "spanish", "norma"},
            {"A","A","A","A","A"}
    };*/
    int[] imgData = {R.drawable.cienanios, R.drawable.booka};

    Button btnUpCr;
    ImageButton btnDelete;
    EditText iBook, iAuthor, iISBN, iLanguage, iPublisher;
    ImageView ivUpCr;
    TextView txtTitle;

    private String operacion;

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
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();
                AlertDialog.Builder myBuilder =  new AlertDialog.Builder(BookListActivity.this);
                View myView = getLayoutInflater().inflate(R.layout.dialog_update, null);
                //btnDelete = findViewById(R.id.btnDelete);
                iBook = (EditText) myView.findViewById(R.id.iBook);
                iAuthor = (EditText) myView.findViewById(R.id.iAuthor);
                iISBN = (EditText) myView.findViewById(R.id.iISBN);
                iLanguage = (EditText) myView.findViewById(R.id.iLanguage);
                iPublisher = (EditText) myView.findViewById(R.id.iPublisher);
                ivUpCr = (ImageView) myView.findViewById(R.id.ivUpCr);
                btnUpCr = (Button) myView.findViewById(R.id.btnUpCr);
                txtTitle = (TextView) myView.findViewById(R.id.txtTitle);

                operacion = "CREATE";
                btnUpCr.setText(operacion);
                txtTitle.setText(operacion);
                //btnDelete.setVisibility(View.INVISIBLE);


                btnUpCr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iBook.getText().toString().isEmpty() ||
                            iAuthor.getText().toString().isEmpty() ||
                            iISBN.getText().toString().isEmpty() ||
                            iLanguage.getText().toString().isEmpty() ||
                            iPublisher.getText().toString().isEmpty()){
                            Toast.makeText(BookListActivity.this, "One row is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ivUpCr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadImage();
                    }
                });
                myBuilder.setView(myView);
                AlertDialog d = myBuilder.create();
                d.show();
            }
        });
        lstBooks = (ListView) findViewById(R.id.lstBooks);
        loadData();
        lstBooks.setAdapter(new Adapter(this,books,lstImages));


        lstBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder myBuilder =  new AlertDialog.Builder(BookListActivity.this);
                View myView = getLayoutInflater().inflate(R.layout.dialog_update, null);
                //btnDelete = findViewById(R.id.btnDelete);
                iBook = (EditText) myView.findViewById(R.id.iBook);
                iAuthor = (EditText) myView.findViewById(R.id.iAuthor);
                iISBN = (EditText) myView.findViewById(R.id.iISBN);
                iLanguage = (EditText) myView.findViewById(R.id.iLanguage);
                iPublisher = (EditText) myView.findViewById(R.id.iPublisher);
                ivUpCr = (ImageView) myView.findViewById(R.id.ivUpCr);
                btnUpCr = (Button) myView.findViewById(R.id.btnUpCr);
                txtTitle = (TextView) myView.findViewById(R.id.txtTitle);

                operacion = "UPDATE";
                btnUpCr.setText(operacion);
                txtTitle.setText(operacion);
                //btnDelete.setVisibility(View.VISIBLE);

                Book selected = books.get(position);
                setContent(selected, position);

                btnUpCr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iBook.getText().toString().isEmpty() ||
                                iAuthor.getText().toString().isEmpty() ||
                                iISBN.getText().toString().isEmpty() ||
                                iLanguage.getText().toString().isEmpty() ||
                                iPublisher.getText().toString().isEmpty()){
                            Toast.makeText(BookListActivity.this, "One row is empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                myBuilder.setView(myView);
                AlertDialog d = myBuilder.create();
                d.show();
            }
        });

    }


    private void loadImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("/image");
        startActivityForResult(Intent.createChooser(intent, "seleccione la imagen"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Uri path = data.getData();
            ivUpCr.setImageURI(path);
        }
    }

    private void loadData(){
        books.add(new Book("Cien años de soledad","Gabo","1233","Spanish","Debolsillo","image/book1.jpg"));
        books.add(new Book("Harry Potter","J.R","6563","English","f","image/book1.jpg"));
        lstImages.add(R.drawable.cienanios);
        lstImages.add(R.drawable.booka);
    }

    private void setContent(Book selected, int position){
        iBook.setText(selected.getName());
        iAuthor.setText(selected.getAuthor());
        iISBN.setText(selected.getISBN());
        iLanguage.setText(selected.getLanguage());
        iPublisher.setText(selected.getPublisher());
        iPublisher.setText("Hola");
        ivUpCr.setImageResource(lstImages.get(position));
    }

    private void clearContent(Book selected, int position){
        iBook.setText("");
        iAuthor.setText("");
        iISBN.setText("");
        iLanguage.setText("");
        iPublisher.setText("");
        ivUpCr.setImageResource(lstImages.get(position));
    }
    
}
