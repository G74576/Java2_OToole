package com.kevinotoole.java2otoole.java2otoole;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Author: Kevin OToole
 * Java 2 Term 1406
 * Week 4 Project
 * Project: USMC Instagram Photos
 * Package: com.kevinotoole.java2otoole.java2otoole;
 * File: JSONParser.java
 * Purpose: The purpose of this page is to Parse the URL from the MainActivity page into JSON from
 *          which I can then pull information from the JSON Object to display in the application.
 */
public class JSONParser {

    static InputStream inputStream = null;
    static JSONObject jsonObject = null;
    static String json = "";

    //Constructor
    public JSONParser() {

    }

    public JSONObject getJSONFromUrl(String url){
        //Make HTTP Request
        try {
            //Default HTTP Client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(get);
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8559-1"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            inputStream.close();
            json = stringBuilder.toString();
        } catch (Exception e){
            Log.e("Buffer Error", "Error converting result" + e.toString());
        }

        //try to parse string to JSON Object
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e){
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        //Return JSON String
        return jsonObject;
    }
}
