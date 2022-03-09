package androidev.main_app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

//create a custom ListView adapter. using the array list containing the ResultsModel objects.
public class ResultsListAdapter extends ArrayAdapter<ResultsModel> {

    private Context mContext;
    int mResource;



    public ResultsListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ResultsModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get the the title (string),topic (string),vote (string),postcode (string) and id (int)
        // from the array list object. To then be store as variables.
        String title = getItem(position).getPollTitle();
        String topic = getItem(position).getTopic();
        String vote = getItem(position).getVote();
        String postcode = getItem(position).getPostcode();
        int id = getItem(position).getResultID();
        //inflate the custom adaptor
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //convertView is used to reuse old view.
        convertView = inflater.inflate(mResource, parent, false);

        //find the textView from the layout file
        TextView pollTitle = (TextView) convertView.findViewById(R.id.textView);
        TextView pollTopic = (TextView) convertView.findViewById(R.id.textView2);

        //create on click listener for the poll title textview
        pollTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create new Shared Preferences
                SharedPreferences PollIDPrefs = getContext().getSharedPreferences("CURRENT_POLL_ID", 0);
                SharedPreferences.Editor idEditor = PollIDPrefs.edit();
                idEditor.putString("currentPollID", String.valueOf(id));
                idEditor.commit();

                //create new Shared Preferences
                SharedPreferences PollVotePrefs = getContext().getSharedPreferences("CURRENT_POLL_VOTE", 0);
                SharedPreferences.Editor voteEditor = PollVotePrefs.edit();
                voteEditor.putString("currentPollVote", vote);
                voteEditor.commit();

                //create new Shared Preferences
                SharedPreferences topicPrefs = getContext().getSharedPreferences("CURRENT_POLL_TOPIC", 0);
                SharedPreferences.Editor topicEditor = topicPrefs.edit();
                topicEditor.putString("currentPollTopic", topic);
                topicEditor.commit();

                //create new Shared Preferences
                SharedPreferences pollTitlePref = getContext().getSharedPreferences("CURRENT_POLL_TITLE", 0);
                SharedPreferences.Editor titleEditor = pollTitlePref.edit();
                titleEditor.putString("currentPollTitle", title);
                titleEditor.commit();

                //Clear the public results_list from the VoteActivity class
                VoteActivity.results_list.clear();

                //This intent will start the PollActivity
                Intent intent = new Intent(getContext(), PollActivity.class);
                mContext.startActivity(intent);
            }
        });

        //set textViews using the string variable title and vote from the array list.
        pollTitle.setText(title);
        pollTopic.setText(vote);

        return convertView;
    }
}
