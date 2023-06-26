package com.example.ricknmortyandroid.locations;

import com.example.ricknmortyandroid.API.Info;
import com.example.ricknmortyandroid.locations.Location;

import java.util.List;

public class LocationResponse {
    private List<Location> results;
    private Info info;

    public List<Location> getResults() {
        return results;
    }

    public void setResults(List<Location> results) {
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
