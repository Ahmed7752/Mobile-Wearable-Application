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
    private Button comp_btn,logOut_btn,details_btn,vote_history_btn,clear_history_btn;
    private String id_str;
    private TextView prof,fb_name;

    private ImageView imageView;

    private CallbackManager callbackManager;
    private LoginButton login_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FacebookSdk.sdkInitialize(getActivity());
        DataBaseHelper db = new DataBaseHelper(getActivity());
        SharedPreferences userPrefs = getActivity().getSharedPreferences("CURRENT_USER_ID", 0);
        if (userPrefs.contains("currentUserID")) {
            id_str = userPrefs.getString("currentUserID", "id");
        }

        fb_name = view.findViewById(R.id.fb_name);

        imageView = view.findViewById(R.id.fb_profile_pic);

        prof = view.findViewById(R.id.textView2);
        comp_btn = view.findViewById(R.id.complete_btn);
        logOut_btn = view.findViewById(R.id.log_out_btn);
        details_btn = view.findViewById(R.id.user_details_btn);
        login_btn = view.findViewById(R.id.login_button);
        vote_history_btn = view.findViewById(R.id.vote_history_btn);
        clear_history_btn = view.findViewById(R.id.clear_all_votes_btn);


        if (db.checkComplete(id_str) == false){
            comp_btn.setVisibility(view.VISIBLE);
        }

        vote_history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new VotingHistoryFragment()).commit();
            }
        });

        details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new UserDetailsFragment()).commit();
            }
        });

        clear_history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteVotingHistory(id_str);
                Toast.makeText(getContext(), "All voting history has been deleted!", Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment()).commit();
            }
        });

        //create a callbackManager to handle login responses by calling
        callbackManager =   CallbackManager.Factory.create();

        login_btn.setPermissions(Arrays.asList("user_gender, user_friends"));

        login_btn.setFragment(this);

        //register a callback with either LoginManager
        login_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            //To respond to a login result
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
                if (db.checkComplete(id_str) == false) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new LaterProfileCompletionFragment()).commit();
                }else{

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment()).commit();

                    prof.setText("Profile has been completed");
                    getActivity().finish();

                }

            }
        });

        logOut_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = userPrefs.edit();
                editor.clear();
                editor.commit();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), UserRegLoginActivity.class);
                startActivity(intent);
            }
        });



       return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (FacebookSdk.isInitialized()) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        GraphRequest graphRequest = new GraphRequest().newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String id = object.getString("id");
                    fb_name.setText(name);
                    Picasso.get().load("https://graph.facebook.com/" + id +"/picture?type=large")
                            .into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("fields", "name,id,first_name,last_name");

        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null){
                LoginManager.getInstance().logOut();
                fb_name.setText("");
                imageView.setImageResource(0);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.startTracking();
    }
}
