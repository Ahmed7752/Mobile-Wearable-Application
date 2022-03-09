package andriodev.Lab06Ex04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mButton;

    EditText SearchLat;
    EditText SearchLong;

    String lat_str,lon_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textView);
        SearchLat = (EditText) findViewById(R.id.textSearchLat);
        SearchLong = (EditText) findViewById(R.id.textSearchLong);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To Be COMPLETED}});


                lat_str=SearchLat.getText().toString();
                lon_str=SearchLong.getText().toString();

                Uri uri = Uri.parse("geo:0,0?q="+lat_str+","+lon_str);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}