//This is a fragment class, it all allows are use it to create a new account and add it to the database.

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

public class AddUserFragment extends Fragment {



    //initialise variables.
    private Button c_btn;
    private ImageButton back_btn;
    EditText username,email,repeatPassword,password;
    TextView id_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        //create constructor for the DataBase Helper class.
        DataBaseHelper db = new DataBaseHelper(getActivity());

        //find and match variables to elements in the layout file.
        c_btn = view.findViewById(R.id.create_btn);
        back_btn = view.findViewById(R.id.Back_btn);
        username = view.findViewById(R.id.set_username);
        email = view.findViewById(R.id.set_email);
        password = view.findViewById(R.id.set_password);
        repeatPassword = view.findViewById(R.id.set_Repeat_password);

        id_view = (TextView) view.findViewById(R.id.textView2);


        //create on click listener for the create button.
        c_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialise an object using the UserModel class.
                UserModel userModel = null;

                //check if the user input sections are blank, if so toast error message.
                if (username.getText().toString().equals("")||email.getText().toString().equals("")||password.getText().toString().equals("")||repeatPassword.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please complete all boxes", Toast.LENGTH_LONG).show();

                }else {
                    //check if the user has repeated the password correctly
                    if (password.getText().toString().equals(repeatPassword.getText().toString())){
                        //check if the email is already in the database using the checkemail method from the database helper class.
                        Boolean checkEmail = db.checkEmail(email.getText().toString());
                        if (checkEmail == true){

                            try {
                                //populate the initialised object with the user input data.
                                userModel = new UserModel(-1, username.getText().toString(), email.getText().toString(), password.getText().toString());

                            }catch (Exception e){
                                Toast.makeText(getActivity(), "Please complete all boxes", Toast.LENGTH_LONG).show();
                            }
                            try {
                                //use the userModel object as an argument for the adduser method from the database helper class.
                                db.addUser(userModel);
                                Toast.makeText(getActivity(), "User: "+ username.getText().toString() + " Has been created" , Toast.LENGTH_SHORT).show();
                               // This transition replaces the current fragment in the fragment manager with viewusefragment

                                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, new ViewUsersFragment()).commit();
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
                    db.close();

                }
            }
        });

        //create on click listener for the back button.
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user the Fragment Manager transition to the ViewUsers Fragment.
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new ViewUsersFragment()).commit();
            }
        });







        return view;
    }
}
