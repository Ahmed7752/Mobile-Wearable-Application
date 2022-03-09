package androidev.lab06Ex02;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "file.txt";

    private Button wButton;
    private Button rButton;

    String fileContents;
    String readFileContents;

    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void WriteToFile(View v) {
        content = (EditText) findViewById(R.id.textWrite);
        fileContents = content.getText().toString();
        FileOutputStream fos= null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            outputStreamWriter.write(fileContents);
            Toast.makeText(this, "Written to file at " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
            outputStreamWriter.flush();
            outputStreamWriter.close();
            content.getText().clear();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void ReadFromFile(View v) {

        try {
            FileInputStream fis = openFileInput(FILE_NAME);

            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String text ;
            while ((text = bufferedReader.readLine()) != null){
                stringBuilder.append(text).append("\n");
            }
            TextView textView = (TextView) findViewById(R.id.textView1);
            textView.setText(stringBuilder.toString());
            inputStreamReader.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}