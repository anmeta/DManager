package com.example.dmanager.ui.general;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dmanager.BaseActivity;
import com.example.dmanager.R;
import com.example.dmanager.RestaurantMenuActivity;
import com.example.dmanager.UserActivity;
import com.example.dmanager.entities.Restaurant;
import com.example.dmanager.entities.UserRestaurant;
import com.example.dmanager.helpers.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;

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
        holder.etTitleTextView.setText(restaurants.get(position).RestaurantName);
        holder.etDescriptionTextView.setText(restaurants.get(position).RestaurantCity);
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
                    Restaurant viewedRestaurant = Context.getInstance().restaurantsForDisplay.get(getPosition());
                    Context.getInstance().addViewedRestaurant(viewedRestaurant);

                    BaseActivity base = new BaseActivity();
                    base.addUserFavoriteRestaurant(
                            new UserRestaurant(Context.getInstance().activeUser.Email, viewedRestaurant.Email, true));

                    Intent restaurantMenu = new Intent(parent.getContext(), RestaurantMenuActivity.class);
                    parent.getContext().startActivity(restaurantMenu);
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