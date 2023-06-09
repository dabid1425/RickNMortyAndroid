package com.example.ricknmortyandroid.episodes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ricknmortyandroid.API.Repository;
import com.example.ricknmortyandroid.characters.Character;

import java.util.List;

public class EpisodeViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<Episode>> episodesLiveData;

    public EpisodeViewModel() {
        repository = new Repository();
        episodesLiveData = repository.getEpisodes();
    }

    public LiveData<List<Episode>> getEpisodes() {
        return episodesLiveData;
    }

    public void loadEpisodes() {
        repository.loadEpisodes();
    }

    public Episode getEpisodeAtIndex(int index) {
        List<Episode> episodes = episodesLiveData.getValue();
        if (episodes != null && index >= 0 && index < episodes.size()) {
            return episodes.get(index);
        }
        return null;
    }
}