package com.example.ricknmortyandroid.locations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ricknmortyandroid.API.Repository;
import com.example.ricknmortyandroid.characters.Character;
import com.example.ricknmortyandroid.locations.Location;

import java.util.List;

public class LocationDetailViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<Location> locationLiveData;
    private MutableLiveData<List<Character>> charactersLiveData;
    public LocationDetailViewModel() {
        repository = Repository.getInstance();
        locationLiveData = new MutableLiveData<>();
        charactersLiveData = new MutableLiveData<>();
    }

    public void loadLocationDetail(String locationUrl) {
        repository.getLocationById(locationUrl, new Repository.LocationCallBack() {
            @Override
            public void onLocationLoaded(Location location) {
                locationLiveData.setValue(location);
                // Fetch episodes associated with the character
                List<String> characterUrls = location.getResidents();
                if (characterUrls != null && !characterUrls.isEmpty()) {
                    repository.getCharacters(characterUrls, new Repository.CharactersCallback() {
                        @Override
                        public void onCharactersLoaded(List<Character> characters) {
                            charactersLiveData.setValue(characters);
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            // Handle failure
                        }
                    });
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle failure
            }
        });
    }
    public Character getCharacterAtIndex(int index) {
        List<Character> characters = charactersLiveData.getValue();
        if (characters != null && index >= 0 && index < characters.size()) {
            return characters.get(index);
        }
        return null;
    }
    public LiveData<Location> getLocationData() {
        return locationLiveData;
    }

    public LiveData<List<Character>> getCharactersData() {
        return charactersLiveData;
    }
}
