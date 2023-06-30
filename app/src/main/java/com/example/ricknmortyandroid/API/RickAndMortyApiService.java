package com.example.ricknmortyandroid.API;

import com.example.ricknmortyandroid.characters.Character;
import com.example.ricknmortyandroid.characters.CharactersListResponse;
import com.example.ricknmortyandroid.episodes.Episode;
import com.example.ricknmortyandroid.episodes.EpisodeResponse;
import com.example.ricknmortyandroid.locations.Location;
import com.example.ricknmortyandroid.locations.LocationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RickAndMortyApiService {
    @GET("character")
    Call<CharactersListResponse> getCharacters(
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
    @GET
    Call<Character> getCharacterByUrl(@Url String url);
    @GET
    Call<Episode> getEpisodeByUrl(
            @Url String episodeUrl
    );
    @GET
    Call<Location> getLocationByUrl(@Url String url);
}
