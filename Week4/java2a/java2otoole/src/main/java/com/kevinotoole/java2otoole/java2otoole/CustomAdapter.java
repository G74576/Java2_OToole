package com.kevinotoole.java2otoole.java2otoole;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevinotoole.java2otoole.java2otoole.imageLoader.ImageLoader;

import java.util.ArrayList;

/**
 * Author: Kevin OToole
 * Java 2 Term 1406
 * Week 4 Project
 * Project: USMC Instagram Photos
 * Package: com.kevinotoole.java2otoole.java2otoole;
 * File: CustomAdapter.java
 * Purpose: This is the custom adapter class for the listview:
 */
public class CustomAdapter extends ArrayAdapter<UserInfo>{

    public ArrayList<UserInfo> userList;
    //public ArrayList<UserInfo> newlist;

    public CustomAdapter(Context context, int resource, ArrayList<UserInfo> userList) {
        super(context, resource, userList);
        this.userList = userList;
//        this.newlist = new ArrayList<UserInfo>();
//        this.newlist.addAll(userList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_row, null);
        }


        UserInfo items = userList.get(position);
        if (items != null) {
            TextView uName = (TextView) view.findViewById(R.id.userName);
            TextView fName = (TextView) view.findViewById(R.id.fullName);
            ImageView uImage = (ImageView) view.findViewById(R.id.userImage);
            ImageView sImage = (ImageView) view.findViewById(R.id.searchImage);

            uName.setText("User Name: " + "  " + items.user_name);
            fName.setText(items.full_name);
            int loader = R.drawable.ic_launcher;
            String imgUrl = items.prof_url;
            ImageLoader imageLoader = new ImageLoader(getContext());
            imageLoader.DisplayImage(imgUrl, loader, uImage);
            String schUrl = items.img_url;
            ImageLoader imageLoader1 = new ImageLoader(getContext());
            imageLoader1.DisplayImage(schUrl, loader, sImage);
        }
        return view;
    }

//    public void search(String searchString){
//        searchString = searchString.toLowerCase();
//
//        userList.clear();
//
//        if (searchString.length() == 0){
//            userList.addAll(newlist);
//        }
//        else {
//            for (UserInfo userInfo : newlist){
//                if (userInfo.getFull_name().toLowerCase().contains(searchString)){
//                    userList.add(userInfo);
//                }
//            }
//        }
//        if (userList.isEmpty()){
//            userList.addAll(newlist);
//        }
//        notifyDataSetChanged();
//    }
}

