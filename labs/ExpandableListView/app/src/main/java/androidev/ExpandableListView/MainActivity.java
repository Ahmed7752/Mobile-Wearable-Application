package androidev.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> mobileCollection;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        groupList = new ArrayList<>();
        createGroupList();
        createCollection();
        expandableListView = findViewById(R.id.expandable_mobiles);
        expandableListAdapter = new MyExpandableListAdapter(this, groupList, mobileCollection);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);

                }
                lastExpandedPosition = i;

            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i, i1).toString();
                Toast.makeText(MainActivity.this, "selected: "+ selected, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void createCollection() {
        String[] dailyModels = {"D1", "D2","D3", "D4","D5"};

        mobileCollection = new HashMap<String, List<String>>();
        for (String group : groupList){
            if (group.equals("Daily Polls")){
                loadChild(dailyModels);
            }mobileCollection.put(group, childList);

        }
    }

    private void loadChild(String[] mobileModels) {
        childList = new ArrayList<>();
        for (String model : mobileModels){
            childList.add(model);
        }
    }

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("Daily Polls");

    }
}