//This fragment class is used to display user details.
package androidev.main_app;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserDetailsFragment extends Fragment {

    //The required variables are declared here.
    private String id_str;
    private TextView fname_text,lname_text,age_text,postcode_text, phoneNumber_text,interests_text,Political_text,Ethnicity_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The home fragment layout file is inflated into the view.
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);

        //Variables are matched with elements from the layout file.
        fname_text = view.findViewById(R.id.input_fname);
        lname_text = view.findViewById(R.id.input_lname);
        age_text = view.findViewById(R.id.input_age);
        postcode_text = view.findViewById(R.id.input_postcode);
        phoneNumber_text = view.findViewById(R.id.input_phoneNumber);
        interests_text = view.findViewById(R.id.input_interests);
        Political_text = view.findViewById(R.id.input_Political);
        Ethnicity_text = view.findViewById(R.id.input_Ethnicity);

        //Constructor is used to create an object for the database helper class.
        DataBaseHelper db = new DataBaseHelper(getActivity());
        //The required shared preferences are called.
        SharedPreferences userPrefs = (getActivity()).getSharedPreferences("CURRENT_USER_ID", 0);
        //The value inside of the shared preference is stored as a string variable
        if (userPrefs.contains("currentUserID")) {
            id_str = userPrefs.getString("currentUserID", "id");
        }

        //The getUserDetails method from the database helper class is used to build a list
        // of objects containing user details, using the ProfileCompletionModel class,
        // when given the users id
        List<ProfileCompletionModel> models = db.getUserDetails(id_str);

        //If the List containing objects is empty the text view is set to
        // display this message.
        if (models.isEmpty()){
            Toast.makeText(getActivity(), "Error: No user details", Toast.LENGTH_LONG).show();

        }else {

            //The object in the list contains all the details for a single user.
            //Because there's only one object in the list it will be at index 0.
            //Using the get method on the list, the objects at index 0 is stored in the user variable.
            ProfileCompletionModel user = models.get(0);

            //Using the get method from the ProfileCompletionModel class all of the
            // required data is retrieved and stored in string variables.
            String fName = user.getFname();
            String lName = user.getLname();
            String age = user.getAge();
            String postcode = user.getPostcode();
            String phoneNumber = user.getPhoneNumber();
            String interests = user.getInterests();
            String political = user.getPoliticalInterests();
            String ethnicity = user.getEthnicity();

            //All the text views are set.
            fname_text.setText(fName);
            lname_text.setText(lName);
            age_text.setText(String.valueOf(age));
            postcode_text.setText(postcode);
            phoneNumber_text.setText(String.valueOf(phoneNumber));
            interests_text.setText(interests);
            Political_text.setText(political);
            Ethnicity_text.setText(ethnicity);
        }
        return view;
    }
}
