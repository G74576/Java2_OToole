package com.kevinotoole.java2otoole.java2otoole;

import java.io.Serializable;

/**
 * Created by kevinotoole on 6/16/14.
 */
public class UserInfo implements Serializable {
    private static long serialVersionUID = 1L;
    public String user_name;
    public String full_name;
    public String prof_url;
    public String img_url;
    public String img_link;

    public UserInfo() {

    }

    public UserInfo(String user_Name, String full_Name, String prof_Url, String img_Url, String img_Link){
        super();
        this.user_name = user_Name;
        this.full_name = full_Name;
        this.prof_url = prof_Url;
        this.img_url = img_Url;
        this.img_link = img_Link;
    }

    public String getUser_name(){
        return user_name;
    }

    public void setUser_name(String user_name){
        this.user_name = user_name;
    }

    public String getFull_name(){
        return full_name;
    }

    public void setFull_name(String full_name){
        this.full_name = full_name;
    }

    public String getProf_url(){
        return prof_url;
    }

    public void setProf_url(String prof_url){
        this.prof_url = prof_url;
    }

    public String getImg_url(){
        return img_url;
    }

    public void setImg_url(String img_url){
        this.img_url = img_url;
    }

    public String getImg_link(){
        return img_link;
    }

    public void setImg_link(String img_link){
        this.img_link = img_link;
    }
}
