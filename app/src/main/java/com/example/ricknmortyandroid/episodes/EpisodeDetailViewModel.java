package com.example.ricknmortyandroid.episodes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ricknmortyandroid.API.Repository;
import com.example.ricknmortyandroid.characters.Character;

import java.util.List;

public class EpisodeDetailViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<Episode> episodeLiveData;
    private MutableLiveData<List<Character>> charactersLiveData;
    public EpisodeDetailViewModel() {
        repository = Repository.getInstance();
        episodeLiveData = new MutableLiveData<>();
        charactersLiveData = new MutableLiveData<>();
    }

    public void loadEpisodeDetail(String episodeUrl) {
        repository.getEpisodeById(episodeUrl, new Repository.EpisodeCallback() {
            @Override
            public void onEpisodeLoaded(Episode episode) {
                episodeLiveData.setValue(episode);
                // Fetch episodes associated with the character
                List<String> characterUrls = episode.getCharacters();
                if (characterUrls != null && !characterUrls.isEmpty()) {
                    repository.getCharacters(characterUrls, new Repository.CharactersCallback() {
                        @Override
                        public void onCharactersLoaded(List<Character> characters) {
                            charactersLiveData.setValue(characters);
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
    public Character getCharacterAtIndex(int index) {
        List<Character> characters = charactersLiveData.getValue();
        if (characters != null && index >= 0 && index < characters.size()) {
            return characters.get(index);
        }
        return null;
    }
    public LiveData<Episode> getEpisodeData() {
        return episodeLiveData;
    }

    public LiveData<List<Character>> getCharactersData() {
        return charactersLiveData;
    }
}
