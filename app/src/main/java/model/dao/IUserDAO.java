package model.dao;

import java.util.List;

import model.Message;
import model.UserAcc;

/**
 * Created by owner on 01/03/2016.
 */
public interface IUserDAO {

    long addUser(UserAcc user);
    UserAcc getUser(String username);
    UserAcc getUser(long id);
    List<UserAcc> getAllUsers();
    void deleteUser(UserAcc user);
    long updateUser(UserAcc user);
    long updateUser(long userId, String fname, String lname, String phone, String city, String address);
    boolean checkUsername(String username);
    boolean checkUserEmail(String email);
    boolean checkPassword(long userId, String password);
    UserAcc checkLogin (String email, String password);
    long updateEmail(long userId, String email);
    long updatePassword(long userId, String password);
    long sendMessage(Message msg);

}
