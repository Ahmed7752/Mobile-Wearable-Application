//This is the main admin activity, this is where all the admin related fragments are presented.
package androidev.main_app;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    //Here the bottom navigation bar class is implemented, which will allow users to switch between fragments easily.
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoadDataFragment()).commit();

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
                case R.id.loadData:
                    selectFragment = new LoadDataFragment();
                    break;
                case R.id.ViewUsers:
                    selectFragment = new ViewUsersFragment();
                    break;

            }
            //The Fragmin manager replaces the current fragment with the one selected by the user.
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();
            return true;
        }
    };
}