package com.kevinotoole.java2otoole.java2otoole;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Author: Kevin OToole
 * Java 2 Term 1406
 * Week 3 Project
 * Project: USMC Instagram Photos
 * Package: com.kevinotoole.java2otoole.java2otoole;
 * File: MyService.java
 * Purpose:
 */
public class MyService extends IntentService{

    //Global Variables:
    public static final String MESSENGER_KEY = "messenger";
    public static final String URL_STRING = "urlString";
    public Boolean success;
    //public URL url_String = null;

    FileManager fileManager;


    public MyService() {
        super("MyService");
    }

    //Establish onHandle Intent:
    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        Messenger messenger = (Messenger) extras.get(MESSENGER_KEY);
        String urlString = extras.getString(URL_STRING);
        String response = "";

        try {
            URL url = new URL(urlString);
            response = getResponse(url);
            if (!(response.equals("Error retrieving data"))){
                fileManager = FileManager.getInstance();
                success = fileManager.WriteFileToString(MainActivity.mContext, MainActivity.fileName, response);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("ON HANDLE INTENT", "Error with URL");
        }

        Message msg = Message.obtain();
        msg.arg1 = Activity.RESULT_OK;
        msg.obj = response;

        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e("ON HANDLE INTENT", "Error with sending message");
        }
    }

    public static String getResponse(URL url) {
        String response;
        try {
            URLConnection connection = url.openConnection();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            byte[] contextByte = new byte[1024];
            int bytesRead;
            StringBuilder stringBuffer = new StringBuilder();
            while ((bytesRead = bufferedInputStream.read(contextByte)) != -1) {
                response = new String(contextByte, 0, bytesRead);
                stringBuffer.append(response);
            }
            response = stringBuffer.toString();
        } catch (IOException e){
            response = "Error retrieving data";
            Log.e("GET RESPONSE", e.toString());
        }
        return response;
    }
}
