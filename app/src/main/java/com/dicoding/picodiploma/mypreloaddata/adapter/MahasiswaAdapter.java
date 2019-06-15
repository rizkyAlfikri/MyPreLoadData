package com.dicoding.picodiploma.mypreloaddata.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dicoding.picodiploma.mypreloaddata.MahasiswaModel;
import com.dicoding.picodiploma.mypreloaddata.R;

import java.util.ArrayList;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaAdapterViewHolder> {
    private ArrayList<MahasiswaModel> listMahasiswa = new ArrayList<>();

    public MahasiswaAdapter() {
    }

    public void setData(ArrayList<MahasiswaModel> listMahasiswa) {
        if (listMahasiswa.size() > 0) {
            this.listMahasiswa.clear();
        }
        this.listMahasiswa.addAll(listMahasiswa);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MahasiswaAdapter.MahasiswaAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mahasiswa_row, viewGroup, false);
        return new MahasiswaAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaAdapterViewHolder viewHolder, int position) {
        viewHolder.txtViewName.setText(listMahasiswa.get(position).getName());
        viewHolder.txtViewNim.setText(listMahasiswa.get(position).getNim());

    }

    @Override
    public int getItemCount() {
        return listMahasiswa.size();
    }

    class MahasiswaAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView txtViewNim, txtViewName;

        MahasiswaAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewName = itemView.findViewById(R.id.txt_nama);
            txtViewNim = itemView.findViewById(R.id.txt_nim);
        }
    }
}
