package com.example.ricknmortyandroid.characters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ricknmortyandroid.Enums.Status;
import com.example.ricknmortyandroid.R;
import com.example.ricknmortyandroid.interfaces.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<Character> characters = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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

    class CharacterViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout backgroundGradient;
        private ImageView characterImageView;
        private TextView nameTextView;
        private TextView statusTextView;
        private TextView speciesTextView;
        private TextView genderTextView;

        CharacterViewHolder(View itemView) {
            super(itemView);
            backgroundGradient = itemView.findViewById(R.id.backgroundGradient);
            characterImageView = itemView.findViewById(R.id.characterImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            speciesTextView = itemView.findViewById(R.id.speciesTextView);
            genderTextView = itemView.findViewById(R.id.genderTextView);

            // Set click listener for the itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }

        void bind(Character character) {
            nameTextView.setText(character.getName());
            statusTextView.setText(character.getStatus());
            speciesTextView.setText(character.getSpecies());
            genderTextView.setText(character.getGender());
            // Get the background drawable
            int colorResource = R.drawable.alive_gradient;
            switch (Status.fromString(character.getStatus())){
                case ALIVE:
                    colorResource = R.drawable.alive_gradient;
                    break;
                case DEAD:
                    colorResource = R.drawable.dead_gradient;
                    break;
                case UNKNOWN:
                    colorResource = R.drawable.unknown_gradient;
                    break;
            }
            backgroundGradient.setBackgroundResource(colorResource);
            // Load character image using Picasso or Glide
            Picasso.get().load(character.getImage()).into(characterImageView);
        }
    }

    // Interface for item click callback

}
