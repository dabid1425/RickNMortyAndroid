package com.example.ricknmortyandroid.episodes;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import com.example.ricknmortyandroid.locations.Location;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>  {
    private List<Episode> episodes = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_episode, parent, false);
        return new EpisodeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode episode = episodes.get(position);
        holder.bind(episode);
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    class EpisodeViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView airDateTextView;
        private TextView episodeTextView;
        private LinearLayout linearLayoutBackground;

        EpisodeViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            linearLayoutBackground = itemView.findViewById(R.id.linearLayoutBackground);
            airDateTextView = itemView.findViewById(R.id.airDateTextView);
            episodeTextView = itemView.findViewById(R.id.episodeTextView);
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

        void bind(Episode episode) {
            nameTextView.setText(episode.getName());
            airDateTextView.setText(episode.getAirDate());
            episodeTextView.setText(episode.getEpisode());
            GradientDrawable backgroundDrawable = (GradientDrawable) linearLayoutBackground.getBackground();
            backgroundDrawable.setStroke(2, Color.BLACK); // Replace 2 with the desired border width
        }
    }
}

