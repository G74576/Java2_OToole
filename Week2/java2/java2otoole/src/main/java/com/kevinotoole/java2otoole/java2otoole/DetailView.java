package com.kevinotoole.java2otoole.java2otoole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kevinotoole.java2otoole.java2otoole.imageLoader.ImageLoader;

/**
 * Kevin OToole
 * Java 2 Term 1406
 * Week 2 Project
 */
public class DetailView extends Activity {
    public String userName;
    public String fullName;
    public String profImg;
    public String searchImg;
    public String imgLink;
    ImageView profileImage, searchImage;
    TextView user_name, full_name;
    Button searchBtn;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);

        //Set TextViews & ImageViews:
        user_name = (TextView) findViewById(R.id.detailUN);
        full_name = (TextView) findViewById(R.id.detailFN);
        profileImage = (ImageView) findViewById(R.id.detailPI);
        searchImage = (ImageView) findViewById(R.id.detailSI);

        //Retrieve data from Main Activity:
        Intent intent = this.getIntent();
        userName = intent.getStringExtra("USERNAME_KEY");
        fullName = intent.getStringExtra("FULLNAME_KEY");
        profImg = intent.getStringExtra("PROFILEIMG_KEY");
        searchImg = intent.getStringExtra("SEARCHIMG_KEY");
        imgLink = intent.getStringExtra("LINK_KEY");

        //Set the TextViews & ImageVies:
        user_name.setText(userName);
        full_name.setText(fullName);
        int loader = R.drawable.ic_launcher;
        String profImgUrl = profImg;
        ImageLoader imageLoader = new ImageLoader(getApplicationContext());
        imageLoader.DisplayImage(profImgUrl, loader, profileImage);
        String searchImgUrl = searchImg;
        ImageLoader imageLoader1 = new ImageLoader(getApplicationContext());
        imageLoader1.DisplayImage(searchImgUrl, loader, searchImage);

    }
}