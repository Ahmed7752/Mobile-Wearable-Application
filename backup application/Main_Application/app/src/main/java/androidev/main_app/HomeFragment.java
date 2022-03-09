package androidev.main_app;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private String id_str, interest_str;
    private ImageButton politics_btn, sports_btn, celeb_btn, movies_btn;
    private ListView daily_list;
    private TextView daily_text;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        DataBaseHelper db = new DataBaseHelper(getActivity());
        SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_USER_ID", 0);

        if (userPrefs.contains("currentUserID")) {
            id_str = userPrefs.getString("currentUserID", "id");
        }


        daily_list = view.findViewById(R.id.daily_list_view);

        daily_text = view.findViewById(R.id.daily_polls_text_view);

        politics_btn = view.findViewById(R.id.politics_image_btn);
        sports_btn = view.findViewById(R.id.sports_image_btn);
        celeb_btn = view.findViewById(R.id.celeb_image_btn);
        movies_btn = view.findViewById(R.id.movies_image_btn);

        interest_str = db.findInterests(id_str);


        if (db.checkComplete(id_str) == true) {

            List<PollModel> models = db.getAllRequestedPolls(interest_str,"daily");

            if (models.isEmpty()){
                daily_text.setText("There are no poll suggestions for today, please check back tomorrow.");
                daily_list.setNestedScrollingEnabled(false);

            }else {
                PollListAdapter pollsAdapter = new PollListAdapter(getActivity(), R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) models);
                daily_list.setAdapter(pollsAdapter);
            }
        }else{
            daily_text.setText("You need to finish setting up your profile before we can suggest daily polls");
            daily_list.setNestedScrollingEnabled(false);
        }

        politics_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new PoliticalPollsFragment()).commit();
            }
        });

        sports_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new SportsPollsFragment()).commit();
            }
        });
        celeb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new CelebPollsFragment()).commit();
            }
        });
        movies_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new MoviePollsFragment()).commit();
            }
        });


        return view;
    }
}
