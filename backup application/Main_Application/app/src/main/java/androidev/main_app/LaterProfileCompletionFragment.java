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
import java.util.Objects;


public class LaterProfileCompletionFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Button comp_btn;
    private ImageButton back_btn;
    private String id_str, interests, politicalInterests, ethnicity;
    private EditText fname,lname,age,postcode, phoneNumber;
    private TextView textView;

    ArrayList<String> selected = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_later_profile_completion, container, false);


        Spinner interestSpinner = view.findViewById(R.id.Interest_spinner);
        ArrayAdapter<CharSequence> interestAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.Interest_array, android.R.layout.simple_spinner_item);
        interestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestSpinner.setAdapter(interestAdapter);
        interestSpinner.setOnItemSelectedListener(this);

        Spinner ethnicitySpinner = view.findViewById(R.id.Ethnicity_spinner);
        ArrayAdapter<CharSequence> ethnicityAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.Ethnicity_array, android.R.layout.simple_spinner_item);
        ethnicityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ethnicitySpinner.setAdapter(ethnicityAdapter);
        ethnicitySpinner.setOnItemSelectedListener(this);

        Spinner politicalSpinner = view.findViewById(R.id.Political_spinner);
        ArrayAdapter<CharSequence> politicalAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.Political_array, android.R.layout.simple_spinner_item);
        politicalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        politicalSpinner.setAdapter(politicalAdapter);
        politicalSpinner.setOnItemSelectedListener(this);



        textView = view.findViewById(R.id.textView);

        comp_btn = view.findViewById(R.id.complete_btn);
        back_btn = view.findViewById(R.id.Back_btn);
        fname = view.findViewById(R.id.input_fname);
        lname = view.findViewById(R.id.input_lname);
        age = view.findViewById(R.id.input_age);
        postcode = view.findViewById(R.id.input_postcode);
        phoneNumber = view.findViewById(R.id.input_phoneNumber);

        DataBaseHelper db = new DataBaseHelper(getActivity());
        SharedPreferences userIDPrefs = Objects.requireNonNull(getActivity()).getSharedPreferences("CURRENT_USER_ID", 0);

        if (userIDPrefs.contains("currentUserID")) {
            id_str = userIDPrefs.getString("currentUserID", "id");
        }

        comp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fname.getText().toString().equals("")||lname.getText().toString().equals("")||age.getText().toString().equals("")||postcode.getText().toString().equals("")||phoneNumber.getText().toString().equals("")||selected.contains("Select")) {

                    Toast.makeText(getActivity(), "Please complete all boxes", Toast.LENGTH_LONG).show();

                }else {
                    try {

                        DatabaseRunnable runnable = new DatabaseRunnable();
                        new Thread(runnable).start();


                        SharedPreferences.Editor editor = userIDPrefs.edit();
                        editor.putString("currentUserID", id_str);
                        editor.commit();

                        Toast.makeText(getActivity(), "Your profile is now COMPLETE", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        db.close();

                    } catch (Exception e) {

                    }
                }



            }

        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsComplete isComplete = new IsComplete(false);
                db.complete(isComplete,id_str);

                SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_USER", 0);
                SharedPreferences.Editor editor = userPrefs.edit();
                editor.putString("currentUser", id_str);
                editor.commit();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment()).commit();
                db.close();


            }
        });

        return view;
    }

    class DatabaseRunnable implements Runnable {

        @Override
        public void run() {
            DataBaseHelper db = new DataBaseHelper(getActivity());

            ProfileCompletionModel proCompModel = new ProfileCompletionModel(age.getText().toString(), phoneNumber.getText().toString(), fname.getText().toString(), lname.getText().toString(), postcode.getText().toString(), interests, politicalInterests, ethnicity);
            IsComplete isComplete = new IsComplete(true);
            db.completeProfile(proCompModel, isComplete, id_str);


            db.close();

        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        TextView hint = (TextView) view;
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.Interest_spinner) {

            if (position == 0){
                hint.setTextColor(Color.GRAY);
                selected.add(parent.getItemAtPosition(position).toString());
            }else {
                selected.clear();
            }
            interests = parent.getItemAtPosition(position).toString();

        }else if(spinner.getId() == R.id.Political_spinner) {

            if (position == 0){
                hint.setTextColor(Color.GRAY);
                selected.add(parent.getItemAtPosition(position).toString());
            }else {
                selected.clear();
            }
            politicalInterests = parent.getItemAtPosition(position).toString();

        }else if(spinner.getId() == R.id.Ethnicity_spinner) {

            if (position == 0){
                hint.setTextColor(Color.GRAY);
                selected.add(parent.getItemAtPosition(position).toString());
            }else {
                selected.clear();
            }
            ethnicity = parent.getItemAtPosition(position).toString();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
