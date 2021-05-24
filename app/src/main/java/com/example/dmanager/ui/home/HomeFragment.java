package com.example.dmanager.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dmanager.R;
import com.example.dmanager.helpers.Context;
import com.example.dmanager.ui.general.RecyclerAdapter;
import com.example.dmanager.ui.restaurants.RestaurantsViewModel;

public class HomeFragment extends Fragment {

    private RecentRestaurantViewModel recentRestaurantViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        recentRestaurantViewModel =
                new ViewModelProvider(this).get(RecentRestaurantViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        recentRestaurantViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        mRecyclerView = (RecyclerView) root.findViewById(R.id.restaurantsRecyclerViewer);
        mRecyclerAdapter = new RecyclerAdapter( Context.getInstance().lastViewedRestaurants);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        return root;
    }
}