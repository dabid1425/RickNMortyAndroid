package com.example.ricknmortyandroid.API;

import com.example.ricknmortyandroid.characters.Character;
import com.example.ricknmortyandroid.characters.CharacterResponse;
import com.example.ricknmortyandroid.characters.CharactersListResponse;
import com.example.ricknmortyandroid.episodes.Episode;
import com.example.ricknmortyandroid.episodes.EpisodeResponse;
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
    @GET("character/{id}")
    Call<CharacterResponse> getCharacterById(@Path("id") int id);
    @GET
    Call<Episode> getEpisodeByUrl(
            @Url String episodeUrl
    );
}
