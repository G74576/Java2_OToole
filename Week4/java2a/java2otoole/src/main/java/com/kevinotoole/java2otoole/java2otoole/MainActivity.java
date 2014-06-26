package com.kevinotoole.java2otoole.java2otoole;
/**
 * Author: Kevin OToole
 * Java 2 Term 1406
 * Week 4 Project
 * Project: USMC Instagram Photos
 * Package: com.kevinotoole.java2otoole.java2otoole;
 * File: MainActivity.java
 * Purpose: This is the main activity of this application which launches on the start of the app.
 *          From this page, users can click the button to search through recent photos that were
 *          tagged on the Instagram Site with the tag "USMC".  Users can then click on any of the
 *          list items to take them to a detail view of that item.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends Activity implements MainActivityFragment.OnListItemClicked{

    // Check connection:
    public boolean connected = false;

    //Global Variables:
    public static Context mContext;
    FileManager fileManager;
    public static String fileName = "JSONData.txt";
    public static ListView listView;
    public static String url = "https://api.instagram.com/v1/tags/USMC/media/recent?access_token=188207900.f59def8.726418d4d14945898ae397a2eca002de";
    String detail_userName, detail_searchImage;
    Float detail_rating;
    public static String ratingForFile;
    public static ArrayList<String> ratingArray = new ArrayList<String>();

    static MainActivityFragment fragment;

    ArrayList<UserInfo> userList = new ArrayList<UserInfo>();

    public CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main_fragment);

        fragment = (MainActivityFragment)getFragmentManager().findFragmentById(R.id.mainFragment);

        listView = (ListView)findViewById(R.id.listView);

        //Set instance of FileManger:
        fileManager = FileManager.getInstance();

        //If there is a username in the SharedPreferences:
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String prefUN = preferences.getString("USERNAME", null);
        TextView loggedUser = (TextView)findViewById(R.id.preferencesName);
        if (prefUN != null){
            loggedUser.setText("Welcome " + prefUN);
            loggedUser.setVisibility(View.VISIBLE);
        }
        else {
            loggedUser.setVisibility(View.GONE);
        }

        //Check whether we are recreating a previously destroyed instance:
        if (savedInstanceState != null) {
            Log.d("MAIN", "Saved instance");

            userList = (ArrayList<UserInfo>)savedInstanceState.getSerializable("saved");
            if (userList != null){
                customAdapter = new CustomAdapter(this, R.layout.list_row, userList);
                listView.setAdapter(customAdapter);
            }
            else{

                Log.d("MAIN", "Saved = null");
            }
        }
        else{
            Log.d("MAIN", "no Saved instance");
        }

        connected = Connectivity.getNetworkStatus(mContext);
        if (!connected){
            //Alert if not connected:
            AlertDialog.Builder noConnection = new AlertDialog.Builder(mContext);
            noConnection.setMessage(getString(R.string.noConnection))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.okBtn), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertDialog = noConnection.create();
            alertDialog.show();
        }
        else{

        }
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
                    fragment.getFragmentData();
                }else{
                    Log.i("HANDLER MESSAGE", "Data not created");
                }
            }
        }
    }

    public void startResultActivity(String userN, String fullN, String profI, String searchI, String imageL, String likeC){
        //Send information to be used in Detail View
        Intent detailView = new Intent(mContext, DetailView.class);
        detailView.putExtra("USERNAME_KEY", userN);
        detailView.putExtra("FULLNAME_KEY", fullN);
        detailView.putExtra("PROFILEIMG_KEY", profI);
        detailView.putExtra("SEARCHIMG_KEY", searchI);
        detailView.putExtra("LINK_KEY", imageL);
        detailView.putExtra("LIKE_KEY", likeC);
        startActivityForResult(detailView, 0);
    }

    @Override
    public void onListItemClicked(String un, String fn, String pi, String si, String il, String lc) {

        DetailActivityFragment detailActivityFragmentfragment = (DetailActivityFragment) getFragmentManager().findFragmentById(R.id.detailFragment);

        if (detailActivityFragmentfragment != null && detailActivityFragmentfragment.isInLayout()){
            //Display results from intent:
            detailActivityFragmentfragment.displayResult(un, fn, pi, si, il, lc);
        }
        else {
            startResultActivity(un, fn, pi, si, il, lc);
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.i("MAIN ACTIVITY", "onActivityResult()");

        if (resultCode == RESULT_OK && requestCode == 0){
            if (data.hasExtra("detailUserName") && data.hasExtra("detailsSearchImage") && data.hasExtra("detailRating")){
                detail_userName = data.getExtras().getString("detailUserName");
                detail_searchImage = data.getExtras().getString("detailsSearchImage");
                detail_rating = data.getExtras().getFloat("detailRating");

                ratingForFile = ("Username: " + detail_userName + " Rating: " + detail_rating.toString());
                ratingArray.add(ratingForFile);

//                File rateFile = new File(ratingFileName);
//                if (rateFile.exists()==false){
//                    //rateFile.createNewFile();
//                    fileManager.WriteFileToString(mContext, ratingFileName, ratingForFile);
//                    //rateFile.createNewFile();
//                }else {
//                    fileManager.WriteToFileString(mContext, ratingFileName, ratingFileName, true);
//                }
////                    FileWriter fileWriter = new FileWriter(ratingFileName,true);
////                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
////                    bufferedWriter.write(ratingForFile + "\n");
////                    bufferedWriter.newLine();
////                    bufferedWriter.close();
////                    PrintWriter out = new PrintWriter(rateFile);
////                    out.append(ratingForFile + "\n");
////                    out.close();
//                //                fileManager.WriteFileToString(mContext, ratingFileName, ratingForFile);


                //Alert of rating of image from detail view:
                AlertDialog.Builder ratingAlert = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.alert_dialog, null);
                TextView headerText = (TextView)view.findViewById(R.id.dialogheader);
                ratingAlert.setCustomTitle(view);
                headerText.setText(getString(R.string.ratinga) + " " + detail_userName + getString(R.string.ratingb));
                TextView messageText = (TextView)view.findViewById(R.id.dialogMessage);
                messageText.setText(getString(R.string.ratingc) + " " + detail_rating);
                        ratingAlert.setCancelable(false)
                        .setPositiveButton(getString(R.string.ratingBtn), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = ratingAlert.create();
                alertDialog.show();

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (userList != null && !userList.isEmpty()){
            outState.putSerializable("saved", userList);
            Log.i("MAIN", "Saving instance state data");
        }
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
            savedInstanceState.getSerializable("saved");

            Log.i("MAIN", "Restoring instance state data");
    }

    public void launchPrefDialog(AlertDialogFragment.DialogType type){
        AlertDialogFragment dialogFragment = AlertDialogFragment.newInstance(type);
        dialogFragment.show(getFragmentManager(), "PREFERENCES");
    }

    public void launchSearchDialog(AlertDialogFragment.DialogType type){
        AlertDialogFragment dialogFragment = AlertDialogFragment.newInstance(type);
        dialogFragment.show(getFragmentManager(), "SEARCH");
    }

    public void launchFavoritesDialog(AlertDialogFragment.DialogType type){
        AlertDialogFragment dialogFragment = AlertDialogFragment.newInstance(type);
        dialogFragment.show(getFragmentManager(), "FAVORITES");
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
        switch (item.getItemId()){
            case R.id.search_menu_item:
                launchSearchDialog(AlertDialogFragment.DialogType.SEARCH);
                break;

            case R.id.info_menu_item:
                Intent infoIntent = new Intent(mContext, InfoActivity.class);
                mContext.startActivity(infoIntent);
                break;

            case R.id.favorites_menu_item:
                launchFavoritesDialog(AlertDialogFragment.DialogType.FAVORITES);
                break;

            case R.id.preferences_menu_item:
                launchPrefDialog(AlertDialogFragment.DialogType.PREFERENCES);
                break;

        }
        return true;
    }
}
