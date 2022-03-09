package andriodev.lab04ex01;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public String massage;
    private String reply;
    private static final int REQUEST_CODE = 1;

    private Button mButton;
    private EditText massageBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mButton = (Button) findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // To Be COMPLETED
                massageBox = (EditText) findViewById(R.id.editText1);
                massage = massageBox.getText().toString();
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("androidev.myapplication.KeyToStringData",massage);
                startActivityForResult(intent, REQUEST_CODE);
            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView textReply = (TextView) findViewById(R.id.textViewReply);


        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            reply = data.getStringExtra("androidev.myapplication.KeyToStringData");
            textReply.setText("Received reply: " + reply);
        }else{
            textReply.setText("error");
        }
    }

}