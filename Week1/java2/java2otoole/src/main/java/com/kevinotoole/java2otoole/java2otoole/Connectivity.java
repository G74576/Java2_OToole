package com.kevinotoole.java2otoole.java2otoole;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by kevinotoole on 6/4/14.
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

    //Get the connection type:
    public static String getConnectionType(Context context){
        connectionStatus(context);
        return connectionType;
    }

    //Get the network status:
    public static Boolean getNetworkStatus(Context context){
        connectionStatus(context);
        return connected;
    }

}
