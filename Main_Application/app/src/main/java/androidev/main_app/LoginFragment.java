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
    //This tag is used to keep track of the current thread id
    private static final String TAG=LoginFragment.class.getSimpleName();

    //The required variables are declared here.
    private Button login_btn;
    private ImageButton back_btn;
    EditText email,password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The fragment_login layout is inflated into the view.
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        //The current thread ID of this fragment class will be shown in the log.
        Log.i(TAG, "MAIN THREAD ID: "+ Thread.currentThread().getId());

        //Constructor is used to create an object for the database helper class.
        DataBaseHelper db = new DataBaseHelper(getActivity());

        login_btn = view.findViewById(R.id.login_btn);
        back_btn = view.findViewById(R.id.Back_btn);
        email = view.findViewById(R.id.input_email);
        password = view.findViewById(R.id.input_password);

        //An on click listener is set for the login button.
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the email a password fields are empty then a message will be toasted to the user.
                if (email.getText().toString().equals("")||password.getText().toString().equals("")){

                    Toast.makeText(getActivity(), "Please complete all boxes", Toast.LENGTH_LONG).show();

                }else {
                    //An instance object of the runnable implemented class is created
                    DatabaseRunnable runnable = new DatabaseRunnable();
                    Log.i(TAG, "DATABASE THREAD ID: "+ Thread.currentThread().getId());
                    DataBaseHelper db = new DataBaseHelper(getActivity());
                    //The checkEmailPassword method from the database helper
                    // class is called and given the correct arguments
                    Boolean checkEmailPassword = db.checkEmailPassword(email.getText().toString(),
                            password.getText().toString());
                    if (checkEmailPassword == true){

                        //Using the runner point face a new thread has started.
                        new Thread(runnable).start();

                    }else {
                        Toast.makeText(getActivity(), "Failed: The email or the password is " +
                                "incorrect. Please try again. ", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        //An on click listener is set for the back button.
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The current fragment is replaced with the welcome fragment.
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login_fragment_container, new WelcomeFragment()).commit();
            }
        });
        return view;
    }
    //This class implements the runnable interface.
    class DatabaseRunnable implements Runnable {

        @Override
        public void run() {
            //Constructor is used to create an object for the database helper class.
            DataBaseHelper db = new DataBaseHelper(getActivity());
            //The required shared preference are called.
            SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_USER_ID", 0);

            //If the email and password match  then an intent instance object is created to
            // start the main admin activity, the database is enclosed.
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

            //edit the CURRENT_USER_ID shared preference. add the find the id of the user
            // who successfully logged on.
            SharedPreferences.Editor editor = userPrefs.edit();
            String currentUserID = db.findUser(email.getText().toString(),password.getText().toString());
            editor.putString("currentUserID", currentUserID);
            editor.commit();
            db.close();

        }
    }
}
