//This fragment class is used to display all the users in the database.
package androidev.main_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewUsersFragment extends Fragment {

    //The required variables are declared here.
    private ListView users_list;
    private TextView user_text;
    private Button show_users_btn, add_user_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The home fragment layout file is inflated into the view.
        View view = inflater.inflate(R.layout.fragment_view_users, container, false);

        //Constructor is used to create an object for the database helper class.
        DataBaseHelper db = new DataBaseHelper(getActivity());

        //Variables are matched with elements from the layout file.
        users_list = view.findViewById(R.id.users_list_view);
        user_text = view.findViewById(R.id.users_list_text_view);
        show_users_btn = view.findViewById(R.id.show_users_btn);
        add_user_btn = view.findViewById(R.id.add_user_btn);

        //An on click listener is set on the show users button.
        show_users_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The getAllUsers method from the database helper class is used to build a list
                // containing user objects using the UserModel class
                List<UserModel> models = db.getAllUsers();
                //A contractor is used to  created an object for the custom list adapter, it is given the
                // custom list adapter layout file and the array list containing user
                // objects retrieved from the database.
                SimpleUserListAdapter userListAdapter = new SimpleUserListAdapter(getActivity(),
                        R.layout.user_list_adapter_layout, (ArrayList<UserModel>) models);
                //If the List containing objects is empty the text view is set to
                // display this message, the custom list adapter is updated.
                if (models.isEmpty()){
                    user_text.setText("There is currently no users in the database!");
                    userListAdapter.notifyDataSetChanged();
                }
                //The list element is set with the custom list adapter.
                users_list.setAdapter(userListAdapter);
            }
        });

        //An on click listener is set on the add user button.
        add_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The current fragment is replaced with the AddUserFragment.
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new AddUserFragment()).commit();

            }
        });

        return view;
    }
}
