//This fragment class allows the admin user to import poll data and question data into the database.
package androidev.main_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.*;

import static android.content.Context.MODE_PRIVATE;

public class LoadDataFragment extends Fragment {
    //File names and the separator are you are declared.
    private static final String POLLS_FILE_NAME = "Poll_Titles.txt";
    private static final String QUESTIONS_FILE_NAME = "Questions.txt";
    public static final String SEPARATOR = "::";

    //All the variables are declared here.
    private String question_title,poll_title,poll_id, questions_data_str, polls_data_str;
    private Button log_out_btn, questions_btn, polls_btn, upload_btn;
    private EditText questions_data, polls_data;
    private TextView poll_status,question_status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The current view is inflated with the7364); layout.
        View view = inflater.inflate(R.layout.fragment_load_data, container, false);

        //A Constructor is created for the database helper class.
        DataBaseHelper db = new DataBaseHelper(getActivity());

        //Variables are matched with elements from the layout file.
        poll_status = view.findViewById(R.id.status_txt_view);
        question_status = view.findViewById(R.id.status_txt_view2);

        upload_btn = view.findViewById(R.id.upload_data);
        upload_btn.setEnabled(false);

        log_out_btn = view.findViewById(R.id.log_out_btn);
        questions_btn = view.findViewById(R.id.read_questions_btn);
        polls_btn = view.findViewById(R.id.read_poll_btn);
        questions_data = view.findViewById(R.id.input_question_data);
        polls_data = view.findViewById(R.id.input_poll_data);

        //An on click Listener is set for the logout button.
        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This message is toasted to the user and the current activity is destroyed.
                Toast.makeText(getActivity(), "Admin Logging Out", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                //This intent starts the userReglogActivity class unlock the user out.
                Intent intent = new Intent(getActivity(), UserRegLoginActivity.class);
                startActivity(intent);
            }
        });

        //An onclick listener is set for the poll button.
        polls_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //If the edit text field is empty this message is toasted to the user.
                if (polls_data.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please enter poll data", Toast.LENGTH_SHORT).show();

                }else{

                    //An array list is declared and stores the user imported data.
                    // when this symbol appears the data is split.
                    String array[] = polls_data.getText().toString().split("::");
                    //The poll title will be at index 0 of the array list.
                    poll_title = array[0];
                    //The data the user inputs is stored as a string variable.
                    polls_data_str = polls_data.getText().toString();
                    //The file output stream class is initialised with a null value.
                    FileOutputStream fos= null;
                    // inside of the try catch The open file output
                    // method creates a Private file inside of this context.
                    try {
                        //Creates or opens an application file for writing.
                        // These files are located in the /files subdirectory
                        fos = getContext().openFileOutput(POLLS_FILE_NAME, MODE_PRIVATE);

                        //A constructor for the output stream writer class is created
                        // and is connected to FileOutputStream.
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);

                        //The replaceall method is used to replace each “,” inside of the the
                        // string variable with a new line
                        polls_data_str = polls_data_str.replaceAll(",", "\n");

                        //The write method is used to pipe in data stored in the string variable
                        // into the output stream writer.
                        outputStreamWriter.write(polls_data_str);

                        //the text view you is set.
                        poll_status.setText("Poll(s) Status: SUCCESS - Ready");

                        //the output stream writer is flushed and closed.
                        outputStreamWriter.flush();
                        outputStreamWriter.close();
                        //the edit text field is cleared.
                        polls_data.getText().clear();
                     //File not found and input-output exceptions are caught.
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        //If the file output stream is not empty then the
                        // upload button is enabled and the file output stream is closed
                        if (fos != null){
                            try {
                                upload_btn.setEnabled(true);
                                fos.close();
                             // input-output exception is caught
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }


        });

        //An onclick listener is set for the questions button.
        questions_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //If the edit text field is empty this message is toasted to the user.
                if (questions_data.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please enter poll data", Toast.LENGTH_SHORT).show();

                }else{

                    //An array list is declared and stores the user imported data.
                    // when this symbol appears the data is split.
                    String array[] = questions_data.getText().toString().split("::");
                    //The questions title will be at index 0 of the array list.
                    question_title = array[0];
                    //The data the user inputs is stored as a string variable.
                    questions_data_str = questions_data.getText().toString();
                    //The file output stream class is initialised with a null value.
                    FileOutputStream fos= null;
                    // inside of the try catch The open file output
                    // method creates a Private file inside of this context.
                    try {
                        //Creates or opens an application file for writing.
                        // These files are located in the /files subdirectory.
                        fos = getContext().openFileOutput(QUESTIONS_FILE_NAME, MODE_PRIVATE);

                        //A constructor for the output stream writer class is created
                        // and is connected to FileOutputStream.
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);

                        //The replaceall method is used to replace each “,” inside of the
                        // the string variable with a new line
                        questions_data_str = questions_data_str.replaceAll(",", "\n");

                        //The write method is used to pipe in data stored in the string variable
                        // into the output stream writer.
                        outputStreamWriter.write(questions_data_str);

                        //the text view you is set.
                        question_status.setText("Question(s) Status: SUCCESS - Ready");

                        //the output stream writer is flushed and closed.
                        outputStreamWriter.flush();
                        outputStreamWriter.close();
                        //the edit text field is cleared.
                        questions_data.getText().clear();
                        //File not found and input-output exceptions are caught.
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        //If the file output stream is not empty then the
                        // upload button is enabled and the file output stream is closed
                        if (fos != null){
                            try {
                                upload_btn.setEnabled(true);
                                fos.close();
                                // input-output exception is caught
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }
        });

        //An onclick listener is set for the upload button.
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //The abstract file  class is used to get the path to the
                // created polls and questions files.
                File pollsFile = new File(getActivity().getFilesDir(),POLLS_FILE_NAME);
                //Condition to check if the poll file has been created.
                File questionsFile = new File(getActivity().getFilesDir(),QUESTIONS_FILE_NAME);

                //Condition to check if the poll file has been created.
                if (pollsFile.exists()){

                    //The user needs to import the polls one at a time .
                    //an object of the checkPoll from the database is initialised using a constructor
                    Boolean checkPoll = db.checkPoll(poll_title);
                    //Check if the entered poll data has already been inserted into the database.
                    if (checkPoll == true){

                        try {
                            //Opens an application file for reading.
                            // These files are located in the /files subdirectory.
                            FileInputStream fis = getContext().openFileInput(POLLS_FILE_NAME);

                            //An instance object of the inputstreamreader is created using a
                            // constructor,  the file input stream is inserted as an argument.
                            InputStreamReader inputStreamReader = new InputStreamReader(fis);

                            //An instance object of the BufferedReader is created using a
                            // constructor,  the inputStreamReader is inserted as an argument.
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                            //A string variable is declared.
                            String pData = null;

                            //A while loop that reads each line in the buffer reader is started.
                            while ((pData = bufferedReader.readLine()) != null){

                                //A string list is declared. The split method is used on the
                                // variable storing lines from the buffer reader. As an argument
                                // the separator symbol is given and the limit is non-positive so
                                // the pattern will be applied as many times as possible.
                                String[] properties = pData.split(SEPARATOR, -1);
                                try {
                                    //index 0  in the string list will be the title
                                    String title = properties[0];
                                    // index 1 in the list will be the topic
                                    String topic = properties[1];
                                    //index 2 will be the Frequency type
                                    String frequency = properties[2];
                                    //The addPolls  method from the database helper class is
                                    // called and given the correct arguments.
                                    db.addPolls(title,topic,frequency);
                                    // the text view is set.
                                    poll_status.setText("Poll(s) Status: SUCCESS - Sent to database");
                                    // the poll file is deleted.
                                    pollsFile.delete();
                                    //any exceptions are caught and this message is toasted to the user.
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), "ERROR: Polls not added to database", Toast.LENGTH_SHORT).show();

                                }
                            }
                            // the inputstreamreader is closed.
                            inputStreamReader.close();
                            //the fileInputStream is closed.
                            fis.close();
                            //Any exceptions are caught and this message is toasted to the user
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "ERROR: Something went wrong, poll file can not be read", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        // the text view is set.
                        poll_status.setText("ERROR: Poll(s) already exist in database");
                    }

                }else {
                    // the text view is set.
                    poll_status.setText("Poll(s) Status: No data was given.");
                }


                //Condition to check if the questions file has been created.
                if (questionsFile.exists()){
                    //The user needs to import the question one at a time .
                    if (question_title != null){
                        //Check if the entered question data has already been
                        // inserted into the database.

                        //An object of the check question method from the database
                        // helper class is initiated with a constructor.
                        Boolean checkQuestions = db.checkQuestions(question_title);
                        if (checkQuestions == true){
                            try {
                                FileInputStream fis = getContext().openFileInput(QUESTIONS_FILE_NAME);
                                //An instance object of the inputstreamreader is created using a
                                // constructor,  the file input stream is inserted as an argument.
                                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                                //An instance object of the BufferedReader
                                //is created using a constructor,  the inputStreamReader is inserted as an argument
                                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                //A string variable is declared.
                                String qData = null;
                                //A while loop that reads each line in the buffer reader is started.
                                while ((qData = bufferedReader.readLine()) != null){

                                    //A string list is declared. The split method is used on the
                                    // variable storing lines from the buffer reader.
                                    // As an argument the separator symbol is given and the
                                    // limit is non-positive so the pattern will be applied
                                    // as many times as possible.
                                    String[] properties = qData.split(SEPARATOR, -1);
                                    try {
                                        // index 0  in the string list will be the title
                                        String title = properties[0];
                                        //index 1 in the list will be the poll title
                                        String pollTitle = properties[1];
                                        //using the findPollID method from the database helper class
                                        // the id of the poll is found and stored into a
                                        // string variable when given the poll title at index 1.
                                        poll_id = db.findPollID(pollTitle);
                                        //The addQuestions  method from the database
                                        // helper class is called and given the correct arguments.
                                        db.addQuestions(title,poll_id);
                                        // the textview is set.
                                        question_status.setText("Question(s) Status: SUCCESS - Sent to database");
                                        //the question file is deleted.
                                        questionsFile.delete();

                                        // any exceptions are caught and this message is toasted to the user.
                                    } catch (Exception e) {
                                        Toast.makeText(getActivity(), "ERROR: Question not added to database", Toast.LENGTH_SHORT).show();

                                    }
                                }
                                //the inputstreamreader is closed.
                                // the fileinputstream is closed.
                                inputStreamReader.close();
                                fis.close();
                            } catch (Exception e) {
                                // Any exceptions are caught and this message is toasted to the user
                                Toast.makeText(getActivity(), "ERROR: Something went wrong, questions file can not be read", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            // the textview is set.
                            question_status.setText("Question(s) Status: Question(s) already exist in database");

                        }
                    }else {
                        // the textview is set.
                        question_status.setText("Question(s) Status: No data was given.");
                    }

                }else {
                    // the textview is set.
                    question_status.setText("Question(s) Status: A questions file has not been created");
                }
            }
        });





        return view;
    }

}
