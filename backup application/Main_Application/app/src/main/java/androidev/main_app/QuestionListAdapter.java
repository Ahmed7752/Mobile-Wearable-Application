package androidev.main_app;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

//create a custom ListView adapter. using the array list containing the QuestionsModel objects.
public class QuestionListAdapter extends ArrayAdapter<QuestionsModel> {
    //initialise variables.
    private Context mContext;
    int mResource;
    private int completedQuestionID;
    private ImageView checkedTick;





    public QuestionListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<QuestionsModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get the the title (string) and poll id from the array list object. To then be store as variables.
        String title = getItem(position).getTitle();
        int questionID = getItem(position).getQuestionID();
        int pollID = getItem(position).getPollID();
        //inflate the custom adaptor
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //convertView is used to reuse old view.
        convertView = inflater.inflate(mResource, parent, false);

        checkedTick = convertView.findViewById(R.id.checked_tick);
        checkedTick.setVisibility(View.INVISIBLE);
        //find the textView from the layout file
        TextView questionTitle = convertView.findViewById(R.id.question_title_text_view);

        SharedPreferences completedIDPrefs = mContext.getSharedPreferences("COMPLETED_QUESTION_ID", 0);

        if (completedIDPrefs.contains("completedQuestionID")) {
            completedQuestionID = Integer.parseInt(completedIDPrefs.getString("completedQuestionID", "id"));
        }

        if (VoteActivity.votes_list.contains(questionID)){
            for(Integer vote : VoteActivity.votes_list){
                checkedTick.setVisibility(View.VISIBLE);

            }
        }
        //create on click listener for the question title textview
        questionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create new Shared Preferences
                SharedPreferences questionTitlePrefs = getContext().getSharedPreferences("CURRENT_QUESTION_TITLE", 0);
                SharedPreferences.Editor titleEditor = questionTitlePrefs.edit();
                titleEditor.putString("currentQuestionTitle", title);
                titleEditor.commit();

                SharedPreferences questionIDPrefs = getContext().getSharedPreferences("CURRENT_QUESTION_ID", 0);
                SharedPreferences.Editor idEditor = questionIDPrefs.edit();
                idEditor.putString("currentQuestionID", String.valueOf(questionID));
                idEditor.commit();


                Intent intent = new Intent(getContext(), VoteActivity.class);
                mContext.startActivity(intent);
            }
        });
        //set the textView using the string variable title from the array list.
        questionTitle.setText(title);

        return convertView;
    }
}
