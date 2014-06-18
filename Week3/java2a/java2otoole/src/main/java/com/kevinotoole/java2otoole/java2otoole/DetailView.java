package com.kevinotoole.java2otoole.java2otoole;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kevinotoole.java2otoole.java2otoole.imageLoader.ImageLoader;

/**
 * Author: Kevin OToole
 * Java 2 Term 1406
 * Week 3 Project
 * Project: USMC Instagram Photos
 * Package: com.kevinotoole.java2otoole.java2otoole;
 * File: DetailView.java
 * Purpose: This is the detail view page which was launched when the user clicked on an item in the
 *          listview from the MainActivity page.  Here users can see a large image of the item
 *          that was selected as well as information about the owner of the image.  Users can also
 *          rate the image from 0-5 stars, and can also view the image on the instagram website by
 *          clicking the button.
 */
public class DetailView extends Activity {
    public String userName;
    public String fullName;
    public String profImg;
    public String searchImg;
    public String imgLink;

    RatingBar ratingBar;
    Float ratingSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view_fragment);

        //Retrieve data from Main Activity:
        Intent intent = this.getIntent();
        userName = intent.getStringExtra("USERNAME_KEY");
        fullName = intent.getStringExtra("FULLNAME_KEY");
        profImg = intent.getStringExtra("PROFILEIMG_KEY");
        searchImg = intent.getStringExtra("SEARCHIMG_KEY");
        imgLink = intent.getStringExtra("LINK_KEY");

        DetailActivityFragment fragment = (DetailActivityFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
        //Display results from intent:
        fragment.displayResult(userName, fullName, profImg, searchImg, imgLink);

        ratingBar = (RatingBar)findViewById(R.id.ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean user) {
                ratingSelected = ratingBar.getRating();
            }
        });


    }


    @Override
    public void finish() {
        Log.i("DETAILS ACTIVITY", "finish");

        Intent detailsBackIntent = new Intent();
        detailsBackIntent.putExtra("detailUserName", userName);
        detailsBackIntent.putExtra("detailsSearchImage", searchImg);
        detailsBackIntent.putExtra("detailRating", ratingSelected);

        setResult(RESULT_OK, detailsBackIntent);

        super.finish();
    }
}
