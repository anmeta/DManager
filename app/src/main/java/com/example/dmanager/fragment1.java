package com.example.dmanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment1 extends Fragment {
    TextView home, rests;
    RecyclerView popularRecycler;
    RecyclerView visitedRecycler;
    PopularRestAdapter popularRestAdapter;
    RecentlyVisitedAdapter recentlyVisitedAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment1 newInstance(String param1, String param2) {
        fragment1 fragment = new fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment1, container, false);
        home= (TextView) view.findViewById(R.id.HomePage);
        rests= (TextView) view.findViewById(R.id.Restaurants);

        List<PopularRest> popularRestList = new ArrayList<>();

        setPopularRecycler(popularRestList);


        popularRestList.add(new PopularRest("Restaurant 1", "Tsimiski 25", R.drawable.rest1));
        popularRestList.add(new PopularRest("Restaurant 2", "Venizelou 20", R.drawable.rest2));
        popularRestList.add(new PopularRest("Restaurant 3", "Agia Sofias 9", R.drawable.rest3));
        setPopularRecycler(popularRestList);

        List<RecentlyVisited> recentlyVisitedList = new ArrayList<>();
        recentlyVisitedList.add(new RecentlyVisited("Food 1", "Tsimiski 25", R.drawable.food));
        recentlyVisitedList.add(new RecentlyVisited("Food 2", "Tsimiski 26", R.drawable.food1));
        setRecentlyRecycler(recentlyVisitedList);

        return view;
    }

    private  void setPopularRecycler(List<PopularRest> popularRestList){
        popularRecycler = popularRecycler.findViewById(R.id.popular_viewer);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        popularRestAdapter = new PopularRestAdapter(this.getContext(), popularRestList);
        popularRecycler.setAdapter(popularRestAdapter);
    }


    private  void setRecentlyRecycler(List<RecentlyVisited> recentlyVisitedList){
        visitedRecycler= visitedRecycler.findViewById(R.id.popular_viewer);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);
        visitedRecycler.setLayoutManager(layoutManager);
        recentlyVisitedAdapter = new RecentlyVisitedAdapter(this.getContext(), recentlyVisitedList);
        visitedRecycler.setAdapter(recentlyVisitedAdapter);
    }
    public void change(String text, String text1){
       home.setText(text);
       rests.setText(text1);
    }
}