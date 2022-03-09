//This fragment class is used to display home screen elements.
package androidev.main_app;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    //The required variables are declared here.
    private String id_str, interest_str;
    private ImageButton politics_btn, sports_btn, celeb_btn, movies_btn;
    private ListView daily_list;
    private TextView daily_text;

    //The setNestedScrollingEnabled property requires level 21 API to function.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The home fragment layout file is inflated into the view.
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Constructor is used to create an object for the database helper class.
        DataBaseHelper db = new DataBaseHelper(getActivity());
        //The required shared preference are called.
        SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_USER_ID", 0);
        //The value inside of the shared preference is stored as a string variable
        if (userPrefs.contains("currentUserID")) {
            id_str = userPrefs.getString("currentUserID", "id");
        }

        //Variables are matched with elements from the layout file.
        daily_list = view.findViewById(R.id.daily_list_view);
        daily_text = view.findViewById(R.id.daily_polls_text_view);
        politics_btn = view.findViewById(R.id.politics_image_btn);
        sports_btn = view.findViewById(R.id.sports_image_btn);
        celeb_btn = view.findViewById(R.id.celeb_image_btn);
        movies_btn = view.findViewById(R.id.movies_image_btn);

        //Using the method findinterests from the database helper class the
        // interest of the current user is  extracted from the database
        // and stored as a string variable
        interest_str = db.findInterests(id_str);

        //The method checkComplete is called from the database helper class  to check if the user
        //has completed setting up their account.
        if (db.checkComplete(id_str) == true) {
            //The getAllRequestedPolls method from the database helper class is used to build a list
            // containing poll objects using the poll model class, when given the users interests
            // and the frequency type of the polls.
            List<PollModel> models = db.getAllRequestedPolls(interest_str,"daily");

            //If the List containing objects is empty the text view is set to
            // display this message, the list view element is no longer scrollable.
            if (models.isEmpty()){
                daily_text.setText("There are no poll suggestions for today, please check back tomorrow.");
                daily_list.setNestedScrollingEnabled(false);

            }else {
                //A contractor is used to  created an object for the custom list adapter, it is given the
                // custom list adapter layout file and the array list containing poll
                // objects retrieved from the database.
                PollListAdapter pollsAdapter = new PollListAdapter(getActivity(), R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) models);
                daily_list.setAdapter(pollsAdapter);
            }
        }else{
            //If the user has not completed setting up their account this text view is set with
            //this message
            daily_text.setText("You need to finish setting up your profile before we can suggest daily polls");
            daily_list.setNestedScrollingEnabled(false);
        }

        //An on click listener is set on the clear politics image button.
        politics_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The current fragment is replaced with the PoliticalPollsFragment.
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new PoliticalPollsFragment()).commit();
            }
        });
        //An on click listener is set on the clear sports image button.
        sports_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The current fragment is replaced with the SportsPollsFragment.
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new SportsPollsFragment()).commit();
            }
        });
        //An on click listener is set on the clear celebs image button.
        celeb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The current fragment is replaced with the CelebPollsFragment.
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new CelebPollsFragment()).commit();
            }
        });
        //An on click listener is set on the clear movies image button.
        movies_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The current fragment is replaced with the MoviePollsFragment.
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new MoviePollsFragment()).commit();
            }
        });


        return view;
    }
}
