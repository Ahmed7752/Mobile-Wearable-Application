package androidev.main_app;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {

    private String topic_str, politics_str = "Politics",sports_str = "Sports",celeb_str = "Celebrities",movies_str = "Movies";
    private ListView live_polls_list, completed_poll_list;
    private TextView  completed_polls_text, live_polls_text, completed_polls_error_text, live_polls_error_text;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        DataBaseHelper db = new DataBaseHelper(getActivity());





        live_polls_list = view.findViewById(R.id.live_polls_list_view);
        completed_poll_list = view.findViewById(R.id.completed_polls_list_view);

        completed_polls_text = view.findViewById(R.id.completed_polls_text_view);
        live_polls_text = view.findViewById(R.id.live_polls_text_view);
        completed_polls_error_text = view.findViewById(R.id.completed_polls__error_text_view);
        live_polls_error_text = view.findViewById(R.id.live_polls_error_text_view);

        List<PollModel> models = db.getAllPollInterest(sports_str);

        if (models.isEmpty()){
            live_polls_error_text.setText("There are no polls available right now, please check back later.");
            live_polls_list.setNestedScrollingEnabled(false);

        }else {
            PollListAdapter pollsAdapter = new PollListAdapter(getActivity(), R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) models);
            live_polls_list.setAdapter(pollsAdapter);
        }

        if (models.isEmpty()){
            completed_polls_error_text.setText("There are no polls available right now, please check back later.");
            completed_poll_list.setNestedScrollingEnabled(false);

        }else {
            PollListAdapter pollsAdapter = new PollListAdapter(getActivity(), R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) models);
            completed_poll_list.setAdapter(pollsAdapter);
        }




        return view;
    }
}
