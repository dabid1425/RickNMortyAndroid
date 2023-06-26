package com.example.ricknmortyandroid.locations;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ricknmortyandroid.R;

public class LocationsInShowFragment extends Fragment {

    private LocationViewModel locationViewModel;
    private LocationAdapter locationAdapter;
    private ProgressBar loadingIndicator;
    private boolean isLoading = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationViewModel = new ViewModelProvider(this).get(LocationViewModel.class);
        locationAdapter = new LocationAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.locations_in_show_fragment, container, false);

        RecyclerView characterRecyclerView = view.findViewById(R.id.locationsRecyclerView);
        loadingIndicator = view.findViewById(R.id.loadingIndicator);

        characterRecyclerView.setAdapter(locationAdapter);
        characterRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        locationViewModel.getLocations().observe(getViewLifecycleOwner(), locations -> {
            locationAdapter.setLocations(locations);
            isLoading = false;
            loadingIndicator.setVisibility(View.GONE);
        });

        characterRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                    if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                        isLoading = true;
                        loadingIndicator.setVisibility(View.VISIBLE);
                        locationViewModel.loadLocations();
                    }
                }
            }
        });

        return view;
    }
}
