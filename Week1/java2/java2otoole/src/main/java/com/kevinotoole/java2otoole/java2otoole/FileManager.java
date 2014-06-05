package com.kevinotoole.java2otoole.java2otoole;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Kevin O'Toole
 * Java 2 Term 1406
 * Week 1 Project
 */
public class FileManager {

    private static FileManager instance;

    //Constructor:
    public static FileManager getInstance() {
        if (instance == null){
            instance = new FileManager();
        }
        return instance;
    }

    //Writing JSON string to file:
    public Boolean writeStringToFile (Context context, String filename, String content){
        Boolean result = false;
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            Log.i("WRITE STRING FILE", "success");
        } catch (Exception e) {
            Log.e("WRITE FILE ERROR", e.toString());
        }
        return result;
    }

    //Read JSON String from file:
    public String readStringFromFile (Context context, String filename){
        String content = "";
        FileInputStream fis = null;

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

        } catch (Exception e){

        } finally {
            try {
                fis.close();
            } catch (IOException e){
                Log.e("CLOSE FILE ERROR", e.toString());
            }
        }

        return content;
    }
}
