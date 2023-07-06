package com.example.ricknmortyandroid.characters;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ricknmortyandroid.Enums.DisplayStyle;
import com.example.ricknmortyandroid.Enums.SelectedDialogOption;
import com.example.ricknmortyandroid.Enums.Sort;
import com.example.ricknmortyandroid.R;
import com.example.ricknmortyandroid.interfaces.OnItemClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.imageview.ShapeableImageView;

import customView.LayoutFilterModel;
import customView.LayoutFilterView;
import customView.LayoutFilterViewModel;

public class CharactersInShowFragment extends Fragment {

    private CharacterViewModel characterViewModel;
    private CharacterAdapter characterAdapter;
    private ProgressBar loadingIndicator;
    private boolean isLoading = false;
    private LayoutFilterViewModel filterViewModel;
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
            //characters = characterViewModel.resort(characters);
            characterAdapter.setCharacters(characters);
            isLoading = false;
            loadingIndicator.setVisibility(View.GONE);
        });

        LayoutFilterView constraintFilter = view.findViewById(R.id.constraintFilter);
        LayoutFilterModel filterModel = new LayoutFilterModel("Search Characters",
                R.drawable.ic_search_product, R.drawable.ic_filter, R.drawable.ic_sort);
        filterViewModel = new LayoutFilterViewModel(filterModel);
        constraintFilter.setOnClickListenerAndViewModel(filterViewModel, new LayoutFilterView.LayoutFilterViewClickCallBack() {
            @Override
            public void inputEditTextChanged(String text) {
                characterViewModel.setCharactersSearchFilteredLiveData(text);
                characterAdapter.setCharacters(characterViewModel.getCharacters().getValue());
            }

            @Override
            public void filterViewSelected() {
                displayFilterSelectedView();
            }

            @Override
            public void sortViewSelected() {
                displaySortSelectedView();
            }
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

    private void displayFilterSelectedView() {
        displayDialog(R.string.viewBy, DisplayStyle.getStringArray(),
                characterViewModel.getDisplayStyle(),SelectedDialogOption.DISPLAYSTYLE);
    }

    private void displaySortSelectedView() {
        displayDialog(R.string.sortBy, Sort.getStringArray(),
                characterViewModel.getSortStyleItem(), SelectedDialogOption.SORTSTYLE);
    }

    private void displayDialog(int title, String[] singleItems, int checkedItem
            , SelectedDialogOption selectedDialogOption) {
        AlertDialog alertDialog;

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(title))
                .setSingleChoiceItems(singleItems, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (selectedDialogOption == SelectedDialogOption.DISPLAYSTYLE) {
                            //characterViewModel.viewBy(which);
                        } else {
                            characterViewModel.sortBy(which);
                            characterViewModel.setSortStyle(Sort.values()[which]);
                        }
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog = builder.create();

        alertDialog.show();

    }

}
