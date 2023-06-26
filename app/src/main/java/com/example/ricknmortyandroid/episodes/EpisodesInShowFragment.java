package com.example.ricknmortyandroid.episodes;
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

public class EpisodesInShowFragment extends Fragment {

    private EpisodeViewModel episodeViewModel;
    private EpisodeAdapter episodeAdapter;
    private ProgressBar loadingIndicator;
    private boolean isLoading = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        episodeViewModel = new ViewModelProvider(this).get(EpisodeViewModel.class);
        episodeAdapter = new EpisodeAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.episodes_in_show_fragment, container, false);

        RecyclerView episodesRecyclerView = view.findViewById(R.id.episodesRecyclerView);
        loadingIndicator = view.findViewById(R.id.loadingIndicator);

        episodesRecyclerView.setAdapter(episodeAdapter);
        episodesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        episodeViewModel.getEpisodes().observe(getViewLifecycleOwner(), episodes -> {
            episodeAdapter.setEpisodes(episodes);
            isLoading = false;
            loadingIndicator.setVisibility(View.GONE);
        });

        episodesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        episodeViewModel.loadEpisodes();
                    }
                }
            }
        });

        return view;
    }
}
