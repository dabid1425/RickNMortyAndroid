package com.example.ricknmortyandroid.episodes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ricknmortyandroid.R;
import com.example.ricknmortyandroid.characters.Character;
import com.example.ricknmortyandroid.characters.CharacterAdapter;
import com.example.ricknmortyandroid.characters.CharacterDetailsActivity;
import com.example.ricknmortyandroid.interfaces.OnItemClickListener;

public class EpisodeDetailActivity extends AppCompatActivity {
    private EpisodeDetailViewModel episodeDetailViewModel;
    private CharacterAdapter characterAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.episode_detail_activity);

        String episodeId = getIntent().getStringExtra("episodeId");
        episodeDetailViewModel = new ViewModelProvider(this).get(EpisodeDetailViewModel.class);
        episodeDetailViewModel.loadEpisodeDetail(episodeId);


        episodeDetailViewModel.getEpisodeData().observe(this, episode -> {
            // Update the UI with the character data
            TextView episodeNameTextView = findViewById(R.id.episodeNameTextView);
            TextView airDateTextView = findViewById(R.id.airDateTextView);
            TextView episodeCodeTextView = findViewById(R.id.episodeCodeTextView);

            episodeNameTextView.setText(episode.getName());
            airDateTextView.setText(episode.getAirDate());
            episodeCodeTextView.setText(episode.getEpisode());


            // You can also set the status color and other character information

            // Set up the RecyclerView for displaying episodes
            RecyclerView characterRecyclerView = findViewById(R.id.characterRecyclerView);
            characterAdapter = new CharacterAdapter();
            characterRecyclerView.setAdapter(characterAdapter);
            characterRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            characterAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Character selectedCharacter = episodeDetailViewModel.getCharacterAtIndex(position);
                    Intent intent = new Intent(EpisodeDetailActivity.this, CharacterDetailsActivity.class);
                    intent.putExtra("characterId", selectedCharacter.getUrl());
                    startActivity(intent);
                }
            });
            // Observe the episodes data and update the adapter
            episodeDetailViewModel.getCharactersData().observe(this, characters -> {
                characterAdapter.setCharacters(characters);
            });
        });
    }
}
