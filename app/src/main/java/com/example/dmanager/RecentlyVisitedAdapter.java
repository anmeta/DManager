package com.example.dmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecentlyVisitedAdapter extends RecyclerView.Adapter<RecentlyVisitedAdapter.RecentlyHolder> {
    Context context;
    List<RecentlyVisited> RecentlyVisited;

    public RecentlyVisitedAdapter(Context context, List<RecentlyVisited> RecentlyVisited) {
        this.context = context;
        this.RecentlyVisited = RecentlyVisited;
    }

    @NonNull
    @Override
    public RecentlyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recenlty_visited, parent, false);
        return new RecentlyHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecentlyHolder holder, int position) {
        holder.foodImage.setImageResource(RecentlyVisited.get(position).getImageUrl());
        holder.orderName.setText(RecentlyVisited.get(position).getName());
        holder.restName.setText(RecentlyVisited.get(position).getName());
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
        return RecentlyVisited.size();
    }

    public static final class RecentlyHolder extends RecyclerView.ViewHolder{
        ImageView foodImage;
        TextView orderName;
        TextView restName;

        public RecentlyHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_img);
            orderName = itemView.findViewById(R.id.orderName);
            restName = itemView.findViewById(R.id.Restname);
        }
    }
}
