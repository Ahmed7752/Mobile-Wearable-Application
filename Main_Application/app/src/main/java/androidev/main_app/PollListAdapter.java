package androidev.main_app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

//create a custom ListView adapter. using the array list containing the PollModel objects.
public class PollListAdapter extends ArrayAdapter<PollModel> {

    private Context mContext;
    int mResource;



    public PollListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PollModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get the the title (string) and topic from the array list object. To then be store
        // in string variables.
        String title = getItem(position).getTitle();
        String topic = getItem(position).getTopic();
        int id = getItem(position).getPollID();
        //inflate the custom adaptor into this view
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //convertView is used to reuse old view.
        convertView = inflater.inflate(mResource, parent, false);

        //find the textViews from the layout file
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
        //set the textView using the string variable title and topic from the array list.
        pollTitle.setText(title);
        pollTopic.setText(topic);

        return convertView;
    }
}
