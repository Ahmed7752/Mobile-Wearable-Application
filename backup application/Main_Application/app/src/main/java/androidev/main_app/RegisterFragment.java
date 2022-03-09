package androidev.main_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RegisterFragment extends Fragment {
    private static final int REQUEST_CODE = 1;


    DataBaseHelper db;
    private Button c_btn;
    private ImageButton back_btn;
    EditText username,email,repeatPassword,password;
    TextView id_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        db = new DataBaseHelper(getActivity());

        c_btn = view.findViewById(R.id.create_btn);
        back_btn = view.findViewById(R.id.Back_btn);
        username = view.findViewById(R.id.set_username);
        email = view.findViewById(R.id.set_email);
        password = view.findViewById(R.id.set_password);
        repeatPassword = view.findViewById(R.id.set_Repeat_password);

        id_view = (TextView) view.findViewById(R.id.textView2);



        c_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserModel userModel = null;

                if (username.getText().toString().equals("")||email.getText().toString().equals("")||password.getText().toString().equals("")||repeatPassword.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please complete all boxes", Toast.LENGTH_LONG).show();

                }else {
                    if (password.getText().toString().equals(repeatPassword.getText().toString())){

                        Boolean checkEmail = db.checkEmail(email.getText().toString());
                        if (checkEmail == true){

                            try {
                                userModel = new UserModel(-1, username.getText().toString(), email.getText().toString(), password.getText().toString());

                            }catch (Exception e){
                                Toast.makeText(getActivity(), "Please complete all boxes", Toast.LENGTH_LONG).show();
                            }
                            try {
                                db.addUser(userModel);
                                Toast.makeText(getActivity(), "User: "+ username.getText().toString() + " Has been created" , Toast.LENGTH_SHORT).show();
                                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.login_fragment_container, new ProfileCompletionFragment()).commit();
                                db.close();

                            }catch (Exception e){
                                Toast.makeText(getActivity(), "ERROR: Account not created", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Toast.makeText(getActivity(), "Email already exists", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(getActivity(), "Passwords do not match ", Toast.LENGTH_SHORT).show();
                    }


                    SharedPreferences idPrefs = getActivity().getSharedPreferences("LAST_CREATED_USER_ID", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = idPrefs.edit();
                    editor.putString("LastCreatedUserID", db.findID());
                    editor.commit();
                    db.close();

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
}
