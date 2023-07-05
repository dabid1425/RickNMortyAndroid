package com.example.ricknmortyandroid.characters;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ricknmortyandroid.R;
import com.example.ricknmortyandroid.interfaces.OnItemClickListener;
import com.google.android.material.imageview.ShapeableImageView;

import customView.LayoutFilterModel;
import customView.LayoutFilterViewModel;

public class CharactersInShowFragment extends Fragment {

    private CharacterViewModel characterViewModel;
    private CharacterAdapter characterAdapter;
    private ProgressBar loadingIndicator;
    private boolean isLoading = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        characterViewModel = new ViewModelProvider(this).get(CharacterViewModel.class);
        characterAdapter = new CharacterAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.characters_in_show_fragment, container, false);

        RecyclerView characterRecyclerView = view.findViewById(R.id.characterRecyclerView);
        loadingIndicator = view.findViewById(R.id.loadingIndicator);

        characterRecyclerView.setAdapter(characterAdapter);
        characterRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        characterViewModel.getCharacters().observe(getViewLifecycleOwner(), characters -> {
            characterAdapter.setCharacters(characters);
            isLoading = false;
            loadingIndicator.setVisibility(View.GONE);
        });

        ConstraintLayout constraintFilter = view.findViewById(R.id.constraintFilter);
        LayoutFilterModel filterModel = new LayoutFilterModel("Search Characters",
                R.drawable.ic_search_product,R.drawable.ic_filter,R.drawable.ic_sort);
        LayoutFilterViewModel filterViewModel = new LayoutFilterViewModel(filterModel);

        AppCompatEditText inputEditText = view.findViewById(R.id.inputEditText);
        inputEditText.setHint(filterViewModel.getPlaceHolderText());
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ShapeableImageView filterImageView = view.findViewById(R.id.filterImageView);
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
                        characterViewModel.loadCharacters();
                    }
                }
            }
        });
        characterAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Character selectedCharacter = characterViewModel.getCharacterAtIndex(position);

                // Create an Intent to start the new activity
                Intent intent = new Intent(requireContext(), CharacterDetailsActivity.class);
                intent.putExtra("characterId", selectedCharacter.getUrl());

                startActivity(intent);
            }
        });
        return view;
    }

}
