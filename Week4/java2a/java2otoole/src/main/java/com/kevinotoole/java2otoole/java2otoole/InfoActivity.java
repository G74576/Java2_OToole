package com.kevinotoole.java2otoole.java2otoole;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Author: Kevin OToole
 * Java 2 Term 1406
 * Week 4 Project
 * Project: USMC Instagram Photos
 * Package: com.kevinotoole.java2otoole.java2otoole;
 * File: InfoActivity.java
 * Purpose: This activity holds the information about the developer of this application:
 */
public class InfoActivity extends Activity implements View.OnClickListener{

    public Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoview);

        //Set close button
        closeBtn = (Button)findViewById(R.id.infoCloseBtn);
        closeBtn.setOnClickListener(this);

    }

    //Returns to MainActivity
    @Override
    public void onClick(View view) {
        finish();
    }
}
