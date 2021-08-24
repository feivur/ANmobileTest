package com.example.anmobiletest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.anmobiletest.fragments.HomeFragment;
import com.example.anmobiletest.fragments.SearchFragment;
import com.example.anmobiletest.fragments.ConnectionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "Base";

    public void setupActivity(BottomNavigationView bottomNavigationView) {

        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    final int previousItem = bottomNavigationView.getSelectedItemId();
                    final int nextItem = item.getItemId();
                    if (previousItem != nextItem) {
                        switch (nextItem) {

                            case R.id.home:
                                bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                                loadFragment(new HomeFragment());
                                break;
                            case R.id.search:
                                bottomNavigationView.getMenu().findItem(R.id.search).setChecked(true);
                                loadFragment(new SearchFragment());
                                break;
                            case R.id.share:
                                bottomNavigationView.getMenu().findItem(R.id.share).setChecked(true);
                                loadFragment(new ConnectionFragment());
                                break;
                        }
                    }
                    return true;
                });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_content, fragment);
        ft.commit();
        overridePendingTransition(0, 0);
    }
}
