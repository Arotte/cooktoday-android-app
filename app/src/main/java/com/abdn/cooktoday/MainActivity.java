package com.abdn.cooktoday;

import android.os.Bundle;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.abdn.cooktoday.cookbook.CookbookFragment;
import com.abdn.cooktoday.home.HomeFragment;
import com.abdn.cooktoday.profile.ProfileFragment;
import com.abdn.cooktoday.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity
        extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener
    {
        private static final String TAG = "MainActivity";

        private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // loading the default fragment
        loadFragment(new HomeFragment());

        // getting bottom navigation view and attaching the listener
        navigation = findViewById(R.id.bottomNavigationView);
        navigation.setOnNavigationItemSelectedListener(this);

        // fix shadow on bottom navbar
        navigation.setBackground(null);
        navigation.setSelectedItemId(R.id.bottomNavbarHome);

        // prevent navbars from flickering on transitions
        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.bottomAppBar), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
            return true;
        }
        return false;
    }

    public void setSelected(int id) {
        navigation.setSelectedItemId(id);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.bottomNavbarHome:
                fragment = new HomeFragment();
                break;

            case R.id.bottomNavbarProfile:
                fragment = new ProfileFragment();
                break;

            case R.id.bottomNavbarSearch:
                fragment = new SearchFragment();
                break;


            case R.id.bottomNavbarCookbook:
                fragment = new CookbookFragment();
                break;

            // case R.id.bottomNavbarUpload:
            //    startActivity(new Intent(MainActivity.this, UploadActivity.class));
            //    break;
        }

        return loadFragment(fragment);
    }
}