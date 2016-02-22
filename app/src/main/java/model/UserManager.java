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

    private UserManager() {
    }

    public static UserManager getInstance() {
        if(ourInstance == null)
            ourInstance = new UserManager();
        return ourInstance;
    }

    private boolean existUsername(String username){
        return helper.selectUsername(username);
    }

    private boolean existEmail(String email){
        return helper.selectUserEmail(email);
    }



    public void register(UserAcc user){
        if(!existUsername(user.getUserName()) && !existEmail(user.getEmail().toString())){
            helper.createUser(user);
        }
        else{
            // friendly message
        }

    }



}
