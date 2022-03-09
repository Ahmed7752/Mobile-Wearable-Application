package com.example.lab02ex05;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setTextFilterEnabled(true);

        String[] countries = getResources().getStringArray(R.array.countries_array);
        lv.setAdapter(new ArrayAdapter < String > (this, R.layout.list_item, countries));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView < ? > parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(),

                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}