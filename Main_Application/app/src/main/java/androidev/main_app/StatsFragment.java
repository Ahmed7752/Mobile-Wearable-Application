//This fragment class is used to display completed polls.
package androidev.main_app;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {
    //The required variables are declared here.
    private String topic_str,interest_str,id_str, politics_str = "Politics",sports_str = "Sports",celeb_str = "Celebrities",movies_str = "Movies";
    private ListView interest_results_list, completed_poll_list;
    private TextView  completed_polls_text, interest_results_text, completed_polls_error_text, live_polls_error_text;

    //The setNestedScrollingEnabled property requires level 21 API to function.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The home fragment layout file is inflated into the view.
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        //Constructor is used to create an object for the database helper class.
        DataBaseHelper db = new DataBaseHelper(getActivity());
        //The required shared preferences are called.
        SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_USER_ID", 0);
        //The value inside of the shared preference is stored as a string variable
        if (userPrefs.contains("currentUserID")) {
            id_str = userPrefs.getString("currentUserID", "id");
        }


        //Variables are matched with elements from the layout file.
        interest_results_list = view.findViewById(R.id.interest_results_list_view);
        completed_poll_list = view.findViewById(R.id.completed_polls_list_view);
        completed_polls_text = view.findViewById(R.id.completed_polls_text_view);
        interest_results_text = view.findViewById(R.id.interest_results_text_view);
        completed_polls_error_text = view.findViewById(R.id.completed_polls__error_text_view);
        live_polls_error_text = view.findViewById(R.id.live_polls_error_text_view);

        //Using the method findinterests from the database helper class the
        // interest of the current user is  extracted from the database
        // and stored as a string variable
        interest_str = db.findInterests(id_str);

        //The getAllPollInterest method from the database helper class is used to build a list
        // containing poll objects using the poll model class, when given the users interests
        List<ResultsModel> models = db.getResultsPollInterest(interest_str,id_str);
        List<PollModel> pollModels = db.getCompletedPolls(id_str);
        //If the List containing objects is empty the text view is set to
        // display this message, the list view element is no longer scrollable.
        if (models.isEmpty()){
            //set textView to show the current your's interest topic.
            live_polls_error_text.setText("You have not completed any polls on your " +
                    "interest topic." +"(" +interest_str+")" );
            interest_results_list.setNestedScrollingEnabled(false);

        }else {
            //A contractor is created for the custom list adapter, it is given the
            // custom list adapter layout file and the array list containing poll
            // objects retrieved from the database.
            ResultsListAdapter pollsAdapter = new ResultsListAdapter(getActivity(),
                    R.layout.results_list_adapter_layout, (ArrayList<ResultsModel>) models);
            interest_results_list.setAdapter(pollsAdapter);
        }

        //If the List containing objects is empty the text view is set to
        // display this message, the list view element is no longer scrollable.
        if (models.isEmpty()){
            completed_polls_error_text.setText("You have not saved any polls after completing a poll.");
            completed_poll_list.setNestedScrollingEnabled(false);

        }else {
            //A contractor is created for the custom list adapter, it is given the
            // custom list adapter layout file and the array list containing poll
            // objects retrieved from the database.
            PollListAdapter pollsAdapter = new PollListAdapter(getActivity(),
                    R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) pollModels);
            completed_poll_list.setAdapter(pollsAdapter);
        }




        return view;
    }
}
