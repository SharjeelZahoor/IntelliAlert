package com.sharjeel.common;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sharjeel.entity.User;


public class App extends Application {
    private String webAddress;
    private User user;
    @Override
    public void onCreate() {
        super.onCreate();
        this.webAddress=getWebAddress();
        this.user=null;
    }

    public String getBaseUrl()
    {
        return this.webAddress+"meeting/api/";
    }
    public void setWebAddress(String webAddress)
    {
        if(!webAddress.endsWith("/")) webAddress=webAddress+"/";
        this.webAddress=webAddress;
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor settingEditor=prefs.edit();
        settingEditor.putString("base_url",webAddress);
        settingEditor.commit();
    }
    public String getWebAddress()
    {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        this.webAddress= prefs.getString("base_url","http://127.0.0.1/");
        return this.webAddress;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getLoginId()
    {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString("login_id","");
    }
    public String getPw()
    {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString("pw","");
    }
    public boolean getRem()
    {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getBoolean("rem",false);
    }
    public void setCredentials(String email,String pw,boolean rem)
    {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor settingEditor=prefs.edit();
        if(rem)
        {
            settingEditor.putString("login_id",email);
            settingEditor.putString("pw",pw);
            settingEditor.putBoolean("rem",rem);
        }else
        {
            settingEditor.putString("login_id","");
            settingEditor.putString("pw","");
            settingEditor.putBoolean("rem",rem);
        }
        settingEditor.commit();
    }


}
