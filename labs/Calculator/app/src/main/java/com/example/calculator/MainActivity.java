package com.example.calculator;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int firstNo, secondNo,answer;
    String operator = "";
    private int sum = 0;

    EditText firstInput;
    EditText secondInput;
    EditText operatorInput;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstInput = (EditText) findViewById(R.id.firstInput);
        secondInput = (EditText) findViewById(R.id.secondInput);
        operatorInput = (EditText) findViewById(R.id.operatorInput);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.answer);
                firstNo = Integer.valueOf(firstInput.getText().toString());
                secondNo = Integer.valueOf(secondInput.getText().toString());
                operator = operatorInput.getText().toString();
                if (operator.equals("+")){
                    answer = firstNo+secondNo;
                    textView.setText("Answer = " + answer);
                }else if (operator.equals("-")){
                    answer = firstNo-secondNo;
                    textView.setText("Answer = " + answer);
                }else if (operator.equals("*")){
                    answer = firstNo*secondNo;
                    textView.setText("Answer = " + answer);
                }else if (operator.equals("/")){
                    answer = firstNo/secondNo;
                    textView.setText("Answer = " + answer);
                }else{
                    textView.setText("SOMETHING WENT WRONG");
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}