package com.example.ricknmortyandroid.locations;

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

public class LocationDetailActivity extends AppCompatActivity {
    private LocationDetailViewModel locationDetailViewModel;
    private CharacterAdapter characterAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail_activity);

        String locationId = getIntent().getStringExtra("locationId");
        locationDetailViewModel = new ViewModelProvider(this).get(LocationDetailViewModel.class);
        locationDetailViewModel.loadLocationDetail(locationId);


        locationDetailViewModel.getLocationData().observe(this, location -> {
            // Update the UI with the character data
            TextView locationNameTextView = findViewById(R.id.locationNameTextView);
            TextView dimensionTextView = findViewById(R.id.dimensionTextView);
            TextView typeTextView = findViewById(R.id.typeTextView);

            locationNameTextView.setText(location.getName());
            dimensionTextView.setText(location.getDimension());
            typeTextView.setText(location.getType());


            // You can also set the status color and other character information

            // Set up the RecyclerView for displaying episodes
            RecyclerView characterRecyclerView = findViewById(R.id.characterRecyclerView);
            characterAdapter = new CharacterAdapter();
            characterRecyclerView.setAdapter(characterAdapter);
            characterRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            characterAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Character selectedCharacter = locationDetailViewModel.getCharacterAtIndex(position);
                    Intent intent = new Intent(LocationDetailActivity.this, CharacterDetailsActivity.class);
                    intent.putExtra("characterId", selectedCharacter.getUrl());
                    startActivity(intent);
                }
            });
            // Observe the episodes data and update the adapter
            locationDetailViewModel.getCharactersData().observe(this, characters -> {
                characterAdapter.setCharacters(characters);
            });
        });
    }
}
