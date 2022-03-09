//This fragment class is used to display A list of all Sports polls.
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

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class SportsPollsFragment extends Fragment {
    //All the required variables are declared here.
    private String sports_str = "Sports";
    private Button show_polls_btn;
    private ListView sports_list;
    private TextView  sports_text;

    //The setNestedScrollingEnabled property requires level 21 API to function.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The celebs poll fragment layout is inflated into the view.
        View view = inflater.inflate(R.layout.fragment_sports_polls, container, false);

        //A constructor for the database helper class is created.
        DataBaseHelper db = new DataBaseHelper(getActivity());

        //Variables are matched with elements from the layout file.
        show_polls_btn = view.findViewById(R.id.show_all_polls);

        sports_list = view.findViewById(R.id.sports_list_view);

        sports_text = view.findViewById(R.id.sports_polls_text_view);

        //The getAllPollInterest method from the database helper class is
        // used to build a list containing poll objects using the poll model class.
        List<PollModel> models = db.getAllPollInterest(sports_str);

        //If the List containing objects is empty the text view is set to display this
        // message, the list view element is no longer scrollable.
        if (models.isEmpty()){
            sports_text.setText("There are no polls available right now, please check back later.");
            sports_list.setNestedScrollingEnabled(false);

        }else {
            //A contractor is created for the custom list adapter class, it is given the custom
            // list adapter layout file and the array list containing poll objects retrieved
            // from the database.
            PollListAdapter pollsAdapter = new PollListAdapter(getActivity(), R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) models);
            sports_list.setAdapter(pollsAdapter);
        }

        //An on click listener is set for the show polls button.
        show_polls_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Once clicked the current fragment is replaced with the all polls fragment.
                FragmentTransaction fragmentTransaction =getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new AllPollsFragment()).commit();
            }
        });


        return view;
    }

}
