package com.dicoding.picodiploma.mypreloaddata.database;

import android.provider.BaseColumns;

// mendeklarasikan nama table dan column yang akan digunakan pada table
public class DatabaseContract {
    static String TABLE_NAME = "table_mahasiswa";

    static final class MahasiswaColumns implements BaseColumns {
        static String NAMA = "nama";
        static String NIM = "nim";
    }
}
