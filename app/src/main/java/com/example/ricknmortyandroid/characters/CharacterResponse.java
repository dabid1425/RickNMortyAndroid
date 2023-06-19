package com.example.ricknmortyandroid.characters;
import com.example.ricknmortyandroid.API.Info;

import java.util.List;

public class CharacterResponse {
    private List<Character> results;
    private Info info;

    public List<Character> getResults() {
        return results;
    }

    public void setResults(List<Character> results) {
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
