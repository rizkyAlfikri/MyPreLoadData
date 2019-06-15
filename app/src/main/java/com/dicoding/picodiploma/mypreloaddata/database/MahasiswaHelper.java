package com.dicoding.picodiploma.mypreloaddata.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.dicoding.picodiploma.mypreloaddata.MahasiswaModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.picodiploma.mypreloaddata.database.DatabaseContract.MahasiswaColumns.NAMA;
import static com.dicoding.picodiploma.mypreloaddata.database.DatabaseContract.MahasiswaColumns.NIM;
import static com.dicoding.picodiploma.mypreloaddata.database.DatabaseContract.TABLE_NAME;

public class MahasiswaHelper {
    // melakukan konstructor dan instance dari DatabseHelper
    private DatabaseHelper databaseHelper;
    private static MahasiswaHelper INSTANCE;

    private SQLiteDatabase database;

    public MahasiswaHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MahasiswaHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MahasiswaHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    // koneksi ke database
    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    // method untuk membaca dan menulis database
    public ArrayList<MahasiswaModel> getAllData() {
        Cursor cursor = database.query(TABLE_NAME
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC"
                , null);

        cursor.moveToFirst();
        ArrayList<MahasiswaModel> arrayList = new ArrayList<>();
        MahasiswaModel mahasiswaModel;
        if (cursor.getCount() > 0) {
            do {
                // membaca data pada table
                mahasiswaModel = new MahasiswaModel();
                mahasiswaModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                mahasiswaModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAMA)));
                mahasiswaModel.setNim(cursor.getString(cursor.getColumnIndexOrThrow(NIM)));

                arrayList.add(mahasiswaModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    // memasukan data pada table

    public long insert(MahasiswaModel mahasiswaModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAMA, mahasiswaModel.getName());
        initialValues.put(NIM, mahasiswaModel.getNim());
        return database.insert(TABLE_NAME, null, initialValues);
    }

    // memasukan data secara bulk / dalam jumlah besar secara bersama sama
    public void beignTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(MahasiswaModel mahasiswaModel) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + NAMA + ", " + NIM + ") VALUES (?, ?";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, mahasiswaModel.getName());
        stmt.bindString(2, mahasiswaModel.getNim());
        stmt.execute();
        stmt.clearBindings();
    }

    // melakukan pencarian data pada table berdasarkan nama
    public ArrayList<MahasiswaModel> getDataByName(String nama) {
        Cursor cursor = database.query(TABLE_NAME
                , null
                , NAMA + " LIKE ?"
                , new String[]{nama}
                , null
                , null
                , _ID + " ASC"
                , null);
        cursor.moveToFirst();
        ArrayList<MahasiswaModel> arrayList = new ArrayList<>();
        MahasiswaModel mahasiswaModel;
        if (cursor.getCount() > 0) {
            do {
                mahasiswaModel = new MahasiswaModel();
                mahasiswaModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                mahasiswaModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAMA)));
                mahasiswaModel.setNim(cursor.getString(cursor.getColumnIndexOrThrow(NIM)));

                arrayList.add(mahasiswaModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }
}
