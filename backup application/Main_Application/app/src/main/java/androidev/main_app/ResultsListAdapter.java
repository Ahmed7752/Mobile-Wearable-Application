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


        String title = getItem(position).getPollTitle();
        String topic = getItem(position).getTopic();
        String vote = getItem(position).getVote();
        String postcode = getItem(position).getPostcode();
        int id = getItem(position).getResultID();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView pollTitle = (TextView) convertView.findViewById(R.id.textView);
        TextView pollTopic = (TextView) convertView.findViewById(R.id.textView2);

        pollTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences PollIDPrefs = getContext().getSharedPreferences("CURRENT_POLL_ID", 0);
                SharedPreferences.Editor idEditor = PollIDPrefs.edit();
                idEditor.putString("currentPollID", String.valueOf(id));
                idEditor.commit();

                SharedPreferences PollVotePrefs = getContext().getSharedPreferences("CURRENT_POLL_VOTE", 0);
                SharedPreferences.Editor voteEditor = PollVotePrefs.edit();
                voteEditor.putString("currentPollVote", vote);
                voteEditor.commit();

                SharedPreferences topicPrefs = getContext().getSharedPreferences("CURRENT_POLL_TOPIC", 0);
                SharedPreferences.Editor topicEditor = topicPrefs.edit();
                topicEditor.putString("currentPollTopic", topic);
                topicEditor.commit();

                SharedPreferences pollTitlePref = getContext().getSharedPreferences("CURRENT_POLL_TITLE", 0);
                SharedPreferences.Editor titleEditor = pollTitlePref.edit();
                titleEditor.putString("currentPollTitle", title);
                titleEditor.commit();

                VoteActivity.results_list.clear();

                Intent intent = new Intent(getContext(), PollActivity.class);
                mContext.startActivity(intent);
            }
        });

        pollTitle.setText(title);
        pollTopic.setText(vote);

        return convertView;
    }
}
