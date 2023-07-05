package com.example.ricknmortyandroid.characters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ricknmortyandroid.API.Repository;
import com.example.ricknmortyandroid.Enums.Status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CharacterViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<Character>> charactersLiveData;
    private MutableLiveData<List<Character>> charactersSearchFilteredLiveData;
    private boolean searchTextCleared = true;

    public CharacterViewModel() {
        repository = new Repository();
        charactersLiveData = (MutableLiveData<List<Character>>) repository.getCharacters();
    }

    public LiveData<List<Character>> getCharacters() {
        return charactersLiveData;
    }

    public void loadCharacters() {
        repository.loadCharacters();
    }

    public boolean getSearchTextCleared(){
        return searchTextCleared;
    }

    public void setSearchTextCleared() {
        this.searchTextCleared = !this.searchTextCleared;
    }

    public void setCharactersSearchFilteredLiveData(String searchString) {
        charactersSearchFilteredLiveData = new MutableLiveData<>();
        List<Character> characters = charactersLiveData.getValue();
        List<Character> filteredCharacter = new ArrayList<>();
        for(Character character : characters){
            if (character.getName().contains(searchString)) {
                filteredCharacter.add(character);
            }
        }
        charactersSearchFilteredLiveData.setValue(filteredCharacter);
    }

    public void setData(List<Character> characters){
        if (searchTextCleared) {
            charactersLiveData.setValue(characters);
        } else {
            charactersSearchFilteredLiveData.setValue(characters);
        }
    }

    public void sortCharactersByStatus(Status status) {
        List<Character> characters = searchTextCleared
                ? charactersLiveData.getValue() : charactersSearchFilteredLiveData.getValue();
        if (characters != null) {
            Collections.sort(characters, new Comparator<Character>() {
                @Override
                public int compare(Character c1, Character c2) {
                    return Status.fromString(c1.getStatus()).compareTo(Status.fromString(c2.getStatus()));
                }
            });
            if (status == Status.ALIVE) {
                Collections.reverse(characters);
            }
            setData(characters);
        }
    }

    public void sortCharactersByNameAscending() {
        List<Character> characters = searchTextCleared
                ? charactersLiveData.getValue(): charactersSearchFilteredLiveData.getValue();
        if (characters != null) {
            Collections.sort(characters, new Comparator<Character>() {
                @Override
                public int compare(Character c1, Character c2) {
                    return c1.getName().compareToIgnoreCase(c2.getName());
                }
            });
            setData(characters);
        }
    }

    public void sortCharactersByNameDescending() {
        List<Character> characters = searchTextCleared
                ? charactersLiveData.getValue(): charactersSearchFilteredLiveData.getValue();
        if (characters != null) {
            Collections.sort(characters, new Comparator<Character>() {
                @Override
                public int compare(Character c1, Character c2) {
                    return c2.getName().compareToIgnoreCase(c1.getName());
                }
            });
            setData(characters);
        }
    }
    public Character getCharacterAtIndex(int index) {
        List<Character> characters = searchTextCleared
                ? charactersLiveData.getValue() : charactersSearchFilteredLiveData.getValue();
        if (characters != null && index >= 0 && index < characters.size()) {
            return characters.get(index);
        }
        return null;
    }
}
