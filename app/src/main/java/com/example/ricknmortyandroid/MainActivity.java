package com.example.ricknmortyandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import com.example.ricknmortyandroid.characters.CharactersInShowFragment;
import com.example.ricknmortyandroid.episodes.EpisodesInShowFragment;
import com.example.ricknmortyandroid.locations.LocationsInShowFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity{
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            // This method is called when a new page is selected in the ViewPager
            // You can perform actions based on the selected page position
            // For example, update the title or perform other operations
            switch (position) {
                case 0:
                    setTitle("Characters");
                    break;
                case 1:
                    setTitle("Episodes");
                    break;
                case 2:
                    setTitle("Locations");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // Create the adapter for the ViewPager
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(this);
        viewPager.setAdapter(tabPagerAdapter);

        // Connect the TabLayout with the ViewPager
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabPagerAdapter.getTabTitle(position))
        ).attach();
        viewPager.registerOnPageChangeCallback(pageChangeCallback);
    }

    private class TabPagerAdapter extends FragmentStateAdapter {
        private final String[] tabTitles = {"Characters", "Episodes", "Locations"};
        public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }
        public String getTabTitle(int position) {
            return tabTitles[position];
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new CharactersInShowFragment();
                case 1:
                    return new EpisodesInShowFragment();
                case 2:
                    return new LocationsInShowFragment();
                default:
                    throw new IllegalArgumentException("Invalid tab position");
            }
        }
        public int getItemCount() {
            return tabTitles.length;
        }
    }
}
