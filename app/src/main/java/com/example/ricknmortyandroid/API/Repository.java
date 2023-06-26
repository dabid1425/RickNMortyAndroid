package com.example.ricknmortyandroid.API;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ricknmortyandroid.characters.Character;
import com.example.ricknmortyandroid.characters.CharacterResponse;
import com.example.ricknmortyandroid.episodes.Episode;
import com.example.ricknmortyandroid.episodes.EpisodeResponse;
import com.example.ricknmortyandroid.locations.Location;
import com.example.ricknmortyandroid.locations.LocationResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    private RickAndMortyApiService apiService;
    private MutableLiveData<List<Character>> charactersLiveData;
    private MutableLiveData<List<Location>> locationsLiveData;
    private MutableLiveData<List<Episode>> episodesLiveData;
    private int currentPage = 1;
    private boolean isFetchingData = false;

    private boolean isDonePagingData = false;

    public Repository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(RickAndMortyApiService.class);
        charactersLiveData = new MutableLiveData<>();
        locationsLiveData = new MutableLiveData<>();
        episodesLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Character>> getCharacters() {
        loadCharacters();
        return charactersLiveData;
    }
    public LiveData<List<Location>> getLocations() {
        loadLocations();
        return locationsLiveData;
    }
    public LiveData<List<Episode>> getEpisodes() {
        loadEpisodes();
        return episodesLiveData;
    }

    public void loadCharacters() {
        if (isFetchingData || isDonePagingData) {
            return;
        }

        isFetchingData = true;
        apiService.getCharacters(currentPage).enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                if (response.isSuccessful()) {
                    CharacterResponse characterResponse = response.body();
                    if (characterResponse != null) {
                        List<Character> characters = characterResponse.getResults();
                        if (characters != null) {
                            List<Character> currentCharacters = charactersLiveData.getValue();
                            if (currentCharacters == null) {
                                currentCharacters = new ArrayList<>();
                            }
                            currentCharacters.addAll(characters);
                            charactersLiveData.setValue(currentCharacters);
                        }
                    }
                    updatePagination(characterResponse != null ? characterResponse.getInfo() : null);
                } else {
                    // Handle error
                }
                isFetchingData = false;
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                // Handle failure
                isFetchingData = false;
            }
        });
    }

    public void loadLocations() {
        if (isFetchingData || isDonePagingData) {
            return;
        }

        isFetchingData = true;
        apiService.getLocations(currentPage).enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.isSuccessful()) {
                    LocationResponse locationResponse = response.body();
                    if (locationResponse != null) {
                        List<Location> locations = locationResponse.getResults();
                        if (locations != null) {
                            List<Location> currentLocations = locationsLiveData.getValue();
                            if (currentLocations == null) {
                                currentLocations = new ArrayList<>();
                            }
                            currentLocations.addAll(locations);
                            locationsLiveData.setValue(currentLocations);
                        }
                    }
                    updatePagination(locationResponse != null ? locationResponse.getInfo() : null);
                } else {
                    // Handle error
                }
                isFetchingData = false;
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                // Handle failure
                isFetchingData = false;
            }
        });
    }


    public void loadEpisodes() {
        if (isFetchingData || isDonePagingData) {
            return;
        }

        isFetchingData = true;
        apiService.getEpisodes(currentPage).enqueue(new Callback<EpisodeResponse>() {
            @Override
            public void onResponse(Call<EpisodeResponse> call, Response<EpisodeResponse> response) {
                if (response.isSuccessful()) {
                    EpisodeResponse episodeResponse = response.body();
                    if (episodeResponse != null) {
                        List<Episode> episodes = episodeResponse.getResults();
                        if (episodes != null) {
                            List<Episode> currentEpisodes = episodesLiveData.getValue();
                            if (currentEpisodes == null) {
                                currentEpisodes = new ArrayList<>();
                            }
                            currentEpisodes.addAll(episodes);
                            episodesLiveData.setValue(currentEpisodes);
                        }
                    }
                    updatePagination(episodeResponse != null ? episodeResponse.getInfo() : null);
                } else {
                    // Handle error
                }
                isFetchingData = false;
            }

            @Override
            public void onFailure(Call<EpisodeResponse> call, Throwable t) {
                // Handle failure
                isFetchingData = false;
            }
        });
    }
    private void updatePagination(Info info) {
        if (info != null) {
            String nextPageUrl = info.getNext();
            if (nextPageUrl != null && !nextPageUrl.isEmpty()) {
                currentPage++;
                isDonePagingData = false;
            } else {
                isDonePagingData = true;
            }
        }
    }
}
