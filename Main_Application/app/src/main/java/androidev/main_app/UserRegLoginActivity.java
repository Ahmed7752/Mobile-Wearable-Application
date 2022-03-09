package androidev.main_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class UserRegLoginActivity extends AppCompatActivity {
    private static final String TAG=UserRegLoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg_login);
        Log.i(TAG, "THREAD ID: "+ Thread.currentThread().getId());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.login_fragment_container, new WelcomeFragment()).commit();



    }
}