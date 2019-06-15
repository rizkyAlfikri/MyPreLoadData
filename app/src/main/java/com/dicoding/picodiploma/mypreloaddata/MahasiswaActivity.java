package com.dicoding.picodiploma.mypreloaddata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dicoding.picodiploma.mypreloaddata.adapter.MahasiswaAdapter;
import com.dicoding.picodiploma.mypreloaddata.database.MahasiswaHelper;

import java.util.ArrayList;

public class MahasiswaActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MahasiswaAdapter mahasiswaAdapter;
    MahasiswaHelper mahasiswaHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        mahasiswaHelper = new MahasiswaHelper(this);
        mahasiswaAdapter = new MahasiswaAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mahasiswaAdapter);

        // membuka koneksi ke database
        mahasiswaHelper.open();

        // mengambil data
        ArrayList<MahasiswaModel> mahasiswaModels = mahasiswaHelper.getAllData();

        // menutup koneksi
        mahasiswaHelper.close();

        // mengirimkan data ke adapter
        mahasiswaAdapter.setData(mahasiswaModels);
    }
}
