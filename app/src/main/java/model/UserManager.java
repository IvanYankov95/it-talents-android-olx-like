package model;

import model.dao.DBUserDAO;
import model.db.DatabaseHelper;

import android.content.Context;

/**
 * Created by owner on 22/02/2016.
 */
public class UserManager {
    private static UserManager ourInstance;
    DBUserDAO userDAO;

    private UserManager(Context context) {

        this.userDAO = new DBUserDAO(context);
    }

    public static UserManager getInstance( Context context) {
        if(ourInstance == null)
            ourInstance = new UserManager(context);
        return ourInstance;
    }

    public boolean existUsername(String username){

        return userDAO.checkUsername(username);
    }

    public boolean existEmail(String email){

        return userDAO.checkUserEmail(email);
    }



    public long register(UserAcc user){
        long id = userDAO.addUser(user);
        if(id != -1)
            user.setUserId(id);
        return id;
    }

    public UserAcc login(String email, String password){

        return userDAO.checkLogin(email, password);
    }




}
