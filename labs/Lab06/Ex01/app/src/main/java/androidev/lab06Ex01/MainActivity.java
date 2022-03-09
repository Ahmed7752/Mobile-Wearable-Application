package androidev.lab06Ex01;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.Date;

import androidev.lab06Ex01.R;

public class MainActivity extends AppCompatActivity {
    private TextView mLastActive;
    private TextView mStartTime;
    private Button mRestart;
    private DateFormat mDataFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        mLastActive = (TextView) findViewById(R.id.textView4);
        mRestart = (Button) findViewById(R.id.button1);
        mStartTime = (TextView) findViewById(R.id.textView2);
        mRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // restart activity
                // this only works after API 11
                recreate();

            }

        });

        // display last time this activity was created
        SharedPreferences activityPrefs = getSharedPreferences("MYPREFS", 0);
        if (activityPrefs.contains("lastTime")) {
            long last_time = activityPrefs.getLong("lastTime", 0L);
            mLastActive.setText(mDataFormat.format(last_time));
        }
        // now save time that current activity was created
        SharedPreferences.Editor editor = activityPrefs.edit();
        long current_time = new Date().getTime();
        editor.putLong("lastTime", current_time);
        editor.commit();
        mStartTime.setText(mDataFormat.format(current_time));
    }
}