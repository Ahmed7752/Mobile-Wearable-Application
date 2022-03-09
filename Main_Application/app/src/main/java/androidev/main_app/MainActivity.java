//This is the main user activity, this is where all the user related fragments are presented.
package androidev.main_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.FacebookSdk;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Here the bottom navigation bar class is implemented, which will allow users to switch between fragments easily.
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    //The navigation select a listener is implemented to keep track of
    // which navigation icon is pressed by the user, each item on the navigation bar is
    // paired with have you fragment. When a user clicks one of the items they are taken to that fragment
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectFragment = null;
            switch (item.getItemId())
            {
                //The home item is linked to the HomeFragment
                case R.id.home:
                    selectFragment = new HomeFragment();
                    break;
                    //The favourites item is linked to the FavouritesFragment
                case R.id.favourites:
                    selectFragment = new FavouritesFragment();
                    break;
                    //The profile item is linked to the ProfileFragment
                case R.id.profile:
                    selectFragment = new ProfileFragment();
                    break;
                //The stats item is linked to the StatsFragment
                case R.id.stats:
                    selectFragment = new StatsFragment();
                    break;
            }

            //The Fragmin manager replaces the current fragment with the one selected by the user.
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();
            return true;
        }
    };


}