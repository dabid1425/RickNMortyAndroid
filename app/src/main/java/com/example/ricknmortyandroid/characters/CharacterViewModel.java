package com.example.ricknmortyandroid.characters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ricknmortyandroid.API.Repository;
import com.example.ricknmortyandroid.Enums.Status;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CharacterViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<Character>> charactersLiveData;

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

    public void sortCharactersByStatus(Status status) {
        List<Character> characters = charactersLiveData.getValue();
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
            charactersLiveData.setValue(characters);
        }
    }

    public void sortCharactersByNameAscending() {
        List<Character> characters = charactersLiveData.getValue();
        if (characters != null) {
            Collections.sort(characters, new Comparator<Character>() {
                @Override
                public int compare(Character c1, Character c2) {
                    return c1.getName().compareToIgnoreCase(c2.getName());
                }
            });
            charactersLiveData.setValue(characters);
        }
    }

    public void sortCharactersByNameDescending() {
        List<Character> characters = charactersLiveData.getValue();
        if (characters != null) {
            Collections.sort(characters, new Comparator<Character>() {
                @Override
                public int compare(Character c1, Character c2) {
                    return c2.getName().compareToIgnoreCase(c1.getName());
                }
            });
            charactersLiveData.setValue(characters);
        }
    }
    public Character getCharacterAtIndex(int index) {
        List<Character> characters = charactersLiveData.getValue();
        if (characters != null && index >= 0 && index < characters.size()) {
            return characters.get(index);
        }
        return null;
    }
}
