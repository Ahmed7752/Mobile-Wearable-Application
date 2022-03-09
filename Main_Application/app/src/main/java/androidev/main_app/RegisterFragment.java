//This fragment class allows a user to register an new account.
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

    //The required variables are declared here.
    private Button c_btn;
    private ImageButton back_btn;
    EditText username,email,repeatPassword,password;
    TextView id_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The home fragment layout file is inflated into the view.
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        //Constructor is used to create an object for the database helper class.
        DataBaseHelper db = new DataBaseHelper(getActivity());
        //Variables are matched with elements from the layout file.
        c_btn = view.findViewById(R.id.create_btn);
        back_btn = view.findViewById(R.id.Back_btn);
        username = view.findViewById(R.id.set_username);
        email = view.findViewById(R.id.set_email);
        password = view.findViewById(R.id.set_password);
        repeatPassword = view.findViewById(R.id.set_Repeat_password);
        id_view = (TextView) view.findViewById(R.id.textView2);


        //create on click listener for create button
        c_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialise an object for the UserModel class.
                UserModel userModel = null;
                //check if the user input sections are blank, if so toast error message.
                if (username.getText().toString().equals("")||email.getText().toString().equals("")||password.getText().toString().equals("")||repeatPassword.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please complete all boxes", Toast.LENGTH_LONG).show();

                }else {
                    //Check if the user has repeated their password correctly.
                    if (password.getText().toString().equals(repeatPassword.getText().toString())){

                        //Call the checkEmail() method from the database helper
                        // class and store results in a boolean variable.
                        Boolean checkEmail = db.checkEmail(email.getText().toString());
                        //Check if the email is already on the database.
                        if (checkEmail == true){
                            //if the user email is not already on the database do this.
                            try {
                                //Pass the user provided input data into the the UserModel class object.
                                userModel = new UserModel(-1, username.getText().toString(), email.getText().toString(), password.getText().toString());

                            }catch (Exception e){
                                //Catch any exceptions by toasting this message.
                                Toast.makeText(getActivity(), "Please complete all boxes", Toast.LENGTH_LONG).show();
                            }
                            try {
                                //Inside of the try catch use the addUser method from the
                                // database helper class to create the account
                                // and pass the usermodel object.
                                db.addUser(userModel);
                                Toast.makeText(getActivity(), "User: "+ username.getText().toString() + " Has been created" , Toast.LENGTH_SHORT).show();
                                //Replace the current fragment with ProfileCompletionFragment.
                                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.login_fragment_container, new ProfileCompletionFragment()).commit();
                                //close the database instance.
                                db.close();

                            }catch (Exception e){
                                //catch any exceptions by toasting this message.
                                Toast.makeText(getActivity(), "ERROR: Account not created", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            //toast this if the email already exists on the database.
                            Toast.makeText(getActivity(), "Email already exists", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        //toast this if the password is not repeated correctly.
                        Toast.makeText(getActivity(), "Passwords do not match ", Toast.LENGTH_SHORT).show();
                    }


                    //create a shared preference to store the last created user’s ID.
                    SharedPreferences idPrefs = getActivity().getSharedPreferences("LAST_CREATED_USER_ID", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = idPrefs.edit();
                    editor.putString("LastCreatedUserID", db.findID());
                    editor.commit();
                    //close the database instance.
                    db.close();

                }
            }
        });

        //set an onclick listener for the back button.
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //replace the current fragment with.
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.login_fragment_container, new WelcomeFragment()).commit();
            }
        });







        return view;
    }
}
