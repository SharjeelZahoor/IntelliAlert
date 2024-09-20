package com.sharjeel.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.sharjeel.db.Db;
import com.sharjeel.entity.User;


public class UserDao {
    //+++++++++++++++++++++++++++ table name +++++++++++++++
    private static final String TABLE = "users";
    //+++++++++++++++++++++++++++ cols name +++++++++++++++
    private static final String COL_ID = "id";
    private static final String COL_CNIC = "cnic";
    private static final String COL_PASSWORD = "pw";
    private static final String COL_NAME = "u_name";
    private static final String COL_AGE = "age";
    private static final String COL_CONTACT = "contact";
    private static final String COL_DEPT = "dept";
    private static final String COL_DESIG = "desig";
    private static final String COL_ADDRESS = "address";
   // private static final String COL_CITY = "city";


    public static String getCreateStatement() {
        return "CREATE TABlE " + TABLE + " ( " +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_CNIC + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_NAME + " TEXT, " +
                COL_AGE + " INTEGER, " +
                COL_CONTACT + " TEXT, " +
                COL_DEPT + " TEXT, " +
                COL_DESIG + " TEXT, " +
                //COL_CITY + " TEXT, " +
                COL_ADDRESS + " TEXT " +
                ");";
    }

    public static String getDropStatement() {
        return "DROP TABLE IF EXISTS " + TABLE;
    }
    // ++++++++++++++++++++++ Data members ++++++++++++++++++++++

    Db db;

    public UserDao(Context context) {
        this.db = new Db(context);
    }

    public int register(User record) {
        ContentValues cv = new ContentValues();
        cv.put(COL_CNIC, record.getCnic());
        cv.put(COL_PASSWORD, record.getPw());
        cv.put(COL_NAME, record.getName());
        cv.put(COL_AGE, record.getAge());
        cv.put(COL_CONTACT, record.getContact());
        cv.put(COL_DEPT, record.getDepartment());
        cv.put(COL_DESIG, record.getDesignation());
        cv.put(COL_ADDRESS, record.getAddress());
        //cv.put(COL_CITY, record.getCity());
        return this.db.add(TABLE, cv);
    }

    //
    @SuppressLint("Range")
    public User get(String cnic) {
        User record = null;
        Cursor c = this.db.get(TABLE, null, COL_CNIC + "=?", cnic);
//        if (c != null) {
//            if (c.moveToFirst()) {
//                record = new User(
//                        c.getInt(c.getColumnIndex(COL_ID)),
//                        c.getString(c.getColumnIndex(COL_CNIC)),
//                        c.getString(c.getColumnIndex(COL_PASSWORD)),
//                        c.getString(c.getColumnIndex(COL_NAME)),
//                        c.getInt(c.getColumnIndex(COL_AGE)),
//                        c.getString(c.getColumnIndex(COL_CONTACT)),
//                        c.getString(c.getColumnIndex(COL_DEPT)),
//                        c.getString(c.getColumnIndex(COL_DESIG)),
//                        c.getString(c.getColumnIndex(COL_ADDRESS))
//                        );
//                //record.setCity(c.getString(c.getColumnIndex(COL_CITY)));
//            }
//        }
        this.db.close();
        return record;
    }

    //
    @SuppressLint("Range")
    public User login(String cnic, String pw) {
        User record = null;
        Cursor c = this.db.getWhere(TABLE, null, COL_CNIC + "=? AND " + COL_PASSWORD + "=?", new String[]{cnic, pw}, null);
//        if (c != null) {
//            if (c.moveToFirst()) {
//                record = new User(
//                        c.getInt(c.getColumnIndex(COL_ID)),
//                        c.getString(c.getColumnIndex(COL_CNIC)),
//                        c.getString(c.getColumnIndex(COL_PASSWORD)),
//                        c.getString(c.getColumnIndex(COL_NAME)),
//                        c.getInt(c.getColumnIndex(COL_AGE)),
//                        c.getString(c.getColumnIndex(COL_CONTACT)),
//                        c.getString(c.getColumnIndex(COL_DEPT)),
//                        c.getString(c.getColumnIndex(COL_DESIG)),
//                        c.getString(c.getColumnIndex(COL_ADDRESS)));
//            }
//        }
        this.db.close();
        return record;
    }

}