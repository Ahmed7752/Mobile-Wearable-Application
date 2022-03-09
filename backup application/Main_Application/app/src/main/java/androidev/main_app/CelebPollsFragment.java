package androidev.main_app;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class CelebPollsFragment extends Fragment {

    private String celeb_str = "Celebrities";
    private Button show_polls_btn;
    private ListView celeb_list;
    private TextView  celeb_text;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_celeb_polls, container, false);

        DataBaseHelper db = new DataBaseHelper(getActivity());

        show_polls_btn = view.findViewById(R.id.show_all_polls);

        celeb_list = view.findViewById(R.id.celeb_list_view);

        celeb_text = view.findViewById(R.id.celeb_polls_text_view);


        List<PollModel> models = db.getAllPollInterest(celeb_str);

        if (models.isEmpty()){
            celeb_text.setText("There are no polls available right now, please check back later.");
            celeb_list.setNestedScrollingEnabled(false);

        }else {
            PollListAdapter pollsAdapter = new PollListAdapter(getActivity(), R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) models);
            celeb_list.setAdapter(pollsAdapter);
        }

        show_polls_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new AllPollsFragment()).commit();
            }
        });

        return view;
    }
}
