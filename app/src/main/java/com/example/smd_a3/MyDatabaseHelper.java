package com.example.smd_a3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabaseHelper {

    private final String DATABASE_NAME = "PASSWORD_MANAGER";
    private final int DATABASE_VERSION = 1;

    private final String TABLE_NAME1 = "SignUp_table";
    private final String KEY_ID1 = "_id1";
    private final String KEY_NAME1 = "_name1";
    private final String KEY_PASSWORD1 = "_password1";
    private final String KEY_EMAIL1 = "_email1";
    private final String KEY_PHONE1 = "_phone1";


    private final String TABLE_NAME2="Password_Manager";
    private final String KEY_ID2="_id2";
    private final String KEY_NAME2="_name2";
    private final String KEY_PASSWORD2="_password2";
    private final String KEY_URL2="_url2";

    private final String KEY_DELETED2="_delete";
    private final String KEY_USER_ID="_userid";

    CreateDataBase helper;
    SQLiteDatabase database;
    Context context;

    public MyDatabaseHelper(Context context)
    {
        this.context = context;

    }


    public void open()
    {
        helper = new CreateDataBase(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = helper.getWritableDatabase();
    }

    public void close()
    {
        database.close();
        helper.close();
    }

    public void insertRegisterationData(String name, String password,String email,String phone)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME1,name);
        cv.put(KEY_PASSWORD1,password);
        cv.put(KEY_EMAIL1,email);
        cv.put(KEY_PHONE1,phone);

        try {
            long records = database.insert(TABLE_NAME1, null, cv);
            if (records != SQLiteDatabase.CONFLICT_NONE) {
                Toast.makeText(context, "Data inserted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Data not inserted", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("InsertError", "Error inserting data: " + e.getMessage());
            Toast.makeText(context, "Error inserting data", Toast.LENGTH_SHORT).show();
        }
    }
    public void insertPasswordData(String name, String password, String link,int userId)
    {


        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME2, name);
        cv.put(KEY_PASSWORD2, password);
        cv.put(KEY_URL2, link);
        cv.put(KEY_DELETED2, false);
        cv.put(KEY_USER_ID, userId);

        try {
            long records = database.insert(TABLE_NAME2, null, cv);
            if (records != SQLiteDatabase.CONFLICT_NONE) {
                Toast.makeText(context, "Data inserted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Data not inserted", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("InsertError", "Error inserting data: " + e.getMessage());
            Toast.makeText(context, "Error inserting data", Toast.LENGTH_SHORT).show();
        }
    }




    public ArrayList<passwordModel> readAllPasswords(int userID)
    {

        ArrayList<passwordModel> records = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + KEY_USER_ID + " = ? AND "+ KEY_DELETED2+" = false";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userID)});


        int id_Index = cursor.getColumnIndex(KEY_ID2);
        int name_Index = cursor.getColumnIndex(KEY_NAME2);
        int pass_Index = cursor.getColumnIndex(KEY_PASSWORD2);
        int link_Index = cursor.getColumnIndex(KEY_URL2);
//      int userID_index= cursor.getColumnIndex(KEY_USER_ID);


        if(cursor.moveToFirst())
        {
            do{
                passwordModel c = new passwordModel();

                c.setId(cursor.getInt(id_Index));
                c.setUsername(cursor.getString(name_Index));
                c.setPassword(cursor.getString(pass_Index));
                c.setUrl(cursor.getString(link_Index));

                records.add(c);
            }while(cursor.moveToNext());
        }

        cursor.close();

        return records;
    }

    public ArrayList<passwordModel> readAllPasswordsDeleted(int userID)
    {

        ArrayList<passwordModel> records = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + KEY_USER_ID + " = ? AND "+ KEY_DELETED2+" = true";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userID)});


        int id_Index = cursor.getColumnIndex(KEY_ID2);
        int name_Index = cursor.getColumnIndex(KEY_NAME2);
        int pass_Index = cursor.getColumnIndex(KEY_PASSWORD2);
        int link_Index = cursor.getColumnIndex(KEY_URL2);
