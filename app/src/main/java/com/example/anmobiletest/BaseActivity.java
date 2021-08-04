package com.example.anmobiletest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.anmobiletest.fragments.HomeFragment;
import com.example.anmobiletest.fragments.LikesFragment;
import com.example.anmobiletest.fragments.ProfileFragment;
import com.example.anmobiletest.fragments.SearchFragment;
import com.example.anmobiletest.fragments.ShareFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "Base";
    Fragment Home;
    Fragment Likes;
    Fragment Profile;
    Fragment Search;
    Fragment Share;



    public void setupActivity(BottomNavigationView bottomNavigationView) {
        Home = new HomeFragment();
        Likes = new LikesFragment();
        Profile = new ProfileFragment();
        Search = new SearchFragment();
        Share = new ShareFragment();



        bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    final int previousItem = bottomNavigationView.getSelectedItemId();
                    final int nextItem = item.getItemId();
                    if (previousItem != nextItem) {
                        switch (item.getItemId()) {

                            case R.id.home:
                                bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                                loadFragment(Home);
                                overridePendingTransition(0, 0);
                                break;
                            case R.id.search:
                                bottomNavigationView.getMenu().findItem(R.id.search).setChecked(true);
                                loadFragment(Search);
                                overridePendingTransition(0, 0);
                                break;
                            case R.id.share:
                                bottomNavigationView.getMenu().findItem(R.id.share).setChecked(true);
                                loadFragment(Share);
                                overridePendingTransition(0, 0);
                                break;
                            case R.id.likes:
                                bottomNavigationView.getMenu().findItem(R.id.likes).setChecked(true);
                                loadFragment(Likes);
                                overridePendingTransition(0, 0);
                                break;
                            case R.id.profile:
                                bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);
                                loadFragment(Profile);
                                overridePendingTransition(0, 0);
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
    }
}
