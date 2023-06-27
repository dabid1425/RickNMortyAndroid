package com.example.ricknmortyandroid.characters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ricknmortyandroid.API.Repository;
import com.example.ricknmortyandroid.episodes.Episode;

import java.util.List;

public class CharacterDetailViewModel extends ViewModel {
    private MutableLiveData<Character> characterData = new MutableLiveData<>();
    private MutableLiveData<List<Episode>> episodesLiveData = new MutableLiveData<>();
    private Repository characterRepository;

    public CharacterDetailViewModel() {
        characterRepository = Repository.getInstance();
    }

    public LiveData<Character> getCharacterData() {
        return characterData;
    }

    public LiveData<List<Episode>> getEpisodesData() {
        return episodesLiveData;
    }

    public void loadCharacterDetail(int characterId) {
        characterRepository.getCharacter(characterId, new Repository.CharacterCallback() {
            @Override
            public void onCharacterLoaded(Character character) {
                characterData.setValue(character);

                // Retrieve the list of episode URLs from the character
                List<String> episodeUrls = character.getEpisode();

                // Load episodes based on the list of URLs
                loadEpisodes(episodeUrls);
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle error case
            }
        });
    }

    private void loadEpisodes(List<String> episodeUrls) {
        characterRepository.getEpisodes(episodeUrls, new Repository.EpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                episodesLiveData.setValue(episodes);
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle error case
            }
        });
    }
}
