package com.example.ricknmortyandroid.API;

import com.example.ricknmortyandroid.characters.CharacterResponse;
import com.example.ricknmortyandroid.episodes.EpisodeResponse;
import com.example.ricknmortyandroid.locations.LocationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RickAndMortyApiService {
    @GET("character")
    Call<CharacterResponse> getCharacters(
            @Query("page") int page
    );

    @GET("location")
    Call<LocationResponse> getLocations(
            @Query("page") int page
    );

    @GET("episode")
    Call<EpisodeResponse> getEpisodes(
            @Query("page") int page
    );
}
