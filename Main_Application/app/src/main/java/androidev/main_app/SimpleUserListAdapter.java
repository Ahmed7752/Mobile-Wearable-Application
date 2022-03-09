package androidev.main_app;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

//create a custom ListView adapter. using the array list containing the UserModel objects.
public class SimpleUserListAdapter extends ArrayAdapter<UserModel> {

    private Context mContext;
    int mResource;



    public SimpleUserListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<UserModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //A constructor is used to create an object for the database helper class.
        DataBaseHelper db = new DataBaseHelper(getContext());

        //get the the username (string), email (string) and id (int) from the array list object.
        // To then be store as variables.
        String username = getItem(position).getUsername();
        String email = getItem(position).getEmail();
        int id = getItem(position).getId();
        //inflate the custom adaptor
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //convertView is used to reuse old view.
        convertView = inflater.inflate(mResource, parent, false);

        //find the textView and imageView elements from the layout file
        TextView username_text = (TextView) convertView.findViewById(R.id.textView);
        TextView email_text = (TextView) convertView.findViewById(R.id.textView2);

        ImageButton delete_btn = (ImageButton) convertView.findViewById(R.id.delete_btn);

        //create on click listener for the delete imageView
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Use the method deleteUser from the database helper class to delete a user, the
                // id of the user is passed.
                int rows = db.deleteUser(id);
                //if the operation was successful the deleteUser method will return an integer
                // of how many rows removed.
                //if more than 0 rows are removed toast this message.
                if (rows > 0 ){
                    Toast.makeText(mContext, username +" has been deleted", Toast.LENGTH_SHORT).show();
                }else {
                    //if no rows are removed toast this message.
                    Toast.makeText(mContext, "ERROR: User could not be deleted", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //create on click listener for the username textView
        username_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast this message plus the users id.
                Toast.makeText(mContext, "User ID:  " + id, Toast.LENGTH_SHORT).show();
            }
        });

        //set the textViews using the string variable title and email from the array list.
        username_text.setText(username);
        email_text.setText(email);

        return convertView;
    }
}
