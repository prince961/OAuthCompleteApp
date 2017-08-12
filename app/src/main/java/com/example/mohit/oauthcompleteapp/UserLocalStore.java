package com.example.mohit.oauthcompleteapp;

/**
 * Created by princ on 18-01-2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserLocalStore {

    public static String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){

        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);

    }

    public void storeUserData(String name, String number, String address, int id){

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("name",name);
        spEditor.putString("phone",number);
        spEditor.putString("address",address);
        spEditor.putInt("id",id);
        spEditor.apply();
        //spEditor.commit();

        Log.i("UserAddress", address);

    }

    public void storeUserEmail(String email){

        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("eMail",email);
        spEditor.apply();
        //spEditor.commit();
    }

    public String getUserName(){
        String userName = userLocalDatabase.getString("name","");
        return userName;
    }
    public String getUserNumber(){
        String userNumber = userLocalDatabase.getString("phone","");
        return userNumber;
    }
    public String getUserAddress(){
        String userNumber = userLocalDatabase.getString("address","");
        return userNumber;
    }


    public User GetLoggedinUser() {
        if (userLocalDatabase.getBoolean("loggedIn", false) == false) {
            return null;
        }

        String name = userLocalDatabase.getString("name", "");
        String username = userLocalDatabase.getString("username", "");
        String password = userLocalDatabase.getString("password", "");
        int age = userLocalDatabase.getInt("age", -1);

        User user = new User(name, age, username, password);
        return user;
    }

    public String getUserEmail(){
        return userLocalDatabase.getString("eMail","");
    }


    public void SetUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("LoggedIn", loggedIn);
        spEditor.commit();
        Log.i("BooleanUserLocalStore", Boolean.toString(userLocalDatabase.getBoolean("LoggedIn",true)));
    }

    public boolean getUserLoggedIn(){
        if (userLocalDatabase.getBoolean("LoggedIn", false)==true){
            return true;
        }
        else {
            return false;
        }
    }

    public void ClearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }

    public void storeUserNumberPassword(String number, String password){
       SharedPreferences.Editor spEditor = userLocalDatabase.edit();
       spEditor.putString("phone",number);
       spEditor.putString("password",password);
        spEditor.apply();
    }

    public String getUserPassword(){
        return userLocalDatabase.getString("password",null);
    }

    public int getUserId() {
        return userLocalDatabase.getInt("id",0);
    }
}