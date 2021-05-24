package com.example.dmanager.ui.restaurants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dmanager.R;
import com.example.dmanager.helpers.Context;
import com.example.dmanager.ui.general.RecyclerAdapter;

public class RestaurantsFragment extends Fragment {

    private RestaurantsViewModel slideshowViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(RestaurantsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_restarurants, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.restaurantsRecyclerViewer);
        mRecyclerAdapter = new RecyclerAdapter(Context.getInstance().restaurantsForDisplay);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        return root;
    }
}