package com.example.anmobiletest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.anmobiletest.fragments.HomeFragment;
import com.example.anmobiletest.fragments.LikesFragment;
import com.example.anmobiletest.fragments.ProfileFragment;
import com.example.anmobiletest.fragments.SearchFragment;
import com.example.anmobiletest.fragments.ConnectionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "Base";
    Fragment Home;
    Fragment Likes;
    Fragment Profile;
    Fragment Search;
    Fragment Share;


    public void setupActivity(BottomNavigationView bottomNavigationView) {

        //W мы же обсуждали, что так делать не стоит. Эти фрагменты вообще не нужно хранить.
        Home = new HomeFragment();
        Likes = new LikesFragment();
        Profile = new ProfileFragment();
        Search = new SearchFragment();
        Share = new ConnectionFragment();


        bottomNavigationView.setOnItemSelectedListener(

                //I всё еще костыльно
                // стоит перенести вызов overridePendingTransition в loadFragment()

                //I код следует форматировать в CamelCase https://en.wikipedia.org/wiki/Camel_case
                // переменныеТак, КлассыТак.
                // Лайфхак: Ctrl+Alt+L форматирует код автоматически. Только имена не исправляет.


                item -> {
                    final int previousItem = bottomNavigationView.getSelectedItemId();
                    final int nextItem = item.getItemId();
                    if (previousItem != nextItem) {

                        /* // Код мог выглядеть так. Лучше же?

                        item.setChecked(true);
                        switch (nextItem) {
                            case R.id.home:
                                loadFragment(new HomeFragment());
                                break;
                            case R.id.search:
                                loadFragment(new SearchFragment());
                                break;
                            case R.id.share:
                                loadFragment(new ConnectionFragment());
                                break;
                            case R.id.likes:
                                loadFragment(new LikesFragment());
                                break;
                            case R.id.profile:
                                loadFragment(new ProfileFragment());
                                break;
                        }

                        */

                        switch (nextItem) {
                            case R.id.home:
                                if (Home == null)
                                    Home = new HomeFragment();
                                bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                                loadFragment(Home);
                                overridePendingTransition(0, 0);
                                break;
                            case R.id.search:
                                if (Search == null)
                                    Search = new SearchFragment();
                                bottomNavigationView.getMenu().findItem(R.id.search).setChecked(true);
                                loadFragment(Search);
                                overridePendingTransition(0, 0);
                                break;
                            case R.id.share:
                                if (Share == null)
                                    Share = new ConnectionFragment();
                                bottomNavigationView.getMenu().findItem(R.id.share).setChecked(true);
                                loadFragment(Share);
                                overridePendingTransition(0, 0);
                                break;
                            case R.id.likes:
                                if (Likes == null)
                                    Likes = new LikesFragment();
                                bottomNavigationView.getMenu().findItem(R.id.likes).setChecked(true);
                                loadFragment(Likes);
                                overridePendingTransition(0, 0);
                                break;
                            case R.id.profile:
                                if (Profile == null)
                                    Profile = new ProfileFragment();
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
