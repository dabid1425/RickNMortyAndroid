package com.example.ricknmortyandroid.characters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ricknmortyandroid.R;

public class CharactersInShowFragment extends Fragment {
    public CharactersInShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.characters_in_show_fragment, container, false);
    }

    // Add any additional logic or methods specific to the first tab
}