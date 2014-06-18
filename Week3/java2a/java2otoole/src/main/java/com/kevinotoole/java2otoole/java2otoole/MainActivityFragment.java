package com.kevinotoole.java2otoole.java2otoole;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kevinotoole on 6/17/14.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener {

    private static FileManager fileManager;
    public static String fileName = "JSONData.txt";
    public static ListView listView;
    Button searchButton;
    public static String url; // "https://api.instagram.com/v1/tags/USMC/media/recent?access_token=188207900.f59def8.726418d4d14945898ae397a2eca002de";

    ArrayList<UserInfo> userList = new ArrayList<UserInfo>();

    JSONArray users = null;
    CustomAdapter customAdapter;

    private static final String TAG_DT = "data";
    private static final String TAG_UN = "username";
    private static final String TAG_PI = "profile_picture";
    private static final String TAG_FN = "full_name";
    private static final String TAG_UI = "url";
    private static final String TAG_LI = "link";

    private onListItemClicked parentActivity;


    public interface onListItemClicked{
        void startResultActivity(String un, String fn, String pi, String si, String il);
        void getData();
        ArrayList<UserInfo> userList = new ArrayList<UserInfo>();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof onListItemClicked){
            parentActivity = (onListItemClicked) activity;
        }
        else {
            throw new ClassCastException(activity.toString() + " must implement onListItemClicked");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container);

        listView = (ListView)view.findViewById(R.id.listView);
        searchButton = (Button)view.findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        userList.clear();
        url = "https://api.instagram.com/v1/tags/usmc/media/recent?access_token=188207900.f59def8.726418d4d14945898ae397a2eca002de";
       // displayDataFromFile();
        parentActivity.getData();
    }

    //Parse JSON Data to display in ListView
    public  void displayDataFromFile(){
        //Pull json from DDMS
        fileManager = FileManager.getInstance();

        String JSONString = fileManager.readStringFromFile(getActivity(), fileName);


        try {
            JSONObject jsonObject = new JSONObject(JSONString);
            users = jsonObject.getJSONArray(TAG_DT);
            for (int i = 0; i < users.length(); i++){
                JSONObject c = users.getJSONObject(i);
                UserInfo results = new UserInfo();
                results.user_name = c.getJSONObject("user").getString(TAG_UN);
                results.full_name = c.getJSONObject("user").getString(TAG_FN);
                results.prof_url = c.getJSONObject("user").getString(TAG_PI);
                results.img_url = c.getJSONObject("images").getJSONObject("standard_resolution").getString(TAG_UI);
                results.img_link = c.getString(TAG_LI);
                userList.add(results);
            }

            //Custom Adapter:
            customAdapter = new CustomAdapter(getActivity(), R.layout.list_row, userList);
            listView.setAdapter(customAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                String username, fullname, profimage, searchimage, imagelink;

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long row) {
                    username = userList.get(position).user_name;
                    fullname = userList.get(position).full_name;
                    profimage = userList.get(position).prof_url;
                    searchimage = userList.get(position).img_url;
                    imagelink = userList.get(position).img_link;

                    parentActivity.startResultActivity(username, fullname, profimage, searchimage, imagelink);
                }
            });

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void getFragmentData(){
        displayDataFromFile();
    }
}
