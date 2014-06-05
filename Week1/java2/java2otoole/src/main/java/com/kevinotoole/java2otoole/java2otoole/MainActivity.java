package com.kevinotoole.java2otoole.java2otoole;
/**
 * Kevin O'Toole
 * Java 2 Term 1406
 * Week 1 Project
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity {

    public boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check for connection:
        connected = Connectivity.getNetworkStatus(this);
        if (!connected){
            //Alert if not connected:
            AlertDialog.Builder noConnection = new AlertDialog.Builder(this);
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
