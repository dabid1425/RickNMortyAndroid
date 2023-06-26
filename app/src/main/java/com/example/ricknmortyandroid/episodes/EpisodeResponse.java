package com.example.ricknmortyandroid.episodes;

import com.example.ricknmortyandroid.API.Info;

import java.util.List;

public class EpisodeResponse {
    private List<Episode> results;
    private Info info;

    public List<Episode> getResults() {
        return results;
    }

    public void setResults(List<Episode> results) {
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
