package com.example.lab02ex04;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    private TextView mTimeView, mDateView;
    private Button mSetTimeButton, mSetDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTimeView = (TextView) findViewById(R.id.textTimeView);
        mDateView = (TextView) findViewById(R.id.textDateView);
        mSetTimeButton = (Button) findViewById(R.id.setTimeButton);
        mSetDateButton = (Button) findViewById(R.id.setDateButton);
        mSetTimeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });
        mSetDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
    }
}