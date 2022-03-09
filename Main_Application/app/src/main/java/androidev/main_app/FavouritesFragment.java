//This fragment class is used to display A list of all favourited polls by the user.
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
    //All the required variables are declared here.
    private String id_str;
    private ListView poll_list;
    private TextView poll_text;
    private Button clear_fav_btn;

    //The setNestedScrollingEnabled property requires level 21 API to function.
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The fragment_favourites layout is inflated into the view.
        View v = inflater.inflate(R.layout.fragment_favourites, container, false);

        //A constructor for the database helper class is created.
        DataBaseHelper db = new DataBaseHelper(getActivity());
        //The required Shared Preferences are called and the vault is stored in a String variable.
        SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_USER_ID", 0);

        if (userPrefs.contains("currentUserID")) {
            id_str = userPrefs.getString("currentUserID", "id");
        }
        //Variables are matched with elements from the layout file.
        clear_fav_btn = v.findViewById(R.id.clear_favourites_btn);

        poll_list = v.findViewById(R.id.poll_list_view);

        poll_text = v.findViewById(R.id.polls_text_view);
        //The method checkComplete is called from the database helper class  to check if the user
        //has completed setting up their account.
        if (db.checkComplete(id_str) == true) {

            //The getPolls method from the database helper class is used to build a list
            // containing poll objects using the poll model class.
            List<PollModel> models = db.getPolls(id_str);

            //If the List containing objects is empty the text view is set to
            // display this message, the list view element is no longer scrollable.
            if (models.isEmpty()){
                poll_text.setText("There are no poll that you have saved yet.");
                poll_list.setNestedScrollingEnabled(false);

            }else {
                //A contractor is created for the custom list adapter, it is given the
                // custom list adapter layout file and the array list containing poll
                // objects retrieved from the database.
                PollListAdapter pollsAdapter = new PollListAdapter(getActivity(), R.layout.poll_list_adapter_layout, (ArrayList<PollModel>) models);
                poll_list.setAdapter(pollsAdapter);
            }
        }else{
            //If the user has not completed setting up their account this text view is set with
            //this message
            poll_text.setText("You need to finish setting up your profile before you can save polls");
            poll_list.setNestedScrollingEnabled(false);
        }

        //An on click listener is set on the clear favourite button.
        clear_fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The deleteAllFavourites method from the database helper class is called
                // and is given the current users ID as an argument.
                db.deleteAllFavourites(id_str);
                //The current fragment is replaced with the home fragment.
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new HomeFragment()).commit();
                //The database is then closed.
                db.close();
                //This message is toasted to the user.
                Toast.makeText(getContext(), "All your saved polls have been deleted!", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
