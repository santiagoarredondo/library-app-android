package com.sarredondo.finalapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.types.ByteArrayType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.DatabaseConnection;
import com.sarredondo.datasource.Book;
import com.sarredondo.datasource.DataBaseHelper;
import com.sarredondo.list.Adapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    // Reference of DatabaseHelper class to access its DAOs and other components
    private DataBaseHelper databaseHelper = null;
    private Dao<Book,Integer> bookDao;

    ListView lstBooks;

    private ArrayList<Book> books = new ArrayList<Book>();
    private  ArrayList<Integer> lstImages = new ArrayList<Integer>();
    private  ArrayList<ByteArrayType> lstIcons = new ArrayList<ByteArrayType>();
    /*
    String[][] data = {
            {"Cien años de soledad", "gabo", "123134", "spanish", "norma"},
            {"A","A","A","A","A"}
    };*/
    int[] imgData = {R.drawable.cienanios, R.drawable.booka};

    Button btnUpCr, btnDel;
    EditText iBook, iAuthor, iISBN, iLanguage, iPublisher;
    ImageView ivUpCr;
    TextView txtTitle;

    private String operacion;
    private String operacionButtonDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.plus);


        loadData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();
                final AlertDialog.Builder myBuilder =  new AlertDialog.Builder(BookListActivity.this);
                final View myView = getLayoutInflater().inflate(R.layout.dialog_update, null);
                //final AlertDialog sl = myBuilder.create();
                iBook = (EditText) myView.findViewById(R.id.iBook);
                iAuthor = (EditText) myView.findViewById(R.id.iAuthor);
                iISBN = (EditText) myView.findViewById(R.id.iISBN);
                iLanguage = (EditText) myView.findViewById(R.id.iLanguage);
                iPublisher = (EditText) myView.findViewById(R.id.iPublisher);
                ivUpCr = (ImageView) myView.findViewById(R.id.ivUpCr);
                btnUpCr = (Button) myView.findViewById(R.id.btnUpCr);
                btnDel = (Button) myView.findViewById(R.id.btnDel);
                txtTitle = (TextView) myView.findViewById(R.id.txtTitle);

                operacion = "CREATE";
                operacionButtonDel = "ERASE";
                btnUpCr.setText(operacion);
                txtTitle.setText(operacion);
                btnDel.setText(operacionButtonDel);

                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearContent();
                    }
                });

                btnUpCr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iBook.getText().toString().isEmpty() ||
                                iAuthor.getText().toString().isEmpty() ||
                                iISBN.getText().toString().isEmpty() ||
                                iLanguage.getText().toString().isEmpty() ||
                                iPublisher.getText().toString().isEmpty()){
                            Toast.makeText(BookListActivity.this, "One row is empty", Toast.LENGTH_SHORT).show();
                        }else{
                            try {
                                bookDao = getHelper().getBookDAO();
                                //bookDao.create(new Book("Cien años de soledad","Gabo","1233","Spanish","Debolsillo","image/book1.jpg"));
                                // bookDao.create(new Book("The Lord Of The Rings","J.R Tolkien","264654","English","booket","image/book1.jpg"));
                                String b = iBook.getText().toString();
                                String a = iAuthor.getText().toString();
                                String i = iISBN.getText().toString();
                                String l = iLanguage.getText().toString();
                                String p = iPublisher.getText().toString();
                                bookDao.create(new Book(b,a,i,l,p,R.drawable.noimage+"",getImage(ivUpCr)));
                                //bookDao.create(new Book(b,a,i,l,p,R.drawable.noimage+"",null));
                                lstBooks.setAdapter(null);
                                lstBooks = (ListView) findViewById(R.id.lstBooks);
                                loadData();
                                lstBooks.setAdapter(new Adapter(BookListActivity.this,books,lstImages));
                                // cerrar la ventana
                                //finish();
                                Intent newWindow = new Intent(BookListActivity.this, BookListActivity.class);
                                startActivity(newWindow);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
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
                d.show();//-----------------
            }
        });
        lstBooks = (ListView) findViewById(R.id.lstBooks);
        loadData();
        lstBooks.setAdapter(new Adapter(this,books,lstImages));


        lstBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder myBuilder =  new AlertDialog.Builder(BookListActivity.this);
                final View myView = getLayoutInflater().inflate(R.layout.dialog_update, null);
                iBook = (EditText) myView.findViewById(R.id.iBook);
                iAuthor = (EditText) myView.findViewById(R.id.iAuthor);
                iISBN = (EditText) myView.findViewById(R.id.iISBN);
                iLanguage = (EditText) myView.findViewById(R.id.iLanguage);
                iPublisher = (EditText) myView.findViewById(R.id.iPublisher);
                ivUpCr = (ImageView) myView.findViewById(R.id.ivUpCr);
                btnUpCr = (Button) myView.findViewById(R.id.btnUpCr);
                btnDel = (Button) myView.findViewById(R.id.btnDel);

                txtTitle = (TextView) myView.findViewById(R.id.txtTitle);

                operacion = "UPDATE";
                operacionButtonDel = "DELETE";
                btnUpCr.setText(operacion);
                txtTitle.setText(operacion);
                btnDel.setText(operacionButtonDel);

                final Book selected = books.get(position);
                setContent(selected, position);

                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            bookDao = getHelper().getBookDAO();

                            lstBooks.setAdapter(null);
                            lstBooks = (ListView) findViewById(R.id.lstBooks);
                            loadData();
                            lstBooks.setAdapter(new Adapter(BookListActivity.this,books,lstImages));
                            AlertDialog.Builder builder = new AlertDialog.Builder(myView.getContext());
                            builder.setMessage("Are you sure?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // Aquí lo que deseas realizar
                                            try {
                                                bookDao.delete(selected);
                                                Toast.makeText(BookListActivity.this, "Book deleted", Toast.LENGTH_SHORT);
                                            }catch (Exception e){
                                                Toast.makeText(BookListActivity.this, "Error deleting book", Toast.LENGTH_SHORT);
                                            }
                                            dialog.cancel();
                                            // cerrar la ventana
                                            //finish();
                                            Intent newWindow = new Intent(BookListActivity.this, BookListActivity.class);
                                            startActivity(newWindow);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

                btnUpCr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (iBook.getText().toString().isEmpty() ||
                                iAuthor.getText().toString().isEmpty() ||
                                iISBN.getText().toString().isEmpty() ||
                                iLanguage.getText().toString().isEmpty() ||
                                iPublisher.getText().toString().isEmpty()){
                            Toast.makeText(BookListActivity.this, "One row is empty", Toast.LENGTH_SHORT).show();
                        }else {
                            try {
                                bookDao = getHelper().getBookDAO();
                                //bookDao.create(new Book("Cien años de soledad","Gabo","1233","Spanish","Debolsillo","image/book1.jpg"));
                                // bookDao.create(new Book("The Lord Of The Rings","J.R Tolkien","264654","English","booket","image/book1.jpg"));
                                String b = iBook.getText().toString();
                                String a = iAuthor.getText().toString();
                                String i = iISBN.getText().toString();
                                String l = iLanguage.getText().toString();
                                String p = iPublisher.getText().toString();
                                //bookDao.create(new Book(b,a,i,l,p,R.drawable.noimage+"",getImage(ivUpCr)));
                                //Book created = new Book(b,a,i,l,p,R.drawable.noimage+"",null);
                                //bookDao.update(new Book(b,a,i,l,p,R.drawable.noimage+"",null));
                                selected.setName(b);
                                selected.setAuthor(a);
                                selected.setISBN(i);
                                selected.setLanguage(l);
                                selected.setPublisher(p);
                                selected.setImg(getImage(ivUpCr));
                                selected.setImgPath("");
                                bookDao.update(selected);
                                /*
                                UpdateBuilder<Book, Integer> ub = bookDao.updateBuilder();
                                ub.where().eq("id",Integer.valueOf(created.getId()));
                                ub.updateColumnValue("name",created.getName());
                                ub.update();
                                */
                                lstBooks.setAdapter(null);
                                lstBooks = (ListView) findViewById(R.id.lstBooks);
                                loadData();
                                lstBooks.setAdapter(new Adapter(BookListActivity.this,books,lstImages));
                                // cerrar la ventana
                                //finish();
                                Intent newWindow = new Intent(BookListActivity.this, BookListActivity.class);
                                startActivity(newWindow);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
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

    }


    private void loadImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "select a picture"), 10);
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
        try {
            bookDao = getHelper().getBookDAO();
            //bookDao.create(new Book("Cien años de soledad","Gabo","1233","Spanish","Debolsillo","image/book1.jpg"));
            // bookDao.create(new Book("The Lord Of The Rings","J.R Tolkien","264654","English","booket","image/book1.jpg"));
            List lstInventary = bookDao.queryForAll();
            books = listToArrayList(lstInventary);
        }catch (Exception e){
            System.err.println("Error al cargar los datos de la lista");
            e.printStackTrace();
        }

        //books.add(new Book("Cien años de soledad","Gabo","1233","Spanish","Debolsillo","image/book1.jpg"));
        //books.add(new Book("Harry Potter","J.R","6563","English","f","image/book1.jpg"));
        //books.add(new Book("Harry Potter","J.R","6563","English","f","image/book1.jpg"));
        lstImages.add(R.drawable.cienanios);
        lstImages.add(R.drawable.booka);
        lstImages.add(R.drawable.lordoftherings);
        lstImages.add(R.drawable.redclocks);
    }

    private void setContent(Book selected, int position){
        iBook.setText(selected.getName());
        iAuthor.setText(selected.getAuthor());
        iISBN.setText(selected.getISBN());
        iLanguage.setText(selected.getLanguage());
        iPublisher.setText(selected.getPublisher());
        try {
            byte[] byteArray = selected.getImg();
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ivUpCr.setImageBitmap(bmp);
            //image.setImageResource((Integer) images.get(position));
            //image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));
        } catch (Exception e) {
            ivUpCr.setImageResource(R.drawable.noimage);
        }
        //ivUpCr.setImageResource(getImage(ivUpCr));
    }

    /*
    Not used already
     */
    private void clearContent(Book selected, int position){
        iBook.setText("");
        iAuthor.setText("");
        iISBN.setText("");
        iLanguage.setText("");
        iPublisher.setText("");
        ivUpCr.setImageResource(R.drawable.noimage);
    }

    private void clearContent(){
        iBook.setText("");
        iAuthor.setText("");
        iISBN.setText("");
        iLanguage.setText("");
        iPublisher.setText("");
        ivUpCr.setImageResource(R.drawable.noimage);
    }

    // This is how, DatabaseHelper can be initialized for future use
    private DataBaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DataBaseHelper.class);
        }
        return databaseHelper;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        /*
         * You'll need this in your class to release the helper when done.
         */
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    private ArrayList<Object> listToArrayList(List<Object> objectList){
        ArrayList<Object> objectArrayList = new ArrayList<Object>();
        for (Object o: objectList){
            objectArrayList.add(o);
        }
        return objectArrayList;
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

    private String imageToString(Bitmap b){
        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, outputstream);
        return Base64.encodeToString(outputstream.toByteArray(), Base64.DEFAULT);
    }

}
