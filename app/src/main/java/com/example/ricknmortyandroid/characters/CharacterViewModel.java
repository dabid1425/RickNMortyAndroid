package com.example.ricknmortyandroid.characters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ricknmortyandroid.API.CharacterRepository;

import java.util.List;

public class CharacterViewModel extends ViewModel {
    private CharacterRepository characterRepository;
    private LiveData<List<Character>> charactersLiveData;

    public CharacterViewModel() {
        characterRepository = new CharacterRepository();
        charactersLiveData = characterRepository.getCharacters();
    }

    public LiveData<List<Character>> getCharacters() {
        return charactersLiveData;
    }

    public void loadCharacters() {
        characterRepository.loadCharacters();
    }

}