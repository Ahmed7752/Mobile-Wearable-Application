//This fragment class is the 1st to appear when the application is launched.
// It gives the user the opportunity to register or log into the application.
// From here the admin can also logon.
package androidev.main_app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class WelcomeFragment extends Fragment {

    //All the required variables are declared here.
    private Button reg_btn, log_btn, admin_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The fragment_welcome layout is inflated into the view.
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        //A constructor for the database helper class is created.
        DataBaseHelper db = new DataBaseHelper(getActivity());

        //Variables are matched with elements from the layout file.
        reg_btn = (Button) view.findViewById(R.id.register_btn);
        log_btn = (Button) view.findViewById(R.id.login_btn);
        admin_btn = (Button) view.findViewById(R.id.admin_login_btn);

        //An on click listener is set for the admin button.
        admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //The checkAdmin method from the database help class is called to see if any
                // admins are in the database.
                if (db.checkAdmin() == true){
                    //The addAdmin method from the database helper class is called.
                    // Hard coded admin login details are added to the database.
                    db.addAdmin();
                }

                //The current fragment is replaced with AdminLoginFragment.
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login_fragment_container, new AdminLoginFragment()).commit();


            }
        });

        //An on click listener is set for the register button.
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The current fragment is replaced with RegisterFragment.
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login_fragment_container, new RegisterFragment()).commit();
            }
        });

        //An on click listener is set for the user login button.
        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The current fragment is replaced with LoginFragment.
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login_fragment_container, new LoginFragment()).commit();
            }
        });

        return view;
    }
}
