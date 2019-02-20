package com.sarredondo.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sarredondo.finalapp.R;

import java.sql.SQLException;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;

    // DAO objects used for acced the library db
    private Dao<Book, Integer> bookDAO = null;
    private RuntimeExceptionDao<Book, Integer> runTimeBookDAO = null;

    public DataBaseHelper(Context context){
        //super(context, DATABASE_NAME, null, DATABASE_VERSION);
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.orm_config);
    }

    /**
     *
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(DataBaseHelper.class.getSimpleName(), "onCreate()");
            TableUtils.createTable(connectionSource, Book.class);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getSimpleName(),"Can't create a db connection", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DataBaseHelper.class.getSimpleName(), "onUpgrade()");
            TableUtils.dropTable(connectionSource, Book.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DataBaseHelper.class.getSimpleName(),"Can't delete the database",e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Book,Integer> getBookDAO() throws SQLException{
        if (bookDAO==null) bookDAO = getDao(Book.class);
        return bookDAO;
    }

    public RuntimeExceptionDao<Book, Integer> getBookRuntimeDAO() throws SQLException{
        if (runTimeBookDAO ==null) runTimeBookDAO = getRuntimeExceptionDao(Book.class);
        return runTimeBookDAO;
    }

    @Override
    public void close() {
        super.close();
        bookDAO = null;
        runTimeBookDAO = null;
    }
}
