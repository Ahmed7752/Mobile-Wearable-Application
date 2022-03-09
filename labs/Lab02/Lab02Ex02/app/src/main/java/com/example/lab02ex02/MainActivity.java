package com.example.lab02ex02;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button mButton;
    private ToggleButton mToggleButton;
    private CheckBox mCheckBox;
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.button);
        mToggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        mCheckBox = (CheckBox)findViewById(R.id.checkBox);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String msg = getString(R.string.press_me);
                mTextView.setText(msg);
            }
        });
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String msg;
                if (((ToggleButton) v).isChecked())
                    msg = getString(R.string.on);
                else
                    msg = getString(R.string.off);
                mTextView.setText(msg);
            }
        });

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                String msg;
                if (((CheckBox) v).isChecked())
                    msg= getString(R.string.check_me);
                else
                    msg = getString(R.string.uncheck_me);
                mTextView.setText(msg);
                // update check box name
                ((CheckBox) v).setText(msg);
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                if (rb.isChecked())
                    mTextView.setText(rb.getText());
            }
        });
    }

}


















