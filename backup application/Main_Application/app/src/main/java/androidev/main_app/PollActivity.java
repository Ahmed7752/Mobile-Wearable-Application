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

    private String result_vote_str,user_postcode,user_id_str, poll_id_str, poll_title_str,poll_topic_str;
    private int poll_id,user_id,result_vote = -1;
    private Button back_btn, save_btn, complete_btn ;
    private TextView poll_title, error_text_view;
    private ListView questions_list_view;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        DataBaseHelper db = new DataBaseHelper(getApplication());

        SharedPreferences PollIDPrefs = this.getSharedPreferences("CURRENT_POLL_ID", 0);
        SharedPreferences UserIDPrefs = this.getSharedPreferences("CURRENT_USER_ID", 0);
        SharedPreferences pollTitlePref = this.getSharedPreferences("CURRENT_POLL_TITLE", 0);
        SharedPreferences pollTopicPref = this.getSharedPreferences("CURRENT_POLL_TOPIC", 0);
        if (pollTitlePref.contains("currentPollTitle")) {
            poll_title_str = pollTitlePref.getString("currentPollTitle", "title");
        }
        if (pollTopicPref.contains("currentPollTopic")) {
            poll_topic_str = pollTopicPref.getString("currentPollTopic", "title");
        }

        if (PollIDPrefs.contains("currentPollID")) {
            poll_id_str = PollIDPrefs.getString("currentPollID", "id");
            poll_id = Integer.parseInt(poll_id_str);
        }

        if (UserIDPrefs.contains("currentUserID")) {
            user_id_str = UserIDPrefs.getString("currentUserID", "id");
            user_id = Integer.parseInt(user_id_str);
        }

        user_postcode = db.findUserPostcode(user_id_str);

        poll_title = findViewById(R.id.poll_title_text_view);
            poll_title.setText(poll_title_str);

        error_text_view = findViewById(R.id.questions_error_text_view);

        questions_list_view = findViewById(R.id.questions_list_view);

        save_btn = findViewById(R.id.save_poll_btn);
        back_btn = findViewById(R.id.back_btn);
        complete_btn = findViewById(R.id.complete_btn);




        List<QuestionsModel> models = db.getPollQuestions(poll_id_str);

        if (models.isEmpty()){
            error_text_view.setText("There are no poll suggestions for today, please check back tomorrow.");
            questions_list_view.setNestedScrollingEnabled(false);

        }else {

            QuestionListAdapter questionAdapter = new QuestionListAdapter(this, R.layout.question_list_adapter_layout, (ArrayList<QuestionsModel>) models);
            questions_list_view.setAdapter(questionAdapter);

        }


        if (VoteActivity.votes_list.size() == 6){
            VoteActivity.votes_list.clear();
            complete_btn.setEnabled(true);
        }else {
            complete_btn.setEnabled(false);
        }

        if (!VoteActivity.results_list.isEmpty()){
            int sum = 0;

            for(Integer result : VoteActivity.results_list){

                sum += result;
            }
            result_vote = (sum / VoteActivity.results_list.size());
            if (!(result_vote == -1)){

                if (result_vote == 1){
                    result_vote_str = "Strongly Agree";
                }else if (result_vote == 2){
                    result_vote_str = "Somewhat Agree";
                }else if (result_vote == 3){
                    result_vote_str = "Somewhat Disagree";
                }else if (result_vote == 4){
                    result_vote_str = "Strongly Disagree";
                }

            }
        }



        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.CheckResult(poll_title_str,user_id_str) == false){

                    db.completeVote(result_vote_str,user_postcode,user_id,poll_title_str,poll_topic_str);
                    Toast.makeText(PollActivity.this, "Your votes have been saved, you can view the results in your vote history section on the profile tab", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(PollActivity.this, "You have already completed a vote on this poll. Please try a different poll", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VoteActivity.votes_list.size() < 6){
                    Toast.makeText(PollActivity.this, "Your votes have not been saved", Toast.LENGTH_SHORT).show();
                    IsComplete isComplete = new IsComplete(false);

                    db.savePoll(poll_id,user_id,isComplete);
                    Toast.makeText(PollActivity.this, "This poll has been saved to your favourites page", Toast.LENGTH_SHORT).show();
                    VoteActivity.votes_list.clear();
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {

                    IsComplete isComplete = new IsComplete(true);

                    db.savePoll(poll_id,user_id,isComplete);
                    Toast.makeText(PollActivity.this, "This poll has been saved to your favourites page", Toast.LENGTH_SHORT).show();
                    VoteActivity.votes_list.clear();
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }



}