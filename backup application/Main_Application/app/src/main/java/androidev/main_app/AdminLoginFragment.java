//This fragment class allows an admin to enter the login details and if
// successful is taken  into the admin section.
package androidev.main_app;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

public class AdminLoginFragment extends Fragment  {
    //This tag is used to keep track of the current thread id
    private static final String TAG=AdminLoginFragment.class.getSimpleName();


    private Button login_btn;
    private ImageButton back_btn;
    EditText email,password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_login, container, false);
        //The current thread ID of this fragment class will be shown in the log.
        Log.i(TAG, "MAIN THREAD ID: "+ Thread.currentThread().getId());
        //Constructor is created for the databasehelper class
        DataBaseHelper db = new DataBaseHelper(getActivity());
        //Button variables are matched with elements from the layout file.
        login_btn = view.findViewById(R.id.login_btn);
        back_btn = view.findViewById(R.id.Back_btn);
        email = view.findViewById(R.id.input_email);
        password = view.findViewById(R.id.input_password);

        //an on click listener is set for the login button.
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the email a password fields are empty then a message will be toasted to the user.
                if (email.getText().toString().equals("")||password.getText().toString().equals("")){

                    Toast.makeText(getActivity(), "Please complete all boxes", Toast.LENGTH_LONG).show();

                }else {
                    //a constructor for the runnable interface is created.
                    DatabaseRunnable runnable = new DatabaseRunnable();
                    //Using the runner point face a new thread has started.
                    new Thread(runnable).start();

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
            Log.i(TAG, "DATABASE THREAD ID: "+ Thread.currentThread().getId());

            //A new construct is created for the database helper class.
            DataBaseHelper db = new DataBaseHelper(getActivity());
            //The admincheckemailpassword method from the database helper
            // class is called and given the correct arguments
            Boolean adminCheckEmailPassword = db.adminCheckEmailPassword(email.getText().toString(),password.getText().toString());
            if (adminCheckEmailPassword == true){
            //If the email and password match  then an intent constructor is created to
                // start the main admin activity, the database is enclosed.
                Intent intent = new Intent(getActivity(), AdminActivity.class);
                startActivity(intent);
                db.close();

            }else {
                //If the email and password do not match what is on the database this
                // message is toasted to the user
                Toast.makeText(getActivity(), "Failed: The email or the password is incorrect. Please try again. ", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
