package com.example.ricknmortyandroid.locations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ricknmortyandroid.API.Repository;

import java.util.List;

public class LocationViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<Location>> locationsLiveData;

    public LocationViewModel() {
        repository = new Repository();
        locationsLiveData = repository.getLocations();
    }

    public LiveData<List<Location>> getLocations() {
        return locationsLiveData;
    }

    public void loadLocations() {
        repository.loadLocations();
    }

}