package com.kevinotoole.java2otoole.java2otoole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by kevinotoole on 6/26/14.
 */
public class InfoActivity extends Activity implements View.OnClickListener{

    public Button closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoview);

        closeBtn = (Button)findViewById(R.id.infoCloseBtn);
        closeBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
