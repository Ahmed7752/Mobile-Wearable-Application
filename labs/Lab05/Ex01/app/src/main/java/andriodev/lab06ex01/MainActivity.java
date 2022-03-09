package andriodev.lab06ex01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    EditText textUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textUri = (EditText) findViewById(R.id.textSearch);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To Be COMPLETED}});
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://" + textUri.getText().toString()));
                startActivity(intent);
            }
        });
    }

}