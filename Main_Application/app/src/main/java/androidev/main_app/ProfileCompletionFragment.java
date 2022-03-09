package androidev.main_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProfileCompletionFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Button comp_btn;
    private ImageButton skip_btn;
    private String id_str, interests, politicalInterests, ethnicity;

    private EditText fname,lname,age,postcode, phoneNumber;
    private TextView textView;
    //create array list for selected option from the spinners.
    ArrayList<String> selected = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_completion, container, false);

        // Find the spinner element in the layout file (fragment_profile_completion).
        Spinner interestSpinner = view.findViewById(R.id.Interest_spinner);
        //use the string array (Interest_array) in the String.xml file as a resource for the array adapter, use a default spinner list adapter.
        ArrayAdapter<CharSequence> interestAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.Interest_array, android.R.layout.simple_spinner_item);
        //The array adapter is set to be a dropdown view, a default resource layout is given
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //The array adapter is set on the spinner
        interestSpinner.setAdapter(interestAdapter);
        //A listener is set on the spinner
        interestSpinner.setOnItemSelectedListener(this);

        // Find the spinner element in the layout file (fragment_profile_completion).
        Spinner ethnicitySpinner = view.findViewById(R.id.Ethnicity_spinner);
        //use the string array (Ethnicity_array) in the String.xml file as a resource for the array adapter, use a default spinner list adapter.
        ArrayAdapter<CharSequence> ethnicityAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.Ethnicity_array, android.R.layout.simple_spinner_item);
        ethnicityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ethnicitySpinner.setAdapter(ethnicityAdapter);
        ethnicitySpinner.setOnItemSelectedListener(this);

        // Find the spinner element in the layout file (fragment_profile_completion).
        Spinner politicalSpinner = view.findViewById(R.id.Political_spinner);
        //use the string array (Political_array) in the String.xml file as a resource for the array adapter, use a default spinner list adapter.
        ArrayAdapter<CharSequence> politicalAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.Political_array, android.R.layout.simple_spinner_item);
        politicalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        politicalSpinner.setAdapter(politicalAdapter);
        politicalSpinner.setOnItemSelectedListener(this);

        //find and match variables to elements in the layout file.
        textView = view.findViewById(R.id.textView);
        comp_btn = view.findViewById(R.id.complete_btn);
        skip_btn = view.findViewById(R.id.Skip_btn);
        fname = view.findViewById(R.id.input_fname);
        lname = view.findViewById(R.id.input_lname);
        age = view.findViewById(R.id.input_age);
        postcode = view.findViewById(R.id.input_postcode);
        phoneNumber = view.findViewById(R.id.input_phoneNumber);
        //create constructor for the DataBase Helper class.
        DataBaseHelper db = new DataBaseHelper(getActivity());
        //get shared preference.
        SharedPreferences idPrefs = getActivity().getSharedPreferences("LAST_CREATED_USER_ID", MODE_PRIVATE);
        //check if a preference contains and a certain key, then store the value as a variable.
        if (idPrefs.contains("LastCreatedUserID")) {
            id_str = idPrefs.getString("LastCreatedUserID", "id");
        }

        //create on click listener for complete button
        comp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if the user input sections are blank, if so toast error message.
                if (fname.getText().toString().equals("")||lname.getText().toString().equals("")||age.getText().toString().equals("")||postcode.getText().toString().equals("")||phoneNumber.getText().toString().equals("")||selected.contains("Select")) {

                    Toast.makeText(getActivity(), "Please complete all boxes", Toast.LENGTH_LONG).show();

                }else {
                    try {
                        //create a runnable object.
                        DatabaseRunnable runnable = new DatabaseRunnable();
                        //pass the object into a new threat and start it.
                        new Thread(runnable).start();

                        //update the CURRENT_USER_ID Shared Preference.
                        SharedPreferences userIDPrefs = getActivity().getSharedPreferences("CURRENT_USER_ID", 0);
                        SharedPreferences.Editor editor = userIDPrefs.edit();
                        editor.putString("currentUserID", id_str);
                        editor.commit();
                        //using intent to current activity to Main Activity.
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getActivity(), "Your profile is now COMPLETE", Toast.LENGTH_LONG).show();

                    } catch (Exception e) {

                    }
                }



            }

        });
        //create on click listener for skip button
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a constructor and an object instance for the IsComplete class
                IsComplete isComplete = new IsComplete(false);
                //use the complete method in the database helper class to update database.
                db.complete(isComplete,id_str);

                //update the CURRENT_USER_ID Shared Preference.
                SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_USER_ID", 0);
                SharedPreferences.Editor editor = userPrefs.edit();
                editor.putString("currentUserID", id_str);
                editor.commit();

                //using intent to current activity to Main Activity.
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                //close the database instance
                db.close();
                getActivity().finish();

            }
        });

        return view;
    }
    //implement the runnable interface.
    class DatabaseRunnable implements Runnable {

        @Override
        public void run() {
            //create constructor for the DataBase Helper class.
            DataBaseHelper db = new DataBaseHelper(getActivity());

            //create a constructor and and object instance using the ProfileCompletionModel class.
            ProfileCompletionModel proCompModel = new ProfileCompletionModel(age.getText().toString(),
                    phoneNumber.getText().toString(), fname.getText().toString(), lname.getText().toString(),
                    postcode.getText().toString(), interests, politicalInterests, ethnicity);
            //create a constructor and an object instance for the IsComplete class
            IsComplete isComplete = new IsComplete(true);
            //use the completeProfile method from the database helper class.
            db.completeProfile(proCompModel, isComplete, id_str);


            db.close();

        }
    }


    //create and find and store the id and string values of the selected items from the spinner arrays.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        TextView hint = (TextView) view; 
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.Interest_spinner) {
            //if the selected position is 0 get the text view (hint to grey).
            if (position == 0){
                hint.setTextColor(Color.GRAY);
                //add the selected option to the created array list (selected).
                selected.add(parent.getItemAtPosition(position).toString());
            }else {
                //if current position is not 0 still remove the first time in the array list (index 0).
                selected.remove(0);
            }
            //from the AdapterView get the selected position and set the vault to string.
            interests = parent.getItemAtPosition(position).toString();

        }else if(spinner.getId() == R.id.Political_spinner) {
            //if the selected position is 0 get the text view (hint to grey).
            if (position == 0){
                hint.setTextColor(Color.GRAY);
                //add the selected option to the created array list (selected).
                selected.add(parent.getItemAtPosition(position).toString());
            }else {
                //if current position is not 0 still remove the first time in the array list (index 0).
                selected.remove(0);
            }
            //from the AdapterView get the selected position and set the vault to string.
            politicalInterests = parent.getItemAtPosition(position).toString();

        }else if(spinner.getId() == R.id.Ethnicity_spinner) {
            //if the selected position is 0 get the text view (hint to grey).
            if (position == 0){
                hint.setTextColor(Color.GRAY);
                //add the selected option to the created array list (selected).
                selected.add(parent.getItemAtPosition(position).toString());
            }else {
                //If the selected list is NOT empty remove vault at index 0 in list.
                if (!selected.isEmpty()) {
                    //if current position is not 0 and the selected list is not empty
                    // remove the first time in the array list (index 0).
                    selected.remove(0);
                }
            }
            //from the AdapterView get the selected position and set the vault to string.
            ethnicity = parent.getItemAtPosition(position).toString();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
