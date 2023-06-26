package com.example.ricknmortyandroid.characters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ricknmortyandroid.API.Repository;

import java.util.List;

public class CharacterViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<Character>> charactersLiveData;

    public CharacterViewModel() {
        repository = new Repository();
        charactersLiveData = repository.getCharacters();
    }

    public LiveData<List<Character>> getCharacters() {
        return charactersLiveData;
    }

    public void loadCharacters() {
        repository.loadCharacters();
    }

}