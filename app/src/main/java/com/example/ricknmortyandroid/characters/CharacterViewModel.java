package com.example.ricknmortyandroid.characters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ricknmortyandroid.API.Repository;
import com.example.ricknmortyandroid.Enums.DisplayStyle;
import com.example.ricknmortyandroid.Enums.Sort;
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
    private Sort sortStyle = Sort.NONE;
    private DisplayStyle displayStyle = DisplayStyle.TABLEVIEW;

    public CharacterViewModel() {
        repository = new Repository();
        charactersLiveData = (MutableLiveData<List<Character>>) repository.getCharacters();
    }

    public LiveData<List<Character>> getCharacters() {
        return searchTextCleared ?
                charactersLiveData : charactersSearchFilteredLiveData;
    }
    public void setDisplayStyle(DisplayStyle displayStyle) {
        this.displayStyle = displayStyle;
    }

    public void setSortStyle(Sort sortStyle) {
        this.sortStyle = sortStyle;
    }

    public int getSortStyleItem() {
        return sortStyle.ordinal();
    }
    public int getDisplayStyle(){
        return displayStyle.ordinal();
    }

    public void loadCharacters() {
        repository.loadCharacters();
    }

    public void setCharactersSearchFilteredLiveData(String searchString) {
        charactersSearchFilteredLiveData = new MutableLiveData<>();
        List<Character> characters = charactersLiveData.getValue();
        List<Character> filteredCharacter = new ArrayList<>();
        if (searchString.isEmpty()) {
            searchTextCleared = true;
        } else {
            searchTextCleared = false;
            for (Character character : characters) {
                if (character.getName().toLowerCase().contains(searchString.toLowerCase())) {
                    filteredCharacter.add(character);
                }
            }
            charactersSearchFilteredLiveData.setValue(filteredCharacter);
        }
    }

    private void setData(List<Character> characters){
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
                    Status status1 = Status.fromString(c1.getStatus());
                    Status status2 = Status.fromString(c2.getStatus());

                    if (status1 == status2) {
                        // If statuses are equal, sort by name
                        return c1.getName().compareToIgnoreCase(c2.getName());
                    } else if (status1 == status) {
                        // Sort characters with the specified status first
                        return -1;
                    } else if (status2 == status) {
                        // Sort characters with the specified status first
                        return 1;
                    } else if (status1 == Status.ALIVE && status2 != Status.ALIVE) {
                        // Sort "ALIVE" status characters to the top
                        return -1;
                    } else if (status1 != Status.ALIVE && status2 == Status.ALIVE) {
                        // Sort "ALIVE" status characters to the top
                        return 1;
                    } else if (status1 == Status.DEAD && status2 == Status.UNKNOWN) {
                        // Sort dead characters before unknown characters
                        return -1;
                    } else if (status1 == Status.UNKNOWN && status2 == Status.DEAD) {
                        // Sort dead characters before unknown characters
                        return 1;
                    } else {
                        // Sort by status
                        return status1.compareTo(status2);
                    }
                }
            });

            setData(characters);
        }
    }


    public void sortBy(int which) {
        sortStyle = Sort.values()[which];
        if (sortStyle == Sort.ASCENDING || sortStyle == Sort.DESCENDING){
            sortCharactersByName(sortStyle == Sort.ASCENDING );
        } else if (sortStyle != Sort.NONE) {
            sortCharactersByStatus(Status.fromString(sortStyle.getValue().toLowerCase()));
        } else {

        }
    }

    public void sortCharactersByName(boolean ascending) {
        List<Character> characters = searchTextCleared
                ? charactersLiveData.getValue() : charactersSearchFilteredLiveData.getValue();
        if (characters != null) {
            Collections.sort(characters, new Comparator<Character>() {
                @Override
                public int compare(Character c1, Character c2) {
                    if (ascending) {
                        return c1.getName().compareToIgnoreCase(c2.getName());
                    } else {
                        return c2.getName().compareToIgnoreCase(c1.getName());
                    }
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
