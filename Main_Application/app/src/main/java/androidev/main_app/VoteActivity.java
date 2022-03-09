package androidev.main_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class VoteActivity extends AppCompatActivity {

    private String voter_str,poll_id_str, checkedButton, user_id_str, question_id_str, question_title_str, poll_topic_str,poll_title_str;

    private Button back_btn, vote_btn;
    private RadioGroup radioGroup;
    private TextView question_text, error_text_view, poll_topic, poll_title;


    public static List<Integer> votes_list = new ArrayList<Integer>();
    public static List<Integer> results_list = new ArrayList<Integer>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        DataBaseHelper db = new DataBaseHelper(getApplication());
        //get all shared preferences.
        SharedPreferences pollTitlePref = this.getSharedPreferences("CURRENT_POLL_TITLE", 0);
        SharedPreferences pollIDPref = this.getSharedPreferences("CURRENT_POLL_ID", 0);
        SharedPreferences topicPrefs = this.getSharedPreferences("CURRENT_POLL_TOPIC", 0);
        SharedPreferences questionTitlePrefs = this.getSharedPreferences("CURRENT_QUESTION_TITLE", 0);
        SharedPreferences questionIDPrefs = this.getSharedPreferences("CURRENT_QUESTION_ID", 0);
        SharedPreferences userIDPrefs = this.getSharedPreferences("CURRENT_USER_ID", 0);

        //check if a preference contains and a certain key, then store the value as a variable.
        if (userIDPrefs.contains("currentUserID")) {
            user_id_str = userIDPrefs.getString("currentUserID", "id");
        }
        if (pollIDPref.contains("currentPollID")) {
            poll_id_str = pollIDPref.getString("currentPollID", "id");
        }

        if (questionIDPrefs.contains("currentQuestionID")) {
            question_id_str = questionIDPrefs.getString("currentQuestionID", "id");
        }

        if (questionTitlePrefs.contains("currentQuestionTitle")) {
            question_title_str = questionTitlePrefs.getString("currentQuestionTitle", "title");
        }
        if (topicPrefs.contains("currentPollTopic")) {
            poll_topic_str = topicPrefs.getString("currentPollTopic", "topic");
        }
        if (pollTitlePref.contains("currentPollTitle")) {
            poll_title_str = pollTitlePref.getString("currentPollTitle", "title");
        }

        //find and match variables to elements in the layout file.
        question_text = findViewById(R.id.question_title_text_view);
        question_text.setText(question_title_str);
        poll_topic = findViewById(R.id.text_view_topic_column);
        poll_topic.setText(poll_topic_str);
        poll_title = findViewById(R.id.text_view_title_column);
        poll_title.setText(poll_title_str);
        back_btn = findViewById(R.id.back_btn);
        vote_btn = findViewById(R.id.vote_btn);
        radioGroup = findViewById(R.id.vote_radio_group);

        // The following code keeps track of which radio button the user has clicked.

        //An onCheckedChangeListener is set on the radio group, this runs whenever
        // the user clicks on a radio button in the radio  group.
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //The radio buttons are link to the radio button elements in the layout file
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                //The text of the click radio button is stored in a string variable.
                checkedButton = checkedRadioButton.getText().toString();
            }
        });





        //create on click listener for the vote button
        vote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the id of the check option.
                if (checkedButton == null){
                    //if no option is click, toast message error message.
                    Toast.makeText(getApplicationContext(), "Please select an option to vote",
                            Toast.LENGTH_SHORT).show();
                }else {
                    //If the string variable checkButton contains “strongly agree” add 1 to
                    // the public results_list
                    if (checkedButton.contains("Strongly Agree")){
                        results_list.add(1);
                        //If the string variable checkButton contains “Somewhat Agree” add 2 to
                        // the public results_list
                    }else  if (checkedButton.contains("Somewhat Agree")){
                        results_list.add(2);
                        //If the string variable checkButton contains “Do Not Know” add 0 to
                        // the public results_list
                    }else if (checkedButton.contains("Do Not Know")){
                        results_list.add(0);
                        //If the string variable checkButton contains “Somewhat Disagree” add 3 to
                        // the public results_list
                    }else if (checkedButton.contains("Somewhat Disagree")){
                        results_list.add(3);
                        //If the string variable checkButton contains “Strongly Disagree” add 4 to
                        // the public results_list
                    }else if (checkedButton.contains("Strongly Disagree")){
                        results_list.add(4);
                    }


                    //Check if the question id is in the database already.
                    if (db.CheckVote(question_id_str, user_id_str) == true){
                        Toast.makeText(VoteActivity.this, "You have already voted on this " +
                                "questions. Please try another question!", Toast.LENGTH_LONG).show();
                        //using intent change back to Poll Activity.
                        Intent intent = new Intent(getApplication(), PollActivity.class);
                        startActivity(intent);
                    }else {
                        //use vote method from the database helper class.
                        db.Vote(Integer.parseInt(question_id_str),checkedButton, Integer.parseInt(user_id_str),
                                Integer.parseInt(poll_id_str));
                        //create a COMPLETED_QUESTION_ID Shared Preference.

                        votes_list.add(Integer.valueOf(question_id_str));

                        //using intent change back to Poll Activity.
                        Intent intent = new Intent(getApplication(), PollActivity.class);
                        startActivity(intent);
                    }



                }
            }
        });



        //Create on click listener for back button.
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //using intent change back to Poll Activity.
                Intent intent = new Intent(getApplication(), PollActivity.class);
                startActivity(intent);
            }
        });



    }


}