package com.example.lab02ex01;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Lab02Ex01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(TAG, "In the onStart() event handler");

    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        Log.d(TAG, "In the onRestart() event handler");
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(TAG, "In the onResume() event handler");

    }
    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(TAG, "In the onPause() event handler");
    }
    @Override
    public void onStop()
    {
        super.onStop();
        Log.d(TAG, "In the onStop() event handler");
        
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "In the onDestroy() event handler");
    }
}