package com.example.rifqi12rpl012018;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class rv_adapter extends RecyclerView.Adapter<rv_adapter.MyViewHolder> {

    private List<rv_model> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, email, id;

        public MyViewHolder(View view) {
            super(view);
            nama = (TextView) view.findViewById(R.id.nama);
            email = (TextView) view.findViewById(R.id.email);
            id = (TextView) view.findViewById(R.id.id);
        }
    }


    public rv_adapter(Context applicationContext, List<rv_model> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_admin, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        rv_model movie = moviesList.get(position);
        holder.nama.setText(movie.getNama());
        holder.email.setText(movie.getEmail());
        holder.id.setText(movie.getId());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}