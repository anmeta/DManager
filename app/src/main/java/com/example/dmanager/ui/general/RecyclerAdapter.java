package com.example.dmanager.ui.general;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dmanager.R;
import com.example.dmanager.entities.Restaurant;
import com.example.dmanager.helpers.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerItemViewHolder> {
    int mLastPosition = 0;
    private ArrayList<Restaurant> restaurants;
    public RecyclerAdapter(ArrayList<Restaurant>restaurants) {
        this.restaurants = restaurants;
    }
    public RecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(RecyclerItemViewHolder holder, final int position) {
        holder.etTitleTextView.setText(restaurants.get(position).getRestaurantName());
        holder.etDescriptionTextView.setText(restaurants.get(position).getRestaurantCity());
        holder.crossImage.setImageResource(R.drawable.ic_menu_send);
        mLastPosition =position;
    }
    @Override
    public int getItemCount() {
        return(null != restaurants?restaurants.size():0);
    }
    public void notifyData(ArrayList<Restaurant> myList) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }
    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView etTitleTextView;
        private final TextView etDescriptionTextView;
        private LinearLayout mainLayout;
        public ImageView crossImage;
        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            etTitleTextView = (TextView) parent.findViewById(R.id.txtTitle);
            etDescriptionTextView = (TextView) parent.findViewById(R.id.txtDescription);
            crossImage = (ImageView) parent.findViewById(R.id.crossImage);
            mainLayout = (LinearLayout) parent.findViewById(R.id.mainLayout);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context.getInstance().addViewedRestaurant(Context.getInstance().restaurantsForDisplay.get(getPosition()));
                    Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                }
            });
            crossImage.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: Open restaurnat menu

                }
            });
        }
    }
}