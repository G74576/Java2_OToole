package com.kevinotoole.java2otoole.java2otoole;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Author: Kevin OToole
 * Java 2 Term 1406
 * Week 3 Project
 * Project: USMC Instagram Photos
 * Package: com.kevinotoole.java2otoole.java2otoole;
 * File: Connectivity.java
 * Purpose: This purpose of this java file is to check the connectivity of the device which is
 *          checked when the user clicks the browse button on the MainActivity page.
 */
public class Connectivity {

    static Boolean connected = false;
    static String connectionType;
    static NetworkInfo networkInfo;
    static String TAG = "NETWORK DATA - CONNECTION";

    //Check the connection of the device:
    public static void connectionStatus(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null){
            if (networkInfo.isConnected()){
                connectionType = networkInfo.getTypeName();
                Log.i(TAG, R.string.connType + " " + networkInfo.getTypeName());

                connected = true;
            }
        }
    }

    //Get the network status:
    public static Boolean getNetworkStatus(Context context){
        connectionStatus(context);
        return connected;
    }

}
