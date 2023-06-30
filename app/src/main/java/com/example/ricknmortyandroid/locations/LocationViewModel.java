package com.example.ricknmortyandroid.locations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ricknmortyandroid.API.Repository;
import com.example.ricknmortyandroid.episodes.Episode;

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
    public Location getLocation(int index) {
        List<Location> locations = locationsLiveData.getValue();
        if (locations != null && index >= 0 && index < locations.size()) {
            return locations.get(index);
        }
        return null;
    }
}