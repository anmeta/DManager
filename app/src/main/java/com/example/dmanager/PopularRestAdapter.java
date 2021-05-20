package com.example.dmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PopularRestAdapter extends RecyclerView.Adapter<PopularRestAdapter.PopularRestHolder> {
    Context context;
    List<PopularRest> PopularRests;

    public PopularRestAdapter(Context context, List<PopularRest> PopularRests) {
        this.context = context;
        PopularRests = PopularRests;
    }

    @NonNull
    @Override
    public PopularRestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_rests, parent, false);
        return new PopularRestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularRestHolder holder, int position) {



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return PopularRests.size();
    }

    public static final class PopularRestHolder extends RecyclerView.ViewHolder{


      public PopularRestHolder(@NonNull View itemView) {
          super(itemView);

      }
  }
}
