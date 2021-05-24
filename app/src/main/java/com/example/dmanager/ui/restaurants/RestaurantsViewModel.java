package com.example.dmanager.ui.restaurants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RestaurantsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RestaurantsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Here we will list of restaurants");
    }

    public LiveData<String> getText() {
        return mText;
    }
}