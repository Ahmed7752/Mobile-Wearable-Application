package andriodev.lab04ex01;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    String reply;

    private Button mButton;
    String msg = "";
    EditText replyBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            msg = extras.getString("androidev.myapplication.KeyToStringData");
        }

        TextView textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText("Received message: " + msg);


        mButton = (Button) findViewById(R.id.button2);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                replyBox = (EditText) findViewById(R.id.editTextReply);
                TextView textReply = (TextView) findViewById(R.id.editTextReply);
                reply = replyBox.getText().toString();
                Intent data = new Intent();
                data.putExtra("androidev.myapplication.KeyToStringData",reply);

                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}