package com.kevinotoole.java2otoole.java2otoole;
/**
 * Kevin O'Toole
 * Java 2 Term 1406
 * Week 1 Project
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.kevinotoole.java2otoole.java2otoole.imageLoader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;
//import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {

    // Check connection:
    public boolean connected = false;

    //Global Variables:
    public static Context mContext;
    private static FileManager fileManager;
    public static String fileName = "JSONData.txt";
    public static ListView listView;
    EditText searchField;
    Button searchButton;
    public static String url; // "https://api.instagram.com/v1/tags/USMC/media/recent?access_token=188207900.f59def8.726418d4d14945898ae397a2eca002de";



    ArrayList<UsersInfo> userList = new ArrayList<UsersInfo>();

    class UsersInfo {
        public String user_name;
        public String full_name;
        public String prof_url;
        public String img_url;
    }

    private static final String TAG_DT = "data";
    private static final String TAG_UN = "username";
    private static final String TAG_PI = "profile_picture";
    private static final String TAG_FN = "full_name";
    private static final String TAG_UI = "url";

    JSONArray users = null;
    CustomAdapter customAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        //Set instance of FileManger:
        fileManager = FileManager.getInstance();

        //Edit text field:
        searchField = (EditText) findViewById(R.id.searchText);

        //Get Search Button by ID:
        searchButton = (Button) findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check for connection:
                connected = Connectivity.getNetworkStatus(mContext);
                if (!connected){
                    //Alert if not connected:
                    AlertDialog.Builder noConnection = new AlertDialog.Builder(mContext);
                    noConnection.setMessage(getString(R.string.noConnection)).setCancelable(false).setPositiveButton(getString(R.string.okBtn), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = noConnection.create();
                    alertDialog.show();
                }
                else if (connected){
                    //Toast.makeText(this, "You are connected to " + Connectivity.getConnectionType(this), Toast.LENGTH_LONG).show();
                    //Do something!!!!!!
                    if (searchField.getText().length() == 0){
                        searchField.setError("Please enter a keyword to search.");
                    }else if (searchField.getText().toString().contains(" ")){
                        searchField.setError("No spaces allowed in search field");
                    }else{
                        userList.clear();
                        url = "https://api.instagram.com/v1/tags/" + searchField.getText().toString() + "/media/recent?access_token=188207900.f59def8.726418d4d14945898ae397a2eca002de";
                    }
                    getData();
                    //displayDataFromFile();
                }
//                final Handler messageHandler = new Handler() {
//                    public void handleMessage(Message msg){
//                        Object returnObject = msg.obj;
//                        Log.i("HANDLE MESSAGE", "Handled");
//                        if (msg.arg1 == RESULT_OK && msg.obj != null){
//                            Log.i("HANDLE MESSAGE", "Success");
//                            displayDataFromFile();
//                        }
//                    }
//
//                    @Override
//                    public void close() {
//
//                    }
//
//                    @Override
//                    public void flush() {
//
//                    }
//
//                    @Override
//                    public void publish(LogRecord logRecord) {
//
//                    }
//                };

            }
        });

    }


    public void getData(){
        final MyHandler myHandler = new MyHandler(this);

        Messenger messenger = new Messenger(myHandler);

        Intent getEventsIntent = new Intent(mContext, MyService.class);
        getEventsIntent.putExtra(MyService.MESSENGER_KEY, messenger);
        getEventsIntent.putExtra(MyService.URL_STRING, url);
        startService(getEventsIntent);

    }

    //Private static class for the Handler to prevent context leaks:
    public static class MyHandler extends Handler {
        //Create Weak Reference:
        private final WeakReference<MainActivity> myActivity;

        public MyHandler(MainActivity activity){
            myActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg){
            MainActivity activity = myActivity.get();
            if (activity != null){
                Object returnedObject = msg.obj;
                if (msg.arg1 == RESULT_OK && returnedObject != null){
                    Log.i("HANDLER MESSAGE", "handleMessage()");
                    activity.displayDataFromFile();
                }else{
                    Log.i("HANDLER MESSAGE", "Data not created");
                }
            }
        }
    }


    public  void displayDataFromFile(){
        //Pull json from DDMS
        fileManager = FileManager.getInstance();

        String JSONString = fileManager.readStringFromFile(mContext, fileName);


        try {
            JSONObject jsonObject = new JSONObject(JSONString);
            users = jsonObject.getJSONArray(TAG_DT);
            for (int i = 0; i < users.length(); i++){
                JSONObject c = users.getJSONObject(i);
                UsersInfo results = new UsersInfo();
                results.user_name = c.getJSONObject("user").getString(TAG_UN);
                results.full_name = c.getJSONObject("user").getString(TAG_FN);
                results.prof_url = c.getJSONObject("user").getString(TAG_PI);
                results.img_url = c.getJSONObject("images").getJSONObject("standard_resolution").getString(TAG_UI);
                userList.add(results);
            }

            //Custom Adapter:
            customAdapter = new CustomAdapter();
            listView.setAdapter(customAdapter);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    class CustomAdapter extends ArrayAdapter<UsersInfo> {
        CustomAdapter(){
            super(MainActivity.this, R.layout.list_row, userList);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            ViewHolder holder;
            if (convertView == null){
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.list_row, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.populateFrom(userList.get(position));
            return (convertView);
        }
    }

    class ViewHolder{
        public TextView uName = null;
        public ImageView uImage = null;
        public ImageView sImage = null;
        public TextView fName = null;

        ViewHolder(View row){
            uName = (TextView)row.findViewById(R.id.userName);
            fName = (TextView)row.findViewById(R.id.fullName);
            uImage = (ImageView)row.findViewById(R.id.userImage);
            sImage = (ImageView)row.findViewById(R.id.searchImage);

        }
        void populateFrom(UsersInfo r){
            uName.setText("User Name: " + "  " + r.user_name);
            fName.setText(r.full_name);
            int loader = R.drawable.ic_launcher;
            String imgUrl = r.prof_url;
            ImageLoader imageLoader = new ImageLoader(getApplicationContext());
            imageLoader.DisplayImage(imgUrl, loader, uImage);
            String schURL = r.img_url;
            ImageLoader imageLoader1 = new ImageLoader(getApplicationContext());
            imageLoader1.DisplayImage(schURL, loader, sImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
