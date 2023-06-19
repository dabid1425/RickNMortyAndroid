package com.example.ricknmortyandroid.episodes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ricknmortyandroid.R;

public class EpisodesInShowFragment extends Fragment {
    public EpisodesInShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.episodes_in_show_fragment, container, false);
    }

    // Add any additional logic or methods specific to the first tab
}