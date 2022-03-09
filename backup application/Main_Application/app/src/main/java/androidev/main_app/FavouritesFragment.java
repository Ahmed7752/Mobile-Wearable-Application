package androidev.main_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import androidev.main_app.PollActivity;
import androidev.main_app.R;

public class FavouritesFragment extends Fragment {
    private String id_str;
    private ListView poll_list;
    private TextView poll_text;
    private Button clear_fav_btn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourites, container, false);


        DataBaseHelper db = new DataBaseHelper(getActivity());
        SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_USER_ID", 0);

        if (userPrefs.contains("currentUserID")) {
            id_str = userPrefs.getString("currentUserID", "id");
        }
        clear_fav_btn = v.findViewById(R.id.clear_favourites_btn);

        poll_list = v.findViewById(R.id.poll_list_view);

        poll_text = v.findViewById(R.id.polls_text_view);

        if (db.checkComplete(id_str) == true) {

            List<PollModel> models = db.getPolls(id_str);

            if (models.isEmpty()){
                poll_text.setText("There are no poll that you have saved yet.");
                poll_list.setNestedScrollingEnabled(false);

            }else {
                PollListAdapter pollsAdapter = new PollListAdapter(getActivity(), R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) models);
                poll_list.setAdapter(pollsAdapter);
            }
        }else{
            poll_text.setText("You need to finish setting up your profile before you can save polls");
            poll_list.setNestedScrollingEnabled(false);
        }

        clear_fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAllFavourites(id_str);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new HomeFragment()).commit();
                db.close();
                Toast.makeText(getContext(), "All your saved polls have been deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
