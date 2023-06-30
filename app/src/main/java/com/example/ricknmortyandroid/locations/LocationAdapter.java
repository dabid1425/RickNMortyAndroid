package com.example.ricknmortyandroid.locations;
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

import com.example.ricknmortyandroid.R;
import com.example.ricknmortyandroid.interfaces.OnItemClickListener;


import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private List<Location> locations = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    public void setLocations(List<Location> locations) {
        this.locations = locations;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView typeTextView;
        private TextView dimensionTextView;
        private LinearLayout linearLayoutBackground;

        LocationViewHolder(View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            linearLayoutBackground = itemView.findViewById(R.id.linearLayoutBackground);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            dimensionTextView = itemView.findViewById(R.id.dimensionTextView);
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

        void bind(Location location) {
            nameTextView.setText(location.getName());
            typeTextView.setText(location.getType());
            dimensionTextView.setText(location.getDimension());
            GradientDrawable backgroundDrawable = (GradientDrawable) linearLayoutBackground.getBackground();
            backgroundDrawable.setStroke(2, Color.BLACK); // Replace 2 with the desired border width
        }
    }
}
