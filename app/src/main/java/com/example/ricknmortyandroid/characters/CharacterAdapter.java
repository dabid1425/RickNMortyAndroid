package com.example.ricknmortyandroid.characters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ricknmortyandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<Character> characters = new ArrayList<>();

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_character, parent, false);
        return new CharacterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.bind(character);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    static class CharacterViewHolder extends RecyclerView.ViewHolder {
        private ImageView characterImageView;
        private TextView nameTextView;
        private TextView statusTextView;
        private TextView speciesTextView;
        private TextView genderTextView;

        CharacterViewHolder(View itemView) {
            super(itemView);
            characterImageView = itemView.findViewById(R.id.characterImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            speciesTextView = itemView.findViewById(R.id.speciesTextView);
            genderTextView = itemView.findViewById(R.id.genderTextView);
        }

        void bind(Character character) {
            nameTextView.setText(character.getName());
            statusTextView.setText(character.getStatus());
            speciesTextView.setText(character.getSpecies());
            genderTextView.setText(character.getGender());

            // Load character image using Picasso or Glide
            Picasso.get().load(character.getImage()).into(characterImageView);
        }
    }
}