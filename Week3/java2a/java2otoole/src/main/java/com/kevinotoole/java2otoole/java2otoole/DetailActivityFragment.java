package com.kevinotoole.java2otoole.java2otoole;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kevinotoole.java2otoole.java2otoole.imageLoader.ImageLoader;

/**
 * Created by kevinotoole on 6/18/14.
 */
public class DetailActivityFragment extends Fragment implements View.OnClickListener {

    ImageView profileImage, searchImage;
    TextView user_name, full_name;
    Button searchBtn;
    String imageLink;
    RatingBar ratingBar;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_view, container);

        //Set the search button:
        searchBtn = (Button) view.findViewById(R.id.button);

        //Set TextViews & ImageViews:
        user_name = (TextView) view.findViewById(R.id.detailUN);
        full_name = (TextView) view.findViewById(R.id.detailFN);
        profileImage = (ImageView) view.findViewById(R.id.detailPI);
        searchImage = (ImageView) view.findViewById(R.id.detailSI);

        //Set RatingBar:
        ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);

        return view;
    }

    public void displayResult(String userN, String fullN, String profI, String searchI, final String imageL){
        //Set the TextViews & ImageVies with retrieved data:
        user_name.setText(userN);
        full_name.setText(fullN);
        int loader = R.drawable.ic_launcher;
        String profImgUrl = profI;
        ImageLoader imageLoader = new ImageLoader(getActivity());
        imageLoader.DisplayImage(profImgUrl, loader, profileImage);
        String searchImgUrl = searchI;
        ImageLoader imageLoader1 = new ImageLoader(getActivity());
        imageLoader1.DisplayImage(searchImgUrl, loader, searchImage);
        imageLink = imageL;

        //Set onClickListener for Search Button:
        searchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Set implicit intent to view image in Instagram
        Intent searchLink = new Intent(Intent.ACTION_VIEW, Uri.parse(imageLink));
        startActivity(searchLink);
    }
}
