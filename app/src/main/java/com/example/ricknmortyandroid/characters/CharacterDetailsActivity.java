package com.example.ricknmortyandroid.characters;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ricknmortyandroid.R;
import com.example.ricknmortyandroid.episodes.Episode;
import com.example.ricknmortyandroid.episodes.EpisodeAdapter;
import com.example.ricknmortyandroid.episodes.EpisodeDetailActivity;
import com.example.ricknmortyandroid.interfaces.OnItemClickListener;

public class CharacterDetailsActivity extends AppCompatActivity {
    private CharacterDetailViewModel characterDetailViewModel;
    private EpisodeAdapter episodeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_detail_activity);

        String characterUrl = getIntent().getStringExtra("characterId");
        characterDetailViewModel = new ViewModelProvider(this).get(CharacterDetailViewModel.class);
        characterDetailViewModel.loadCharacterDetail(characterUrl);


        characterDetailViewModel.getCharacterData().observe(this, character -> {
            // Update the UI with the character data
            ImageView characterImage = findViewById(R.id.characterImage);
            TextView nameTextView = findViewById(R.id.nameTextView);
            TextView statusText = findViewById(R.id.statusText);
            TextView species = findViewById(R.id.speciesTextView);
            TextView genderText = findViewById(R.id.genderText);
            TextView locationText = findViewById(R.id.locationText);
            TextView firstEpisodeText = findViewById(R.id.firstEpisodeText);
            TextView lastEpisodeText = findViewById(R.id.lastEpisodeText);
            // Load and display the character image using a library like Glide or Picasso
            nameTextView.setText(character.getName());
            statusText.setText(character.getStatus());
            species.setText(character.getSpecies());
            genderText.setText(character.getGender());
            locationText.setText(character.getOrigin().getName());

            // You can also set the status color and other character information

            // Set up the RecyclerView for displaying episodes
            RecyclerView episodesRecyclerView = findViewById(R.id.episodesRecyclerView);
            episodeAdapter = new EpisodeAdapter();
            episodesRecyclerView.setAdapter(episodeAdapter);
            episodesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            episodeAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Episode selectedEpisode = characterDetailViewModel.getEpisodeAtIndex(position);
                    Intent intent = new Intent(CharacterDetailsActivity.this, EpisodeDetailActivity.class);
                    intent.putExtra("episodeId", selectedEpisode.getUrl());
                    startActivity(intent);
                }
            });
            // Observe the episodes data and update the adapter
            characterDetailViewModel.getEpisodesData().observe(this, episodes -> {
                episodeAdapter.setEpisodes(episodes);
            });
        });
    }
}
