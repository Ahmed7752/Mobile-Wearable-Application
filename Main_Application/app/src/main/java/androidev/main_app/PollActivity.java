//This is the main poll activity, this is where all poll related fragments are presented.
package androidev.main_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PollActivity extends AppCompatActivity {
    //The required variables are declared here.
    private String result_vote_str,user_postcode,user_id_str, poll_id_str, poll_title_str,poll_topic_str;
    private int poll_id,user_id,result_vote = -1;
    private Button back_btn, save_btn, complete_btn ;
    private TextView poll_title, error_text_view;
    private ListView questions_list_view;

    //The setNestedScrollingEnabled property requires level 21 API to function.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        //Constructor is used to create an object for the database helper class.
        DataBaseHelper db = new DataBaseHelper(getApplication());

        //The required shared preferences are called.
        SharedPreferences PollIDPrefs = this.getSharedPreferences("CURRENT_POLL_ID", 0);
        SharedPreferences UserIDPrefs = this.getSharedPreferences("CURRENT_USER_ID", 0);
        SharedPreferences pollTitlePref = this.getSharedPreferences("CURRENT_POLL_TITLE", 0);
        SharedPreferences pollTopicPref = this.getSharedPreferences("CURRENT_POLL_TOPIC", 0);
        //The value inside of the shared preference is stored as a string variable
        if (pollTitlePref.contains("currentPollTitle")) {
            poll_title_str = pollTitlePref.getString("currentPollTitle", "title");
        }
        //The value inside of the shared preference is stored as a string variable
        if (pollTopicPref.contains("currentPollTopic")) {
            poll_topic_str = pollTopicPref.getString("currentPollTopic", "title");
        }

        //The value inside of the shared preference is stored as a string variable
        if (PollIDPrefs.contains("currentPollID")) {
            poll_id_str = PollIDPrefs.getString("currentPollID", "id");
            poll_id = Integer.parseInt(poll_id_str);
        }
        //The value inside of the shared preference is stored as a string variable
        if (UserIDPrefs.contains("currentUserID")) {
            user_id_str = UserIDPrefs.getString("currentUserID", "id");
            user_id = Integer.parseInt(user_id_str);
        }

        //Using the method findUserPostcode from the database helper class the
        // postcode of the current user is extracted from the database
        // and stored as a string variable
        user_postcode = db.findUserPostcode(user_id_str);

        //Variables are matched with elements from the layout file.
        poll_title = findViewById(R.id.poll_title_text_view);
            //The poll title textView is set.
            poll_title.setText(poll_title_str);
        error_text_view = findViewById(R.id.questions_error_text_view);
        questions_list_view = findViewById(R.id.questions_list_view);
        save_btn = findViewById(R.id.save_poll_btn);
        back_btn = findViewById(R.id.back_btn);
        complete_btn = findViewById(R.id.complete_btn);


        //The getPollQuestions method from the database helper class is used to build a list
        // containing question objects using the Questions Model class, when given the users ID.
        List<QuestionsModel> models = db.getPollQuestions(poll_id_str);

        //If the List containing objects is empty the text view is set to
        // display this message, the list view element is no longer scrollable.
        if (models.isEmpty()){
            error_text_view.setText("There are no poll suggestions for today, please check back tomorrow.");
            questions_list_view.setNestedScrollingEnabled(false);

        }else {

            //A contractor is used to created an object for the custom list adapter, it is given the
            // custom list adapter layout file and the array list containing question
            // objects retrieved from the database.
            QuestionListAdapter questionAdapter = new QuestionListAdapter(this, R.layout.question_list_adapter_layout, (ArrayList<QuestionsModel>) models);
            questions_list_view.setAdapter(questionAdapter);

        }



        //If the public vote_list from the VoteActivity class has a size greater than 6 then do this
        if (VoteActivity.votes_list.size() > 6){
            // clear the list
            VoteActivity.votes_list.clear();
            //If the public vote_list from the VoteActivity class has a size less than 6 then do this
        }else if (VoteActivity.votes_list.size() < 6){
            //Disable the complete button
            complete_btn.setEnabled(false);
        }else {
            //enable the complete button
            complete_btn.setEnabled(true);
        }

        // if the public results list from the vote activity class is not empty do this.
        if (!VoteActivity.results_list.isEmpty()){
            // declare an Int variable and initialise it with zero.
            int sum = 0;

            // for every item in the results_list add the value to the sum int variable
            for(Integer result : VoteActivity.results_list){

                sum += result;
            }
            //divide the value stored int sum variable by the size of the results_list
            // and store the result in an int variable.
            result_vote = (sum / VoteActivity.results_list.size());

            //If the variable is not empty do this.
            if (!(result_vote == -1)){

                // if the int variable contains 1, set this string variable as “strongly agree”.
                if (result_vote == 1){
                    result_vote_str = "Strongly Agree";
                    // if the int variable contains 2, set this string variable as “Somewhat Agree”.
                }else if (result_vote == 2){
                    result_vote_str = "Somewhat Agree";
                    // if the int variable contains 3, set this string variable as “Somewhat Disagree”.
                }else if (result_vote == 3){
                    result_vote_str = "Somewhat Disagree";
                    // if the int variable contains 4, set this string variable as “Strongly Disagree”.
                }else if (result_vote == 4){
                    result_vote_str = "Strongly Disagree";
                }

            }
        }



        //An on click listener is set on the complete button.
        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if a result has already been generated in the database.
                // if a poll with this specific title has not been inserted into
                // the results table by the current user do this.
                if (db.CheckResult(poll_title_str,user_id_str) == false){

                    //using the completeVote method from the database helper class the users vote,
                    // postcode, ID and the poll title, poll topic are inserted into the results
                    // table. Thus generated the users vote result.
                    db.completeVote(result_vote_str,user_postcode,user_id,poll_title_str,poll_topic_str);
                    //Toast this message.
                    Toast.makeText(PollActivity.this, "Your votes have been saved, you " +
                            "can view the results in your vote history " +
                            "section on the profile tab", Toast.LENGTH_LONG).show();

                    //This intent starts the MainActivity
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    //destroy this activity by using the finish() method.
                    finish();
                }else {
                    //Toast this message.
                    Toast.makeText(PollActivity.this, "You have already completed a " +
                            "vote on this poll. Please try a different poll", Toast.LENGTH_LONG).show();
                    //This intent starts the MainActivity
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    //destroy this activity by using the finish() method.
                    finish();
                }

            }
        });

        //An on click listener is set on the save button.
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the votes_list from the VoteActivity class has a size not equal to 6 do this.
                if (VoteActivity.votes_list.size() != 6){
                    //Toast this message.
                    Toast.makeText(PollActivity.this, "Your votes have not " +
                            "been saved", Toast.LENGTH_SHORT).show();
                    IsComplete isComplete = new IsComplete(false);

                    db.savePoll(poll_id,user_id,isComplete);
                    //Toast this message.
                    Toast.makeText(PollActivity.this, "This poll has been saved " +
                            "to your favourites page", Toast.LENGTH_SHORT).show();
                    VoteActivity.votes_list.clear();
                    //This intent starts the MainActivity
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    //destroy this activity by using the finish() method.
                    finish();
                }else {

                    IsComplete isComplete = new IsComplete(true);

                    db.savePoll(poll_id,user_id,isComplete);
                    //Toast this message.
                    Toast.makeText(PollActivity.this, "This poll has been saved " +
                            "to your favourites page", Toast.LENGTH_SHORT).show();
                    VoteActivity.votes_list.clear();
                    //This intent starts the MainActivity
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    //destroy this activity by using the finish() method.
                    finish();
                }
            }
        });

        //An on click listener is set on the back button.
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This intent starts the MainActivity
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                //destroy this activity by using the finish() method.
                finish();
            }
        });




    }



}