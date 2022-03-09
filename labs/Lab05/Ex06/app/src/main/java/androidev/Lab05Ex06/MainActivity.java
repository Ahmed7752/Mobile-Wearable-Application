package androidev.Lab05Ex06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.imageView);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
            mImageView.setImageURI((Uri) bundle.get(Intent.EXTRA_STREAM));
    }
}