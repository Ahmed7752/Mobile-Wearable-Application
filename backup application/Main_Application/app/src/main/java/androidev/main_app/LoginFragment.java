package androidev.main_app;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class LoginFragment extends Fragment  {
    private static final String TAG=LoginFragment.class.getSimpleName();


    DataBaseHelper db;
    private Button login_btn;
    private ImageButton back_btn;
    EditText email,password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Log.i(TAG, "MAIN THREAD ID: "+ Thread.currentThread().getId());

        db = new DataBaseHelper(getActivity());

        login_btn = view.findViewById(R.id.login_btn);
        back_btn = view.findViewById(R.id.Back_btn);
        email = view.findViewById(R.id.input_email);
        password = view.findViewById(R.id.input_password);


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("")||password.getText().toString().equals("")){

                    Toast.makeText(getActivity(), "Please complete all boxes", Toast.LENGTH_LONG).show();

                }else {
                    DatabaseRunnable runnable = new DatabaseRunnable();
                    Log.i(TAG, "DATABASE THREAD ID: "+ Thread.currentThread().getId());
                    DataBaseHelper db = new DataBaseHelper(getActivity());
                    Boolean checkEmailPassword = db.checkEmailPassword(email.getText().toString(),password.getText().toString());
                    if (checkEmailPassword == true){

                        new Thread(runnable).start();

                    }else {
                        Toast.makeText(getActivity(), "Failed: The email or the password is incorrect. Please try again. ", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login_fragment_container, new WelcomeFragment()).commit();
            }
        });
        return view;
    }
    class DatabaseRunnable implements Runnable {

        @Override
        public void run() {
            SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_USER_ID", 0);

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

            SharedPreferences.Editor editor = userPrefs.edit();
            String currentUserID = db.findUser(email.getText().toString(),password.getText().toString());
            editor.putString("currentUserID", currentUserID);
            editor.commit();
            db.close();

        }
    }
}
