package model;

import model.dao.DBUserDAO;
import model.db.DatabaseHelper;

import android.content.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        password = md5(password);

        return userDAO.checkLogin(email, password);
    }

    private static final String md5(final String password) {
        try {

            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
