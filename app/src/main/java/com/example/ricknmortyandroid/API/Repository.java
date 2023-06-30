package com.example.ricknmortyandroid.API;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ricknmortyandroid.characters.Character;
import com.example.ricknmortyandroid.characters.CharactersListResponse;
import com.example.ricknmortyandroid.episodes.Episode;
import com.example.ricknmortyandroid.episodes.EpisodeResponse;
import com.example.ricknmortyandroid.locations.Location;
import com.example.ricknmortyandroid.locations.LocationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {
    public interface EpisodesCallback {
        void onEpisodesLoaded(List<Episode> episodes);
        void onFailure(String errorMessage);
    }
    public interface CharactersCallback {
        void onCharactersLoaded(List<Character> episodes);
        void onFailure(String errorMessage);
    }
    public interface LocationCallBack {
        void onLocationLoaded(Location location);
        void onFailure(String errorMessage);
    }
    public interface CharacterCallback {
        void onCharacterLoaded(Character character);
        void onFailure(String errorMessage);
    }
    public interface EpisodeCallback {
        void onEpisodeLoaded(Episode episode);
        void onFailure(String errorMessage);
    }
    private RickAndMortyApiService apiService;
    private MutableLiveData<List<Character>> charactersLiveData;
    private MutableLiveData<List<Location>> locationsLiveData;
    private MutableLiveData<List<Episode>> episodesLiveData;
    private int currentPage = 1;
    private boolean isFetchingData = false;
    private static Repository instance;
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
    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
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
    public void getCharacterById(String characterUrl, final CharacterCallback callback) {
        apiService.getCharacterByUrl(characterUrl).enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                if (response.isSuccessful()) {
                    Character character = response.body();
                    if (character != null) {
                        callback.onCharacterLoaded(character);
                    }
                } else {
                    // Handle error
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                // Handle failure
                callback.onFailure(t.getMessage());
            }
        });
    }
    public void getCharacters(List<String> characterUrls, final CharactersCallback callback) {
        final List<Character> characters = new ArrayList<>();
        final AtomicInteger count = new AtomicInteger(characterUrls.size());

        for (String url : characterUrls) {
            getCharacterById(url, new CharacterCallback() {
                @Override
                public void onCharacterLoaded(Character character) {
                    characters.add(character);
                    int remaining = count.decrementAndGet();
                    if (remaining == 0) {
                        callback.onCharactersLoaded(characters);
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    int remaining = count.decrementAndGet();
                    if (remaining == 0) {
                        callback.onFailure(errorMessage);
                    }
                }
            });
        }
    }


    public void loadCharacters() {
        if (isFetchingData || isDonePagingData) {
            return;
        }

        isFetchingData = true;
        apiService.getCharacters(currentPage).enqueue(new Callback<CharactersListResponse>() {
            @Override
            public void onResponse(Call<CharactersListResponse> call, Response<CharactersListResponse> response) {
                if (response.isSuccessful()) {
                    CharactersListResponse charactersListResponse = response.body();
                    if (charactersListResponse != null) {
                        List<Character> characters = charactersListResponse.getResults();
                        if (characters != null) {
                            List<Character> currentCharacters = charactersLiveData.getValue();
                            if (currentCharacters == null) {
                                currentCharacters = new ArrayList<>();
                            }
                            currentCharacters.addAll(characters);
                            charactersLiveData.setValue(currentCharacters);
                        }
                    }
                    updatePagination(charactersListResponse != null ? charactersListResponse.getInfo() : null);
                } else {
                    // Handle error
                }
                isFetchingData = false;
            }

            @Override
            public void onFailure(Call<CharactersListResponse> call, Throwable t) {
                // Handle failure
                isFetchingData = false;
            }
        });
    }

    public void getEpisodeById(String episodeUrl, final EpisodeCallback callback) {
        apiService.getEpisodeByUrl(episodeUrl).enqueue(new Callback<Episode>() {
            @Override
            public void onResponse(Call<Episode> call, Response<Episode> response) {
                if (response.isSuccessful()) {
                    Episode episode = response.body();
                    if (episode != null) {
                        callback.onEpisodeLoaded(episode);
                    }
                } else {
                    // Handle error
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<Episode> call, Throwable t) {
                // Handle failure
                callback.onFailure(t.getMessage());
            }
        });
    }
    public void getEpisodes(List<String> episodeUrls, final EpisodesCallback callback) {
        final List<Episode> episodes = new ArrayList<>();
        final AtomicInteger count = new AtomicInteger(episodeUrls.size());

        for (String url : episodeUrls) {
            getEpisodeById(url, new EpisodeCallback() {
                @Override
                public void onEpisodeLoaded(Episode episode) {
                    episodes.add(episode);
                    int remaining = count.decrementAndGet();
                    if (remaining == 0) {
                        callback.onEpisodesLoaded(episodes);
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    int remaining = count.decrementAndGet();
                    if (remaining == 0) {
                        callback.onEpisodesLoaded(episodes);
                    }
                }
            });
        }
    }

    public void getLocationById(String locationUrl, final LocationCallBack callback) {
        apiService.getLocationByUrl(locationUrl).enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                if (response.isSuccessful()) {
                    Location location = response.body();
                    if (location != null) {
                        callback.onLocationLoaded(location);
                    }
                } else {
                    // Handle error
                    callback.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                // Handle failure
                callback.onFailure(t.getMessage());
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
