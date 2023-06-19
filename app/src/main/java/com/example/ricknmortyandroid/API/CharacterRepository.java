package com.example.ricknmortyandroid.API;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ricknmortyandroid.characters.Character;
import com.example.ricknmortyandroid.characters.CharacterResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharacterRepository {
    private RickAndMortyApiService apiService;
    private MutableLiveData<List<Character>> charactersLiveData;
    private int currentPage = 1;
    private boolean isFetchingData = false;

    public CharacterRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(RickAndMortyApiService.class);
        charactersLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Character>> getCharacters() {
        loadCharacters();
        return charactersLiveData;
    }

    public void loadCharacters() {
        if (isFetchingData) {
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

    private void updatePagination(Info info) {
        if (info != null) {
            String nextPageUrl = info.getNext();
            if (nextPageUrl != null && !nextPageUrl.isEmpty()) {
                currentPage++;
            }
        }
    }
}
