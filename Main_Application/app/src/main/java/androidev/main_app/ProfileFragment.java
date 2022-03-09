//This fragment class is used to display the user profile section.
package androidev.main_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class ProfileFragment extends Fragment {
    //All the required variables are declared here.
    private Button comp_btn,logOut_btn,details_btn,vote_history_btn,clear_history_btn;
    private String id_str;
    private TextView prof,fb_name;

    private ImageView imageView;

    private CallbackManager callbackManager;
    private LoginButton login_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //The celebs poll fragment layout is inflated into the view.
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //A constructor is used to create an object for the database helper class.
        DataBaseHelper db = new DataBaseHelper(getActivity());
        //The required shared preferences are called.
        SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_USER_ID", 0);
        //The value inside of the shared preference is stored as a string variable
        if (userPrefs.contains("currentUserID")) {
            id_str = userPrefs.getString("currentUserID", "id");
        }

        //Variables are matched with elements from the layout file.
        fb_name = view.findViewById(R.id.fb_name);

        imageView = view.findViewById(R.id.fb_profile_pic);

        prof = view.findViewById(R.id.textView2);
        comp_btn = view.findViewById(R.id.complete_btn);
        logOut_btn = view.findViewById(R.id.log_out_btn);
        details_btn = view.findViewById(R.id.user_details_btn);
        login_btn = view.findViewById(R.id.login_button);
        vote_history_btn = view.findViewById(R.id.vote_history_btn);
        clear_history_btn = view.findViewById(R.id.clear_all_votes_btn);


        //The method checkComplete is called from the database helper class  to check if the user
        //has completed setting up their account.
        if (db.checkComplete(id_str) == false){
            comp_btn.setVisibility(view.VISIBLE);
        }

        //An on click listener is set for vote history button.
        vote_history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the current fragment is replaced with the VotingHistoryFragment.
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new VotingHistoryFragment()).commit();
            }
        });

        //An on click listener is set for details button.
        details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the current fragment is replaced with the UserDetailsFragment.
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new UserDetailsFragment()).commit();
            }
        });

        //An on click listener is set for clear history button.
        clear_history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //using the deleteVotingHistory method from the database helper class
                // all voting history is deleted from the database for a specific user.
                db.deleteVotingHistory(id_str);
                //This message is toasted to the user.
                Toast.makeText(getContext(), "All voting history has been deleted!", Toast.LENGTH_SHORT).show();

            }
        });

        //create a callbackManager to handle login responses by calling
        callbackManager =   CallbackManager.Factory.create();

        //Login in with the following permission.(currently not used)
        login_btn.setPermissions(Arrays.asList("user_gender, user_friends"));

        //Because this used in a fragment, call loginButton.setFragment(this);
        login_btn.setFragment(this);

        //register a callback with either LoginManager
        login_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            //To respond to a login result
            //If login succeeds, the LoginResult parameter has the new AccessToken,
            // and the most recently granted or declined permissions.
            public void onSuccess(LoginResult loginResult) {
                Log.d("Test", "Login was successful");

            }

            @Override
            public void onCancel() {
                Log.d("Test", "Login was canceled");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Test", "Login error");

            }

        });



        comp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Using the CheckComplete method from the database helper class check if the
                // user has finished setting up their account.
                if (db.checkComplete(id_str) == false) {
                    //the current fragment is replaced with the LaterProfileCompletionFragment.
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new LaterProfileCompletionFragment()).commit();
                }else{

                    //the current fragment is replaced with the ProfileFragment.
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment()).commit();
                    //set the textView
                    prof.setText("Profile has been completed");
                    //destroy this activity by using the finish() method.
                    getActivity().finish();

                }

            }
        });

        //An on click listener is set for the logout button.
        logOut_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //The currentUserID Shared Preference is cleared
                SharedPreferences.Editor editor = userPrefs.edit();
                editor.clear();
                editor.commit();
                //The current activity is destroyed
                getActivity().finish();
                //This intent starts the UserRegLogin Activity
                Intent intent = new Intent(getActivity(), UserRegLoginActivity.class);
                startActivity(intent);
            }
        });



       return view;
    }



    //The method on onActivityResult is called when the Facebook activity is exited.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Check if the facebook sdk is initialised.
        if (FacebookSdk.isInitialized()) {
            //call callbackManager.onActivityResult to pass the login results to the
            // LoginManager via callbackManager.
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        //Using facebook graph API to get data from Facebook.

        //To request data from Facebook we use the graphRequest class

        //A newMeRequest() is used to retrieve A user’s own profile

        //The access token and a graphJSONObjectCallBack is passed
        // (Which is a callback for requests that results in a JSON object).
        GraphRequest graphRequest = new GraphRequest().newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                //The JSONObject will contain the fields listed in the
                // bundle that was set with the request.
                try {
                    //From the object the user's name is retrieved and stored as a string variable.
                    String name = object.getString("name");
                    //From the objects the user’s id is retrieved and stored in
                    // string variable, which will be used to retrieve the user's profile.
                    String id = object.getString("id");
                    //The textView for the user's name is set.
                    fb_name.setText(name);
                    //Picasso is a powerful Image Downloader and caching library and is
                    // used to display the user's profile picture.

                    //To get a Facebook users profile picture the following
                    // URL “https://graph.facebook.com/" + id +"/picture?type=large”  must be used
                    Picasso.get().load("https://graph.facebook.com/" + id +"/picture?type=large")
                            //Load image into the imageView.
                            .into(imageView);
                } catch (JSONException e) {
                    //A JSONException is caught.
                    e.printStackTrace();
                }

            }
        });

        //The bundle class is called to pass data to the Facebook activity.
        Bundle bundle = new Bundle();
        //The Facebook activity requires the key to be “fields”
        //Here we are requesting the full name of the user, there ID, there first name
        // and last name.
        bundle.putString("fields", "name,id,first_name,last_name");


        graphRequest.setParameters(bundle);
        //By calling executeAsync()  the Graph API request will be run
        // on a separate thread in the background.
        graphRequest.executeAsync();
    }

    //The AccessTokenTracker class is used to track the access token.
    // Whenever the access token changes the method onCurrentAccessTokenChanged()
    // is automatically called.
    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null){
                //The login manager class managers login and permissions on Facebook.
                // The getInstance() method is the getter for the login manager
                // it (returns the login manager).
                LoginManager.getInstance().logOut();
                //When a user logs out clear the textView and the imageView.
                fb_name.setText("");
                imageView.setImageResource(0);
            }
        }
    };

    //The onDestroy() method is the final call you receive before your activity is destroyed.
    @Override
    public void onDestroy() {
        super.onDestroy();
        //access token tracking is stopped
        accessTokenTracker.stopTracking();
    }
}
