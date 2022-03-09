package androidev.main_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class WelcomeFragment extends Fragment {

    DataBaseHelper db;
    private Button reg_btn, log_btn, admin_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        db = new DataBaseHelper(getActivity());

        reg_btn = (Button) view.findViewById(R.id.register_btn);
        log_btn = (Button) view.findViewById(R.id.login_btn);
        admin_btn = (Button) view.findViewById(R.id.admin_login_btn);

        admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (db.checkAdmin() == true){
                    db.addAdmin();
                }

                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login_fragment_container, new AdminLoginFragment()).commit();


            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login_fragment_container, new RegisterFragment()).commit();
            }
        });

        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login_fragment_container, new LoginFragment()).commit();
            }
        });

        return view;
    }
}
