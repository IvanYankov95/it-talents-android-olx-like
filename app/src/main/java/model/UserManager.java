package model;

import db.DatabaseHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;

import java.sql.SQLException;

/**
 * Created by owner on 22/02/2016.
 */
public class UserManager {
    private static UserManager ourInstance;
    DatabaseHelper helper;

    private UserManager(Context context) {
        this.helper = new DatabaseHelper(context);
    }

    public static UserManager getInstance( Context context) {
        if(ourInstance == null)
            ourInstance = new UserManager(context);
        return ourInstance;
    }

    private boolean existUsername(String username){
        return helper.checkUsername(username);
    }

    private boolean existEmail(String email){
        return helper.checkUserEmail(email);
    }



    public void register(UserAcc user){
        if(!existUsername(user.getUserName()) && !existEmail(user.getEmail().toString())){
            helper.createUser(user);
        }
        else{
            // friendly message
        }

    }

    public void login(String username, String password){

    }




}
