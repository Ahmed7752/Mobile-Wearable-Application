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
    private static final String POLLS_FILE_NAME = "Poll_Titles.txt";
    private static final String QUESTIONS_FILE_NAME = "Questions.txt";
    public static final String SEPARATOR = "::";


    private String question_title,poll_title,poll_id, questions_data_str, polls_data_str;
    private Button log_out_btn, questions_btn, polls_btn, upload_btn;
    private EditText questions_data, polls_data;
    private TextView poll_status,question_status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_load_data, container, false);


        DataBaseHelper db = new DataBaseHelper(getActivity());

        poll_status = view.findViewById(R.id.status_txt_view);
        question_status = view.findViewById(R.id.status_txt_view2);

        upload_btn = view.findViewById(R.id.upload_data);
        upload_btn.setEnabled(false);

        log_out_btn = view.findViewById(R.id.log_out_btn);
        questions_btn = view.findViewById(R.id.read_questions_btn);
        polls_btn = view.findViewById(R.id.read_poll_btn);
        questions_data = view.findViewById(R.id.input_question_data);
        polls_data = view.findViewById(R.id.input_poll_data);


        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Admin Logging Out", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), UserRegLoginActivity.class);
                startActivity(intent);
            }
        });

        polls_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (polls_data.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please enter poll data", Toast.LENGTH_SHORT).show();

                }else{


                    String array[] = polls_data.getText().toString().split("::");
                    poll_title = array[0];
                    Toast.makeText(getActivity(), poll_title, Toast.LENGTH_SHORT).show();
                    polls_data_str = polls_data.getText().toString();
                    FileOutputStream fos= null;
                    try {
                        fos = getContext().openFileOutput(POLLS_FILE_NAME, MODE_PRIVATE);
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
                        polls_data_str = polls_data_str.replaceAll(",", "\n");
                        outputStreamWriter.write(polls_data_str);
                        poll_status.setText("Poll(s) Status: SUCCESS - Ready");
                        outputStreamWriter.flush();
                        outputStreamWriter.close();
                        polls_data.getText().clear();
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if (fos != null){
                            try {
                                upload_btn.setEnabled(true);
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }


        });

        questions_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questions_data.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please enter poll data", Toast.LENGTH_SHORT).show();

                }else{

                    //Commented out section below is used to check if the spilt functions correctly

                    String array[] = questions_data.getText().toString().split("::");
                    question_title = array[0];
                    Toast.makeText(getActivity(), question_title, Toast.LENGTH_SHORT).show();
                    questions_data_str = questions_data.getText().toString();
                    FileOutputStream fos= null;
                    try {
                        fos = getContext().openFileOutput(QUESTIONS_FILE_NAME, MODE_PRIVATE);
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
                        questions_data_str = questions_data_str.replaceAll(",", "\n");
                        outputStreamWriter.write(questions_data_str);
                        question_status.setText("Question(s) Status: SUCCESS - Ready");
                        outputStreamWriter.flush();
                        outputStreamWriter.close();
                        questions_data.getText().clear();
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if (fos != null){
                            try {
                                upload_btn.setEnabled(true);
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File pollsFile = new File(getActivity().getFilesDir(),POLLS_FILE_NAME);
                File questionsFile = new File(getActivity().getFilesDir(),QUESTIONS_FILE_NAME);

                if (pollsFile.exists()){

                    Boolean checkPoll = db.checkPoll(poll_title);
                    if (checkPoll == true){

                        try {
                            FileInputStream fis = getContext().openFileInput(POLLS_FILE_NAME);
                            InputStreamReader inputStreamReader = new InputStreamReader(fis);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            String pData = null;
                            while ((pData = bufferedReader.readLine()) != null){

                                String[] properties = pData.split(SEPARATOR, -1);
                                try {
                                    String title = properties[0];
                                    String topic = properties[1];
                                    String frequency = properties[2];
                                    db.addPolls(title,topic,frequency);
                                    poll_status.setText("Poll(s) Status: SUCCESS - Sent to database");
                                    pollsFile.delete();
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), "ERROR: Polls not added to database", Toast.LENGTH_SHORT).show();

                                }
                            }
                            inputStreamReader.close();
                            fis.close();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "ERROR: Something went wrong, poll file can not be read", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        poll_status.setText("ERROR: Poll(s) already exist in database");
                    }

                }else {
                    poll_status.setText("Poll(s) Status: No data was given.");
                }



                if (questionsFile.exists()){
                    if (question_title != null){
                        Boolean checkQuestions = db.checkQuestions(question_title);
                        if (checkQuestions == true){
                            try {
                                FileInputStream fis = getContext().openFileInput(QUESTIONS_FILE_NAME);
                                InputStreamReader inputStreamReader = new InputStreamReader(fis);
                                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                String qData = null;
                                while ((qData = bufferedReader.readLine()) != null){

                                    String[] properties = qData.split(SEPARATOR, -1);
                                    try {
                                        String title = properties[0];
                                        String pollTitle = properties[1];
                                        poll_id = db.findPollID(pollTitle);
                                        db.addQuestions(title,poll_id);
                                        question_status.setText("Question(s) Status: SUCCESS - Sent to database");
                                        questionsFile.delete();

                                    } catch (Exception e) {
                                        Toast.makeText(getActivity(), "ERROR: Question not added to database", Toast.LENGTH_SHORT).show();

                                    }
                                }
                                inputStreamReader.close();
                                fis.close();
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "ERROR: Something went wrong, questions file can not be read", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            question_status.setText("Question(s) Status: Question(s) already exist in database");

                        }
                    }else {
                        question_status.setText("Question(s) Status: No data was given.");
                    }

                }else {
                    question_status.setText("Question(s) Status: A questions file has not been created");
                }
            }
        });





        return view;
    }

}
