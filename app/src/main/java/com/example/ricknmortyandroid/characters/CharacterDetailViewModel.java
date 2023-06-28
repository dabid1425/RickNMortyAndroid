package com.example.ricknmortyandroid.characters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ricknmortyandroid.API.Repository;
import com.example.ricknmortyandroid.episodes.Episode;

import java.util.List;

public class CharacterDetailViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<Character> characterLiveData;
    private MutableLiveData<List<Episode>> episodesLiveData;

    public CharacterDetailViewModel() {
        repository = Repository.getInstance();
        characterLiveData = new MutableLiveData<>();
        episodesLiveData = new MutableLiveData<>();
    }

    public void loadCharacterDetail(String characterUrl) {
        repository.getCharacterById(characterUrl, new Repository.CharacterCallback() {
            @Override
            public void onCharacterLoaded(Character character) {
                characterLiveData.setValue(character);
                // Fetch episodes associated with the character
                List<String> episodeUrls = character.getEpisode();
                if (episodeUrls != null && !episodeUrls.isEmpty()) {
                    repository.getEpisodes(episodeUrls, new Repository.EpisodesCallback() {
                        @Override
                        public void onEpisodesLoaded(List<Episode> episodes) {
                            episodesLiveData.setValue(episodes);
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

    public LiveData<Character> getCharacterData() {
        return characterLiveData;
    }

    public LiveData<List<Episode>> getEpisodesData() {
        return episodesLiveData;
    }
}
