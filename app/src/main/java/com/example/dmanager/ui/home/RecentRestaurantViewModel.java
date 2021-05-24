package com.example.dmanager.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecentRestaurantViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RecentRestaurantViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Here we will list of restaurants");
    }

    public LiveData<String> getText() {
        return mText;
    }
}