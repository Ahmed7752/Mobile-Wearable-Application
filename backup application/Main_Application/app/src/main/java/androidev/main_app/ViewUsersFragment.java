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


    private ListView users_list;
    private TextView user_text;
    private Button show_users_btn, add_user_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_users, container, false);

        DataBaseHelper db = new DataBaseHelper(getActivity());

        users_list = view.findViewById(R.id.users_list_view);

        user_text = view.findViewById(R.id.users_list_text_view);

        show_users_btn = view.findViewById(R.id.show_users_btn);
        add_user_btn = view.findViewById(R.id.add_user_btn);

        show_users_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<UserModel> models = db.getAllUsers();
                SimpleUserListAdapter userListAdapter = new SimpleUserListAdapter(getActivity(), R.layout.user_list_adapter_layout, (ArrayList<UserModel>) models);
                if (models.isEmpty()){
                    user_text.setText("There is currently no users in the database!");
                    userListAdapter.notifyDataSetChanged();
                }
                users_list.setAdapter(userListAdapter);
            }
        });
        add_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new AddUserFragment()).commit();

            }
        });

        return view;
    }
}
