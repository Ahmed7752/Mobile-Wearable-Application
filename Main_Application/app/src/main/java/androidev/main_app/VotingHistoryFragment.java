//This fragment class is used to display a users voting history.
package androidev.main_app;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class VotingHistoryFragment extends Fragment {

    //The required variables are declared here.
    private String topic_str,id_str,poll_id_str,vote_str,poll_title_str, politics_str = "Politics",sports_str = "Sports",celeb_str = "Celebrities",movies_str = "Movies";
    private ListView politics_list, sports_list, celeb_list, movies_list;
    private TextView  politics_text, sports_text, celeb_text, movies_text;

    //The setNestedScrollingEnabled property requires level 21 API to function.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The home fragment layout file is inflated into the view.
        View view = inflater.inflate(R.layout.fragment_voting_history, container, false);

        //Constructor is used to create an object for the database helper class.
        DataBaseHelper db = new DataBaseHelper(getActivity());

        //The required shared preferences are called.
        SharedPreferences topicPrefs = getActivity().getSharedPreferences("CURRENT_TOPIC", 0);
        SharedPreferences PollIDPrefs = getActivity().getSharedPreferences("CURRENT_POLL_ID", 0);
        SharedPreferences PollVotePrefs = getActivity().getSharedPreferences("CURRENT_POLL_VOTE", 0);
        SharedPreferences pollTitlePref = getActivity().getSharedPreferences("CURRENT_POLL_TITLE", 0);
        SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_USER_ID", 0);

        //The value inside of the shared preferences are stored as  string variables
        if (topicPrefs.contains("currentTopic")) {
            topic_str = topicPrefs.getString("currentTopic", "topic");
        }else {
            topic_str = "";
        }
        if (PollIDPrefs.contains("currentPollID")) {
            poll_id_str = PollIDPrefs.getString("currentPollID", "id");
        }
        if (PollVotePrefs.contains("currentPollVote")) {
            vote_str = PollVotePrefs.getString("currentPollVote", "vote");
        }
        if (pollTitlePref.contains("currentPollTitle")) {
            poll_title_str = pollTitlePref.getString("currentPollTitle", "title");
        }
        if (userPrefs.contains("currentUserID")) {
            id_str = userPrefs.getString("currentUserID", "id");
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

        //The required object lists are initialised using methods from the database helper class.
        //Each list requires different information from the database.
        List<ResultsModel> politicsModel = db.getResultsPollInterest(politics_str,id_str);
        List<ResultsModel> sportsModel = db.getResultsPollInterest(sports_str,id_str);
        List<ResultsModel> celebModel = db.getResultsPollInterest(celeb_str,id_str);
        List<ResultsModel> moviesModel = db.getResultsPollInterest(movies_str,id_str);

        //If the list is empty then this message is toasted to the user.
        if (politicsModel.isEmpty()){
            politics_text.setText("You have not completed any polls on this topic.");

        }else {
            //A constructor is used to create an object for the custom list adapter is
            // created and the correct arguments are
            // passed such as the layout file and the array list containing data from the database.
            ResultsListAdapter politicsAdapter = new ResultsListAdapter(getActivity(),
                    R.layout.results_list_adapter_layout, (ArrayList<ResultsModel>) politicsModel);
            politics_list.setAdapter(politicsAdapter);
        }
        //If the list is empty then this message is toasted to the user.
        if (sportsModel.isEmpty()){
            sports_text.setText("You have not completed any polls on this topic.");

        }else {
            //A constructor is used to create an object for the custom list adapter is
            // created and the correct arguments are
            // passed such as the layout file and the array list containing data from the database.
            ResultsListAdapter sportsAdapter = new ResultsListAdapter(getActivity(),
                    R.layout.results_list_adapter_layout, (ArrayList<ResultsModel>) sportsModel);
            sports_list.setAdapter(sportsAdapter);

        }
        //If the list is empty then this message is toasted to the user.
        if (celebModel.isEmpty()){
            celeb_text.setText("You have not completed any polls on this topic.");

        }else {
            //A constructor is used to create an object for the custom list adapter is
            // created and the correct arguments are
            // passed such as the layout file and the array list containing data from the database.
            ResultsListAdapter celebAdapter = new ResultsListAdapter(getActivity(),
                    R.layout.results_list_adapter_layout, (ArrayList<ResultsModel>) celebModel);
            celeb_list.setAdapter(celebAdapter);

        }
        //If the list is empty then this message is toasted to the user.
        if (moviesModel.isEmpty()){
            movies_text.setText("You have not completed any polls on this topic.");

        }else {
            //A constructor is used to create an object for the custom list adapter
            // is created and the correct arguments are
            // passed such as the layout file and the array list containing data from the database.
            ResultsListAdapter moviesAdapter = new ResultsListAdapter(getActivity(),
                    R.layout.results_list_adapter_layout, (ArrayList<ResultsModel>) moviesModel);
            movies_list.setAdapter(moviesAdapter);

        }

        return view;
    }
}
