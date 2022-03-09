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

    private Button comp_btn;
    private String id_str;

    private TextView textView,fname_text,lname_text,age_text,postcode_text, phoneNumber_text,interests_text,Political_text,Ethnicity_text;

    ArrayList<String> selected = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_details, container, false);


        textView = view.findViewById(R.id.textView);

        fname_text = view.findViewById(R.id.input_fname);
        lname_text = view.findViewById(R.id.input_lname);
        age_text = view.findViewById(R.id.input_age);
        postcode_text = view.findViewById(R.id.input_postcode);
        phoneNumber_text = view.findViewById(R.id.input_phoneNumber);
        interests_text = view.findViewById(R.id.input_interests);
        Political_text = view.findViewById(R.id.input_Political);
        Ethnicity_text = view.findViewById(R.id.input_Ethnicity);


        DataBaseHelper db = new DataBaseHelper(getActivity());
        SharedPreferences userPrefs = (getActivity()).getSharedPreferences("CURRENT_USER_ID", 0);

        if (userPrefs.contains("currentUserID")) {
            id_str = userPrefs.getString("currentUserID", "id");
        }

        List<ProfileCompletionModel> models = db.getUserDetails(id_str);

        if (models.isEmpty()){
            Toast.makeText(getActivity(), "Error: No user details", Toast.LENGTH_LONG).show();

        }else {
            ProfileCompletionModel user = models.get(0);

            String fName = user.getFname();
            String lName = user.getLname();
            String age = user.getAge();
            String postcode = user.getPostcode();
            String phoneNumber = user.getPhoneNumber();
            String interests = user.getInterests();
            String political = user.getPoliticalInterests();
            String ethnicity = user.getEthnicity();

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
