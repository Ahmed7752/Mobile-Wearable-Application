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

        DataBaseHelper db = new DataBaseHelper(getContext());

        String username = getItem(position).getUsername();
        String email = getItem(position).getEmail();
        int id = getItem(position).getId();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView username_text = (TextView) convertView.findViewById(R.id.textView);
        TextView email_text = (TextView) convertView.findViewById(R.id.textView2);

        ImageButton delete_btn = (ImageButton) convertView.findViewById(R.id.delete_btn);

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int rows = db.deleteUser(id);
                if (rows > 0 ){
                    Toast.makeText(mContext, username +" has been deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext, "ERROR: User could not be deleted", Toast.LENGTH_SHORT).show();
                }


            }
        });


        username_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "User ID:  " + id, Toast.LENGTH_SHORT).show();
            }
        });

        username_text.setText(username);
        email_text.setText(email);

        return convertView;
    }
}
