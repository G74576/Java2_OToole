package com.kevinotoole.java2otoole.java2otoole;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Author: Kevin OToole
 * Java 2 Term 1406
 * Week 4 Project
 * Project: USMC Instagram Photos
 * Package: com.kevinotoole.java2otoole.java2otoole;
 * File: FileManager.java
 * Purpose:
 */
public class FileManager {

    private static FileManager instance;

    private FileManager(){

    }

    //Constructor:
    public static FileManager getInstance() {
        if (instance == null){
            instance = new FileManager();
        }
        return instance;
    }

    //Writing JSON string to file:
    public Boolean WriteFileToString (Context context, String filename, String content){
        Boolean result = false;
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            result = true;
            Log.i("WRITE_FILE", "Success");
        } catch (FileNotFoundException e) {
            Log.e("WRITE FILE ERROR", "Error writing file");
        }catch (IOException e){
            Log.e("WRITE FILE ERROR", "Failure");
        }
        return result;
    }

    //Read JSON String from file:
    public String readStringFromFile (Context context, String filename){
        String content = "";
        FileInputStream fis = null;

        File saveData = MainActivity.mContext.getFileStreamPath(MainActivity.fileName);

        if (saveData.exists()) {
            try {
                fis = context.openFileInput(filename);
                BufferedInputStream bis = new BufferedInputStream(fis);
                byte[] contentBytes = new byte[1024];
                int bytesRead = 0;
                StringBuffer contentBuffer = new StringBuffer();

                while ((bytesRead = bis.read(contentBytes)) != -1) {
                    content = new String(contentBytes, 0, bytesRead);
                    contentBuffer.append(content);
                }
                content = contentBuffer.toString();

            } catch (Exception e) {
                Log.e("READ FILE ERROR", "Error reading file");
                e.printStackTrace();
                return content;
            } finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("READ FILE ERROR", "Error closing input stream");
                }
            }
        }

        return content;
    }
}
