package customView;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ricknmortyandroid.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

public class LayoutFilterView extends ConstraintLayout {
    public interface LayoutFilterViewClickCallBack {
        void inputEditTextChanged(String text);
        void filterViewSelected();
        void sortViewSelected();
    }
    private LayoutFilterViewClickCallBack layoutFilterViewClickCallBack;
    private LayoutFilterViewModel viewModel;
    private AppCompatEditText inputEditText;
    private ShapeableImageView filterImageView;
    private MaterialCardView cardViewFilter;
    private MaterialCardView cardViewSort;
    private ShapeableImageView sortImageView;
    private AppCompatImageView searchImageView;
    public LayoutFilterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }
    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.layout_filter, this, true);
        inputEditText = findViewById(R.id.inputEditText);
        filterImageView = findViewById(R.id.filterImageView);
        cardViewFilter= findViewById(R.id.cardViewFilter);
        cardViewSort= findViewById(R.id.cardViewSort);
        sortImageView= findViewById(R.id.sortImageView);
        searchImageView= findViewById(R.id.searchImageView);
        setOnListeners();
    }
    public void setOnClickListenerAndViewModel(LayoutFilterViewModel viewModel,
                                               LayoutFilterViewClickCallBack
                                                       layoutFilterViewClickCallBack) {
        this.layoutFilterViewClickCallBack = layoutFilterViewClickCallBack;
        this.viewModel = viewModel;
        updateViewItems();
    }
    private void updateViewItems(){
            inputEditText.setHint(viewModel.getPlaceHolderText());
            filterImageView.setImageResource(viewModel.getFilterImage());
            sortImageView.setImageResource(viewModel.getSortImage());
    }
    private void setOnListeners(){
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (layoutFilterViewClickCallBack != null) {
                    layoutFilterViewClickCallBack.inputEditTextChanged(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cardViewFilter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layoutFilterViewClickCallBack != null) {
                    layoutFilterViewClickCallBack.filterViewSelected();
                }
            }
        });
        cardViewSort.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layoutFilterViewClickCallBack != null) {
                    layoutFilterViewClickCallBack.sortViewSelected();
                }
            }
        });
    }
}
