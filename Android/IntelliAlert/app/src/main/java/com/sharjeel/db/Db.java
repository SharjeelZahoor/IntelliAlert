package com.sharjeel.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sharjeel.dao.UserDao;


public class Db {
    private static final String DB="ims";
    private static final int DB_VERSION=1;
    // inner database helper class
    private class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DB, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(UserDao.getCreateStatement());
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(UserDao.getDropStatement());
            this.onCreate(db);
        }
    }

    // class members and functions
    private DbHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    public Db(Context c) {
        this.context=c;
    }
    private Db open()
    {
        this.dbHelper=new DbHelper(this.context);
        this.database=dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        this.dbHelper.close();
    }

    public int add(String table, ContentValues cv)
    {
        this.open();
        int rowId=(int) this.database.insert(table, null, cv);
        this.close();
        return rowId;
    }


    public Cursor get(String table, String[] columns, String where, String val)
    {
        this.open();
        Cursor c=this.database.query(table, columns, where, new String[]{val}, null, null, null, null);
        //this.close();
        return c;
    }

    public Cursor getWhere(String table,String[] columns,String where, String[] val,String orderBy)
    {
        this.open();
        Cursor c=this.database.query(table, columns, where, val, null, null, orderBy, null);
        //this.close();
        return c;
    }


}