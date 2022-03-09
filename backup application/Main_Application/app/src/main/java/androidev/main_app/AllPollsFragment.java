//This fragment class is used to display all of the polls from the database into scrollable lists
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

public class AllPollsFragment extends Fragment {
    //All required variables are declared
    private String topic_str, politics_str = "Politics",sports_str = "Sports",celeb_str = "Celebrities",movies_str = "Movies";
    private ListView politics_list, sports_list, celeb_list, movies_list;
    private TextView  politics_text, sports_text, celeb_text, movies_text;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The view is inflated with the layout for this fragment.
        View view = inflater.inflate(R.layout.fragment_all_polls, container, false);
        //A constructor is created for the database helper class.
        DataBaseHelper db = new DataBaseHelper(getActivity());
        //The required shared preferences are called
        SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_TOPIC", 0);
        //The values from the shared preference is extracted and stored into a variable string.
        if (userPrefs.contains("currentTopic")) {
            topic_str = userPrefs.getString("currentTopic", "topic");
        }else {
            topic_str = "";
        }
        
        //The list variables are matched with the list elements from the layout file.
        politics_list = view.findViewById(R.id.politics_list_view);
        sports_list = view.findViewById(R.id.sports_list_view);
        celeb_list = view.findViewById(R.id.celeb_list_view);
        movies_list = view.findViewById(R.id.movies_list_view);
        //The text variables are matched with the text elements from the layout file.
        politics_text = view.findViewById(R.id.politics_polls_text_view);
        sports_text = view.findViewById(R.id.sports_polls_text_view);
        celeb_text = view.findViewById(R.id.celeb_polls_text_view);
        movies_text = view.findViewById(R.id.movies_polls_text_view);
        //The required object lists are initialised using methods from the database help a class.
        // Each list requires different information from the database.
        List<PollModel> politicsModel = db.getAllPollInterest(politics_str);
        List<PollModel> sportsModel = db.getAllPollInterest(sports_str);
        List<PollModel> celebModel = db.getAllPollInterest(celeb_str);
        List<PollModel> moviesModel = db.getAllPollInterest(movies_str);

        //If the list is empty then this message is toasted to the user.
        if (politicsModel.isEmpty()){
            politics_text.setText("There are no polls available right now, please check back later.");

        }else {
            //A constructor for the custom list adapter is created and the correct arguments are
            // passed such as the layout file and the array list containing data from the database.
            PollListAdapter politicsAdapter = new PollListAdapter(getActivity(), R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) politicsModel);
            politics_list.setAdapter(politicsAdapter);
        }
        //If the list is empty then this message is toasted to the user.
        if (sportsModel.isEmpty()){
            sports_text.setText("There are no polls available right now, please check back later.");

        }else {
            //A constructor for the custom list adapter is created and the correct arguments are
            // passed such as the layout file and the array list containing data from the database.
            PollListAdapter sportsAdapter = new PollListAdapter(getActivity(), R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) sportsModel);
            sports_list.setAdapter(sportsAdapter);

        }
        //If the list is empty then this message is toasted to the user.
        if (celebModel.isEmpty()){
            celeb_text.setText("There are no polls available right now, please check back later.");

        }else {
            //A constructor for the custom list adapter is created and the correct arguments are
            // passed such as the layout file and the array list containing data from the database.
            PollListAdapter celebAdapter = new PollListAdapter(getActivity(), R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) celebModel);
            celeb_list.setAdapter(celebAdapter);

        }
        //If the list is empty then this message is toasted to the user.
        if (moviesModel.isEmpty()){
            movies_text.setText("There are no polls available right now, please check back later.");

        }else {
            //A constructor for the custom list adapter is created and the correct arguments are
            // passed such as the layout file and the array list containing data from the database.
            PollListAdapter moviesAdapter = new PollListAdapter(getActivity(), R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) moviesModel);
            movies_list.setAdapter(moviesAdapter);

        }

        return view;
    }
}