//      int userID_index= cursor.getColumnIndex(KEY_USER_ID);


        Log.d("MyAdapter", "Item removed from passwords array"+name_Index);

        if(cursor.moveToFirst())
        {
            do{
                passwordModel c = new passwordModel();

                c.setId(cursor.getInt(id_Index));
                c.setUsername(cursor.getString(name_Index));
                c.setPassword(cursor.getString(pass_Index));
                c.setUrl(cursor.getString(link_Index));

                records.add(c);
            }while(cursor.moveToNext());
        }

        cursor.close();

        return records;
    }
    public void deletePasswordDataPermanently(int id)
    {
        int rows = database.delete(TABLE_NAME2, KEY_ID2+"=?", new String[]{id+""});
        if(rows>0)
        {
            Toast.makeText(context, "Data deleted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Data not deleted", Toast.LENGTH_SHORT).show();
        }
    }
    public ArrayList<passwordModel> getAllDeletedData() {
        ArrayList<passwordModel> deletedData = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE " + KEY_DELETED2 + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{"true"});

        int idIndex = cursor.getColumnIndex(KEY_ID2);
        int nameIndex = cursor.getColumnIndex(KEY_NAME2);
        int passIndex = cursor.getColumnIndex(KEY_PASSWORD2);
        int urlIndex = cursor.getColumnIndex(KEY_URL2);

        if (cursor.moveToFirst()) {
            do {
                passwordModel data = new passwordModel();
                data.setId(cursor.getInt(idIndex));
                data.setUsername(cursor.getString(nameIndex));
                data.setPassword(cursor.getString(passIndex));
                data.setUrl(cursor.getString(urlIndex));

                deletedData.add(data);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return deletedData;
    }


//    public void deletePasswordData(int id) {
//        ContentValues cv = new ContentValues();
//        cv.put(KEY_DELETED2, "true");
//
//        int rows = database.update(TABLE_NAME2, cv, KEY_ID2 + "=?", new String[]{String.valueOf(id)});
//        if (rows > 0) {
//            Toast.makeText(context, "Data deleted", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Data not deleted", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void deletePasswordData(int id) {
        // Check if the data is already marked as deleted
        String query = "SELECT " + KEY_DELETED2 + " FROM " + TABLE_NAME2 + " WHERE " + KEY_ID2 + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});
        boolean isDeleted = false;

        if (cursor.moveToFirst()) {
            int deletedIndex = cursor.getColumnIndex(KEY_DELETED2);
            isDeleted = cursor.getInt(deletedIndex) != 0;
        }
        cursor.close();

        // If the data is not marked as deleted, update it
        if (!isDeleted) {
            ContentValues cv = new ContentValues();
            cv.put(KEY_DELETED2, "true");

            int rows = database.update(TABLE_NAME2, cv, KEY_ID2 + "=?", new String[]{String.valueOf(id)});
            if (rows > 0) {
                Toast.makeText(context, "Data deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Data not deleted", Toast.LENGTH_SHORT).show();
            }
        } else {
            // If the data is already marked as deleted, delete it permanently
            deletePasswordDataPermanently(id);
        }
    }

    public void updatePassword(int id, String name, String pass,String url)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME2, name);
        cv.put(KEY_PASSWORD2, pass);
        cv.put(KEY_URL2,url);

        int records = database.update(TABLE_NAME2, cv, KEY_ID2+"=?", new String[]{id+""});
        if(records>0)
        {
            Toast.makeText(context, "Contact updated", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Contact not updated", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkLogin(String username, String password) {

        String query = "SELECT * FROM " + TABLE_NAME1 + " WHERE " + KEY_NAME1 + " = ? AND " + KEY_PASSWORD1 + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{username, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    @SuppressLint("Range")
    public int getUserId(String username, String password) {
        int userId = -1; // Default value if user is not found
        String query = "SELECT " + KEY_ID1 + " FROM " + TABLE_NAME1 + " WHERE " + KEY_NAME1 + " = ? AND " + KEY_PASSWORD1 + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{username, password});
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(KEY_ID1));
        }
        cursor.close();
        return userId;
    }

    private class CreateDataBase extends SQLiteOpenHelper
    {
        public CreateDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query1 = "CREATE TABLE " + TABLE_NAME1 + "(" +
                    KEY_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_NAME1 + " TEXT NOT NULL," +
                    KEY_PASSWORD1 + " TEXT NOT NULL," +
                    KEY_EMAIL1 + " TEXT NOT NULL," +
                    KEY_PHONE1 + " TEXT NOT NULL" +
                    ");";

            String query2 = "CREATE TABLE " + TABLE_NAME2 + "(" +
                    KEY_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_NAME2 + " TEXT NOT NULL," +
                    KEY_PASSWORD2 + " TEXT NOT NULL," +
                    KEY_URL2 + " TEXT NOT NULL," +
                    KEY_DELETED2 + " TEXT NOT NULL," +
                    KEY_USER_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " + TABLE_NAME1 + "(" + KEY_ID1 + ")" +
                    ");";


            db.execSQL(query1);
            db.execSQL(query2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // backup code here
            db.execSQL("DROP TABLE "+TABLE_NAME1+" IF EXISTS");
            db.execSQL("DROP TABLE "+TABLE_NAME2+" IF EXISTS");

            onCreate(db);
        }
    }

}
